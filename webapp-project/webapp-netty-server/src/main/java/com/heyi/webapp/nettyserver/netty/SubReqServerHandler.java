package com.heyi.webapp.nettyserver.netty;

import com.heyi.webapp.server.netty.SubscribeReqProto;
import com.heyi.webapp.server.netty.SubsrcibeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        SubscribeReqProto.SubscribeReq req =(SubscribeReqProto.SubscribeReq) msg;
        System.out.println("server accept: " + req.toString());
        ctx.writeAndFlush(resp(req.getSubReqID()));
    }

    private SubsrcibeRespProto.SubscribeResp resp(int subReqID){
        SubsrcibeRespProto.SubscribeResp.Builder builder=SubsrcibeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(200);
        builder.setDesc("server send response: I know your information");
        return  builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
