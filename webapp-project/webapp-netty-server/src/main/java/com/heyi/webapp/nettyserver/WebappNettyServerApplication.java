package com.heyi.webapp.nettyserver;

import com.heyi.webapp.server.netty.SubscribeReqProto;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappNettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappNettyServerApplication.class, args);
        System.out.println(test());
    }

    public static SubscribeReqProto.SubscribeReq test(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.addAddress("shanghai 401");
        builder.setProductName("cnabs");
        builder.setUserName("llj");

        SubscribeReqProto.SubscribeReq req= builder.build();
        byte[] data=req.toByteArray();
        SubscribeReqProto.SubscribeReq req1=null;

        try {
            req1= SubscribeReqProto.SubscribeReq.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return req1;
    }


}
