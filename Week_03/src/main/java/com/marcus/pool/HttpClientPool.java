package com.marcus.pool;

import com.marcus.client.handler.HttpClientResponseHandler;
import com.marcus.client.handler.PoolClientResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class HttpClientPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap cb = new Bootstrap();
        cb.group(group).channel(NioSocketChannel.class);
        cb.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

//      The following line is for request & response codec.
                pipeline.addLast(new HttpClientCodec());

//      The following line is for automatic content decompression.
                pipeline.addLast(new HttpContentDecompressor());

//      The following line is for handle HttpContents.
                pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
            }
        });

        AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool> poolMap = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>() {
            @Override
            protected SimpleChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(cb.remoteAddress(key), new AbstractChannelPoolHandler() {
                    @Override
                    public void channelCreated(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

//      The following line is for request & response codec.
                        pipeline.addLast(new HttpClientCodec());

//      The following line is for automatic content decompression.
                        pipeline.addLast(new HttpContentDecompressor());

//      The following line is for handle HttpContents.
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));

                        pipeline.addLast(new PoolClientResponseHandler());
                    }
                }, 3, 6);
            }
        };

// depending on when you use addr1 or addr2 you will get different pools.
        final SimpleChannelPool pool = poolMap.get(getAddr());
        Future<Channel> f = pool.acquire();
        f.addListener((FutureListener<Channel>) f1 -> {
            if (f1.isSuccess()) {
                Channel ch = f1.getNow();
                ch.writeAndFlush(getRequest());

                // Release back to pool
                pool.release(ch);
            }
        });
    }

    private static InetSocketAddress getAddr() {
        String url = "http://127.0.0.1:8801";
        URI uri = URI.create(url);
        return InetSocketAddress.createUnresolved(uri.getHost(), uri.getPort());
    }

    private static FullHttpRequest getRequest() {

        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/api/hello");
        defaultFullHttpRequest.headers().set("connection", "close");
        defaultFullHttpRequest.headers().set("host", "localhost:8801");
        defaultFullHttpRequest.headers().set("accept-encoding", "gzip");
        defaultFullHttpRequest.headers().set("user-agent", "okhttp/4.7.2");
        return defaultFullHttpRequest;

    }
}
