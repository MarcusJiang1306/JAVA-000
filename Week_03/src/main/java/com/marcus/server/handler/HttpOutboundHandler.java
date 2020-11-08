package com.marcus.server.handler;

import com.marcus.dispatcher.RequestPendingCenter;
import com.marcus.pool.HttpClientPool;
import com.marcus.pool.HttpResponseFuture;
import com.marcus.server.router.HttpRouter;
import com.marcus.util.HttpResponseTemplate;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class HttpOutboundHandler extends ChannelInboundHandlerAdapter {

    private HttpRouter httpRouter = new HttpRouter();
    private List<String> serverList = Arrays.asList("http://localhost:8801"
            , "http://localhost:8802"
            , "http://localhost:8803"
    );
    private HttpClientPool clientPool = new HttpClientPool();
    private RequestPendingCenter requestPendingCenter = RequestPendingCenter.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        String requestID = fullHttpRequest.headers().get("requestID");
        String url = httpRouter.route(serverList);
        flushResponse(ctx, fullHttpRequest, requestID, url);
    }

    private void flushResponse(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest, String requestID, String url) throws Exception {
        clientPool.newCall(requestID, url, fullHttpRequest);
        HttpResponseFuture future = requestPendingCenter.get(requestID);
        try {
            FullHttpResponse response = future.get(500, TimeUnit.MILLISECONDS);
            ctx.writeAndFlush(response);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            requestPendingCenter.remove(requestID);
            throw e;
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("httpOutboundHandler hit exception");
        DefaultFullHttpResponse errorResponse = HttpResponseTemplate.getErrorResponse("null");
        ctx.writeAndFlush(errorResponse);
        ctx.close();
        cause.printStackTrace();
    }
}
