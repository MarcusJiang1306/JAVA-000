package com.marcus.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientResponseHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ChannelHandlerContext;

    public void setCtx(ChannelHandlerContext ChannelHandlerContext) {
        this.ChannelHandlerContext = ChannelHandlerContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.close();
        FullHttpResponse httpResponse = (FullHttpResponse) msg;
        ChannelHandlerContext.writeAndFlush(httpResponse);

    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("httpClientResponseHandler hit exception");
        cause.printStackTrace();
    }
}
