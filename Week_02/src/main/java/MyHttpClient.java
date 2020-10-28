import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class MyHttpClient {
    OkHttpClient client = new OkHttpClient();


    public static void main(String[] args) {
        MyHttpClient myHttpClient = new MyHttpClient();
        String response = myHttpClient.getResponse("http://localhost:8088/api/hello");
        System.out.println(response);
    }

    private String getResponse(String url)  {
        String responseString = null;
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
              responseString = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }
}
