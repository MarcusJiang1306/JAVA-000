package com.marcus.util;

import io.netty.handler.codec.http.*;

public class HttpResponseTemplate {

    public static DefaultFullHttpResponse getErrorResponse(String requestID) {

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT);
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        response.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
        response.headers().set("requestID", requestID);
        return response;

    }
}
