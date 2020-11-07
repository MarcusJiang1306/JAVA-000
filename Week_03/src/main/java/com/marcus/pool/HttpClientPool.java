package com.marcus.pool;

import com.marcus.dispatcher.RequestPendingCenter;
import io.netty.channel.Channel;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class HttpClientPool {

    HttpChannelPoolMap poolMap = new HttpChannelPoolMap(3, 6);
    private RequestPendingCenter requestPendingCenter = RequestPendingCenter.getInstance();


    public HttpClientPool() {
    }

    public void newCall(String requestID, String url, FullHttpRequest request) {
        InetSocketAddress addr = getAddr(url);
        FixedChannelPool pool = poolMap.get(addr);
        Future<Channel> f = pool.acquire();

        try {
            Channel channel = f.get();
            HttpResponseFuture future = new HttpResponseFuture();
            requestPendingCenter.add(requestID, future);
            channel.writeAndFlush(request);
            pool.release(channel);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            requestPendingCenter.set(requestID, getErrorResponse(requestID));
        }


    }

    private static InetSocketAddress getAddr(String url) {
        URI uri = URI.create(url);
        return InetSocketAddress.createUnresolved(uri.getHost(), uri.getPort());
    }

    private DefaultFullHttpResponse getErrorResponse(String requestID) {

        DefaultFullHttpResponse defaultFullHttpRequest = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        defaultFullHttpRequest.headers().set("connection", HttpHeaders.Values.KEEP_ALIVE);
        defaultFullHttpRequest.headers().set("accept-encoding", "gzip");
        defaultFullHttpRequest.headers().set("requestID", requestID);
        return defaultFullHttpRequest;

    }
}
