package com.marcus.client.handler;

import com.marcus.client.dispatcher.RequestPendingCenter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;

public class HttpClientInitalizer extends ChannelInitializer<NioSocketChannel> {
    private final SslContext sslContext;

    public HttpClientInitalizer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }
//      The following line is for request & response codec.
        pipeline.addLast(new HttpClientCodec());

//      The following line is for automatic content decompression.
        pipeline.addLast(new HttpContentDecompressor());

//      The following line is for handle HttpContents.
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));

        pipeline.addLast(new HttpClientResponseHandler(new RequestPendingCenter()));

    }
}
