package com.marcus.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class HttpChannelPoolMap extends AbstractChannelPoolMap<InetSocketAddress,FixedChannelPool> {

    @Override
    protected FixedChannelPool newPool(InetSocketAddress key) {
        Bootstrap bootstrap = new Bootstrap().group(new NioEventLoopGroup()).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
        return new FixedChannelPool(bootstrap.remoteAddress(key), new HttpChannelPoolHandler(), 3, 6);
    }
}
