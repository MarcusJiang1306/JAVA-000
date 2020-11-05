package com.marcus.client;

import com.marcus.client.handler.HttpClientResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class NettyHttpClient {
    private Channel ch ;
    private HttpClientResponseHandler httpClientResponseHandler;

    public NettyHttpClient(URI uri,ChannelHandlerContext ctx, HttpRequest httpRequest) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

//      The following line is for request & response codec.
                pipeline.addLast(new HttpClientCodec());

//      The following line is for automatic content decompression.
                pipeline.addLast(new HttpContentDecompressor());

//      The following line is for handle HttpContents.
                pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                 httpClientResponseHandler = new HttpClientResponseHandler();
                pipeline.addLast(httpClientResponseHandler);

            }
        });

        String host = uri.getHost();
        try {
            ch = bootstrap.connect(host, uri.getPort()).sync().channel();
            log.info("connected to backend: {}",uri.toString());
            sendRequest(ctx, httpRequest);
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public void sendRequest(ChannelHandlerContext ctx, HttpRequest request) {
        httpClientResponseHandler.setCtx(ctx);
        ch.writeAndFlush(request);
    }


}
