package com.marcus.server.router;

import java.util.List;

public class HttpRouter implements HttpEndpointRouter {

    private int seq = 0;

    @Override
    public String route(List<String> endpoints) {
        seq %= endpoints.size();
        return endpoints.get(seq++);
    }

}
