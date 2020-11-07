package com.marcus.server.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HeaderFilter extends ChannelInboundHandlerAdapter implements HttpRequestFilter {


    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("Nio", "Marcus");
        fullRequest.headers().set("requestID", fullRequest.hashCode());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        filter((FullHttpRequest) msg, ctx);
        super.channelRead(ctx, msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("headerFilter hit exception");
        cause.printStackTrace();
    }
}
