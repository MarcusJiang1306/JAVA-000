package com.marcus.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class PoolClientResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("get response");
        FullHttpResponse httpResponse = (FullHttpResponse) msg;
        System.out.println(httpResponse.content().toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("PoolClientResponseHandler hit exception");
        cause.printStackTrace();
    }
}
