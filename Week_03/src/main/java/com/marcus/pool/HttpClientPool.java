package com.marcus.pool;

import com.marcus.dispatcher.RequestPendingCenter;
import io.netty.channel.Channel;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class HttpClientPool {

    HttpChannelPoolMap poolMap = new HttpChannelPoolMap();
    private RequestPendingCenter requestPendingCenter = RequestPendingCenter.getInstance();


    public HttpClientPool() {
    }

    public void newCall(String url, FullHttpRequest request) {
        InetSocketAddress addr = getAddr(url);
        FixedChannelPool pool = poolMap.get(addr);
        Future<Channel> f = pool.acquire();
        Channel channel = null;
        try {
            channel = f.get();
            HttpResponseFuture future = new HttpResponseFuture();
            requestPendingCenter.add(request.headers().get("requestID"), future);
            channel.writeAndFlush(request);
            pool.release(channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    }

    private static InetSocketAddress getAddr(String url) {
        URI uri = URI.create(url);
        return InetSocketAddress.createUnresolved(uri.getHost(), uri.getPort());
    }

    private static FullHttpRequest getRequest() {

        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
        defaultFullHttpRequest.headers().set("connection", HttpHeaders.Values.KEEP_ALIVE);
        defaultFullHttpRequest.headers().set("host", "localhost:9350");
        defaultFullHttpRequest.headers().set("accept-encoding", "gzip");
        defaultFullHttpRequest.headers().set("user-agent", "okhttp/4.7.2");
        defaultFullHttpRequest.headers().set("requestID", "1");
        return defaultFullHttpRequest;

    }
}
