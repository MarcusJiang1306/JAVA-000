package handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.EntityUtils;
import router.HTTPRouter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class HttpOutboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private HTTPRouter httpRouter;
    private List<String> serverList;

    public HttpOutboundHandler() {
        this.httpRouter = new HTTPRouter();
        this.serverList = Arrays.asList("http://localhost:8801","http://localhost:8802","http://localhost:8803");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        List<Map.Entry<String, String>> entries = fullHttpRequest.headers().entries();
        HashMap<String, String> requestBodyMap = new HashMap<>();
        entries.forEach(headersMap -> requestBodyMap.put(headersMap.getKey(),headersMap.getValue()));
        requestBodyMap.put("connection", "close");

        String targetUrl = httpRouter.route(serverList);
        Request.Builder builder = new Request.Builder();
        requestBodyMap.forEach(builder::header);

        String uri = fullHttpRequest.uri();
        System.out.println(uri);
        builder.url(targetUrl + "/" + uri);

        Request request = builder.get().build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        byte[] body = response.body().bytes();
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
        fullHttpResponse.headers().setInt("Content-Length", body.length);
        ctx.write(fullHttpResponse);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
