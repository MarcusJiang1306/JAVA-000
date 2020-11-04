package com.marcus.client;

import com.marcus.client.handler.HttpClientInitalizer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.DefaultCookie;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

public class NettyHttpClient {
    private static final String url = "http://127.0.0.1:8801/api/hello";

    public static void main(String[] args) throws URISyntaxException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);

        bootstrap.handler(new HttpClientInitalizer(null));

        URI uri = new URI(url);
        String host = uri.getHost();


        try {
            Channel ch = bootstrap.connect(host, uri.getPort()).sync().channel();

            // Prepare the HTTP request.
            HttpRequest request = getHttpRequest(uri, host);

            // Send the HTTP request.
            ch.writeAndFlush(request);
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

    private static HttpRequest getHttpRequest(URI uri,String host) {
        HttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath(), Unpooled.EMPTY_BUFFER);
        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

        // Set some example cookies.
        request.headers().set(
                HttpHeaderNames.COOKIE,
                io.netty.handler.codec.http.cookie.ClientCookieEncoder.STRICT.encode(
                        new DefaultCookie("my-cookie", "foo"),
                        new DefaultCookie("another-cookie", "bar")));
        return request;
    }
}
