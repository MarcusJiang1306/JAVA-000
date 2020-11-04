package com.marcus.handler;

import com.marcus.client.NettyHttpClient;
import com.marcus.router.HttpRouter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@ChannelHandler.Sharable
public class HttpOutboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private HttpRouter httpRouter;
    private List<String> serverList;
    private OkHttpClient client = new OkHttpClient();

    public HttpOutboundHandler() {
        super(false);

        this.httpRouter = new HttpRouter();
        this.serverList = Arrays.asList("http://localhost:8801"
                ,"http://localhost:8802"
                ,"http://localhost:8803"
        );
//        clientPool = new HttpClientPool(serverList);
//        System.out.println("client pool");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {

        String target = httpRouter.route(serverList);
        new NettyHttpClient(URI.create(target), ctx, fullHttpRequest);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("caught");
        cause.printStackTrace();
//        ctx.close();
    }
}
