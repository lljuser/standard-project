package com.heyi.webapp.nettyserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
             sendError(ctx, HttpResponseStatus.BAD_REQUEST);
             return;
        }

        if (request.method() != HttpMethod.GET) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
           return;
        }

        final String uri = request.uri();
        final String path = sanitizeUri(uri);
        if (path == null) {
             sendError(ctx, HttpResponseStatus.FORBIDDEN);
             return;
        }

        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
             return;
        }

        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
               sendListing(ctx, file, uri);
              } else {
                  sendRedirect(ctx, uri + '/');
              }
              return;
         }

        if (!file.isFile()) {
             sendError(ctx, HttpResponseStatus.FORBIDDEN);
             return;
        }

          // Cache Validation
          String ifModifiedSince = request.headers().get(HttpHeaderNames.IF_MODIFIED_SINCE);
         if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
             SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
             Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

              // Only compare up to the second because the datetime format we send to the client
              // does not have milliseconds
              long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
              long fileLastModifiedSeconds = file.lastModified() / 1000;
             if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                 sendNotModified(ctx);
                return;
              }
         }

         RandomAccessFile raf;
         try {
             raf = new RandomAccessFile(file, "r");
         } catch (FileNotFoundException ignore) {
             sendError(ctx, HttpResponseStatus.NOT_FOUND);
             return;
         }
         long fileLength = raf.length();

         HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
         HttpUtil.setContentLength(response, fileLength);
         setContentTypeHeader(response, file);
         setDateAndCacheHeaders(response, file);
        if (HttpUtil.isKeepAlive(request)) {
             response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

         // Write the initial line and the header.
         ctx.write(response);

         // Write the content.
         ChannelFuture sendFileFuture;
         ChannelFuture lastContentFuture;
         if (ctx.pipeline().get(SslHandler.class) == null) {
              sendFileFuture =
                     ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength), ctx.newProgressivePromise());
             // Write the end marker.
            lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
         } else {
            sendFileFuture =
                     ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 0, fileLength, 8192)),
                           ctx.newProgressivePromise());
             // HttpChunkedInput will write the end marker (LastHttpContent) for us.
            lastContentFuture = sendFileFuture;
        }

         sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
              @Override
              public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                 if (total < 0) { // total unknown
                    System.err.println(future.channel() + " Transfer progress: " + progress);
                 } else {
                     System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                  }
              }

                @Override
              public void operationComplete(ChannelProgressiveFuture future) {
                    System.err.println(future.channel() + " Transfer complete.");
                 }
              });

         // Decide whether to close the connection or not.
         if (!HttpUtil.isKeepAlive(request)) {
              // Close the connection when the whole content is written out.
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
       }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
             sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private static String sanitizeUri(String uri) {
        try{
            uri= URLDecoder.decode(uri,"UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new Error(e);
        }

        if(uri.isEmpty() || uri.charAt(0) !='/'){
            return null;
        }

        uri = uri.replace('/',File.separatorChar);

        if(uri.contains(File.separator+'.') ||
                uri.contains('.' + File.separator) ||
                uri.charAt(0) == '.' || uri.charAt(uri.length() -1) == '.' ||
                INSECURE_URI.matcher(uri).matches()){
            return null;
        }

        String path = HttpFileServer.class.getResource("/").getPath().replace('/',File.separatorChar);
        path = path.substring(1,path.length());
        //return SystemPropertyUtil.get("user.dir") + File.separator + uri;
        return path+ "static" + uri;
    }


    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[^-\\._]?[^<>&\\\"]*");

    private static void sendListing(ChannelHandlerContext ctx,File dir,String dirPath){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html; charset=UTF-8");

        StringBuilder buf= new StringBuilder()
                .append("<!DOCTYPE html>")
                .append("<html><head><meta charset='utf-8' /><title>")
                .append("Listing of: ")
                .append(dirPath)
                .append("</title></head><body>")
                .append("<h3>Listing of: ")
                .append(dirPath)
                .append("</h3>")
                .append("<ul>")
                .append("<li><a href=\"../\">..</a></li>");

        for(File f: dir.listFiles()){
            if(f.isHidden() || !f.canRead()){
                continue;
            }

            String name = f.getName();
            if(!ALLOWED_FILE_NAME.matcher(name).matches()){
                continue;
            }

            buf.append("<li><a href=\"")
                    .append(name)
                    .append("\">")
                    .append(name)
                    .append("</a></li>");
        }

        buf.append("</ul></body></html>");
        ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    private static void sendRedirect(ChannelHandlerContext ctx,String newUri){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaderNames.LOCATION,newUri);

        ctx.writeAndFlush(response);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,Unpooled.copiedBuffer("Failure: "+ status + "\r\n",CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendNotModified(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
        setDateHeader(response);

         // Close the connection as soon as the error message is sent.
         ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setDateHeader(FullHttpResponse response){
        SimpleDateFormat dateFormat = new SimpleDateFormat(HTTP_DATE_FORMAT,Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        Calendar time =new GregorianCalendar();
        response.headers().set(HttpHeaderNames.DATE,dateFormat.format(time.getTime()));
    }

    private static void setDateAndCacheHeaders(HttpResponse response, File fileToCache) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        // Date header
        Calendar time = new GregorianCalendar();
        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));

        // Add cache headers
        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
        response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.headers().set(HttpHeaderNames.LAST_MODIFIED, dateFormatter.format(new Date(fileToCache.lastModified())));
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
     }
}
