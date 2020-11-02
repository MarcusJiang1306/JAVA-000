package router;

import java.util.Arrays;
import java.util.List;

public class HTTPRouter implements HttpEndpointRouter {

    private int seq = 0;

    @Override
    public String route(List<String> endpoints) {
        seq %= endpoints.size();
        return endpoints.get(seq++);
    }

}
