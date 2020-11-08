package com.marcus.pool;

import com.marcus.dispatcher.ResponseDispatcherHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class HttpChannelPoolHandler extends AbstractChannelPoolHandler {


    @Override
    public void channelCreated(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//      The following line is for request & response codec.
        pipeline.addLast(new HttpClientCodec());
//      The following line is for automatic content decompression.
        pipeline.addLast(new HttpContentDecompressor());
//      The following line is for handle HttpContents.
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new ResponseDispatcherHandler());
    }
}
