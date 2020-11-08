package com.marcus.dispatcher;


import com.marcus.pool.HttpResponseFuture;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {
    private static RequestPendingCenter pendingCenter = new RequestPendingCenter();

    private ConcurrentHashMap<String , HttpResponseFuture> futureMap = new ConcurrentHashMap<>();

    public void add(String requestID, HttpResponseFuture future) {
        this.futureMap.put(requestID, future);
    }

    public void set(String requestID, FullHttpResponse operationResult) {
        if (null != requestID && futureMap.containsKey(requestID)) {
            HttpResponseFuture operationResultFuture = this.futureMap.get(requestID);
            operationResultFuture.setSuccess(operationResult);
            remove(requestID);
        }
    }
    public HttpResponseFuture get(String requestID) {
        return futureMap.get(requestID);
    }

    public void remove(String requestID) {
        this.futureMap.remove(requestID);
    }

    private RequestPendingCenter() {
    }

    public static RequestPendingCenter getInstance() {
        return pendingCenter;
    }
}
