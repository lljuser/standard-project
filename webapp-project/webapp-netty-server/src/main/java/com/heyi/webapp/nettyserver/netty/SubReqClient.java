package com.heyi.webapp.nettyserver.netty;

import com.heyi.webapp.server.netty.SubscribeReqProto;
import com.heyi.webapp.server.netty.SubsrcibeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SubReqClient {
    public static void main(String[] args){
        int port=8080;
        String host="127.0.0.1";
        SubReqClient client=new SubReqClient();
        try {
            client.connect(8080,host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(int port,String host) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup(100);
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.RCVBUF_ALLOCATOR,AdaptiveRecvByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            socketChannel.pipeline().addLast(new ProtobufDecoder(SubsrcibeRespProto.SubscribeResp.getDefaultInstance()));

                            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            socketChannel.pipeline().addLast(new ProtobufEncoder());

                            socketChannel.pipeline().addLast(new SubReqClientHandler());

                        }
                    });

            ChannelFuture future= bootstrap.connect(host,port).sync();

            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
