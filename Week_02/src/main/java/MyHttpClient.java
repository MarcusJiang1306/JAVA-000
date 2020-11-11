import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;

import java.io.IOException;

public class MyHttpClient {
    OkHttpClient client = new OkHttpClient();


    public static void main(String[] args) {
        MyHttpClient myHttpClient = new MyHttpClient();
        String response = myHttpClient.getResponse("http://localhost:8801/header");
        System.out.println(response);
    }

    private String getResponse(String url)  {
        String responseString = null;
        MediaType header = MediaType.parse("text/plain; charset=utf-8");

        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Connection","close")
                .build();
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
