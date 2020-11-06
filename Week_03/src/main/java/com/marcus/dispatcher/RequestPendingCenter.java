package com.marcus.dispatcher;


import com.marcus.pool.HttpResponseFuture;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {
    private static RequestPendingCenter pendingCenter = new RequestPendingCenter();

    private ConcurrentHashMap<String , HttpResponseFuture> futureMap = new ConcurrentHashMap<>();

    public void add(String requestID, HttpResponseFuture future) {
        System.out.println("put id: "+ requestID);
        this.futureMap.put(requestID, future);
    }

    public void set(String requestID, FullHttpResponse operationResult) {
        System.out.println("set id: "+ requestID);
        HttpResponseFuture operationResultFuture = this.futureMap.get(requestID);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
            this.futureMap.remove(requestID);
        }
    }
    public HttpResponseFuture get(String requestID) {
        System.out.println("get id: "+ requestID);
        return futureMap.get(requestID);
    }

    private RequestPendingCenter() {
    }

    public static RequestPendingCenter getInstance() {
        return pendingCenter;
    }
}
