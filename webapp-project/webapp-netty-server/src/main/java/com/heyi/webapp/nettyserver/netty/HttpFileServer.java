package com.heyi.webapp.nettyserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
    public static void main(String[] args){
        int port=8080;
        HttpFileServer server=new HttpFileServer();
        try {
            server.run(port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public HttpFileServer() {
    }

    public void run(final int port) throws InterruptedException {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("http-decoder",new HttpServerCodec());
                            socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));

                            socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            //socketChannel.pipeline().addLast("http-encoder",new HttpResponseEncoder());


                            socketChannel.pipeline().addLast("fileServerHandler",new HttpFileServerHandler());
                        }
                    });

            ChannelFuture future=bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
