package com.marcus.handler;

import com.marcus.client.NettyHttpClient;
import com.marcus.router.HttpRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
@ChannelHandler.Sharable
public class HttpOutboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private HttpRouter httpRouter;
    private List<String> serverList;
    private OkHttpClient client = new OkHttpClient();
    private boolean useOkClient;

    public HttpOutboundHandler(boolean useOkClient) {
        super(false);

        this.httpRouter = new HttpRouter();
        this.serverList = Arrays.asList("http://localhost:8801"
                , "http://localhost:8802"
                , "http://localhost:8803"
        );
        this.useOkClient = useOkClient;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        if (useOkClient) {
            sendRequestFromOkClient(ctx, fullHttpRequest);
        } else {
            sendRequestFromNettyClient(ctx, fullHttpRequest);
        }
    }

    private void sendRequestFromOkClient(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws IOException {
        Request request = remapRequest(fullHttpRequest);
        Response response = client.newCall(request).execute();

        log.info("response is successful? {}", response.isSuccessful());
        byte[] body = response.body().bytes();
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
        fullHttpResponse.headers().setInt("Content-Length", body.length);
        ctx.writeAndFlush(fullHttpResponse);
    }

    private void sendRequestFromNettyClient(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        String target = httpRouter.route(serverList);
        new NettyHttpClient(URI.create(target), ctx, fullHttpRequest);
    }

    private Request remapRequest(FullHttpRequest fullHttpRequest) {
        List<Map.Entry<String, String>> entries = fullHttpRequest.headers().entries();
        String targetUrl = httpRouter.route(serverList);
        Request.Builder builder = new Request.Builder();
        entries.forEach(headersMap -> builder.header(headersMap.getKey(), headersMap.getValue()));

        String uri = fullHttpRequest.uri();

        return builder.url(targetUrl + "/" + uri).get().build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("caught");
        cause.printStackTrace();
//        ctx.close();
    }
}
