package com.heyi.webapp.nettyserver.netty;

import com.heyi.webapp.server.netty.SubscribeReqProto;
import com.heyi.webapp.server.netty.SubsrcibeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        for(int i=0;i<5;i++){
            ctx.write(req(i));
        }

        ctx.flush();
    }

    public static SubscribeReqProto.SubscribeReq req(int i){
        SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("llj-"+i);
        builder.setProductName("cnabs-"+i);
        builder.addAddress("shanghai-"+i);
        builder.addAddress("nanjing-"+i);
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        SubsrcibeRespProto.SubscribeResp resp=(SubsrcibeRespProto.SubscribeResp)msg;
        System.out.println("client receive response:"+ resp.toString());

        for(int i=0;i<5;i++){
            ctx.write(req(i));
        }

        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
        executorService.shutdown();
    }

    static class MyJob implements Runnable{
        private ChannelHandlerContext ctx;
        private CountDownLatch latch;
        private int data;
        public MyJob(ChannelHandlerContext ctx,int data) {
            this.ctx=ctx;
            this.data=data;
        }

        @Override
        public void run() {
            ctx.write(req(data));
        }
    }
}
