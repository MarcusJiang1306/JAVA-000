package com.marcus.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;

public class ResponseDispatcherHandler extends ChannelInboundHandlerAdapter {

    private RequestPendingCenter requestPendingCenter = RequestPendingCenter.getInstance();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse response = (FullHttpResponse) msg;
        String requestID = response.headers().get("requestID");
        requestPendingCenter.set(requestID, response);
    }
}
