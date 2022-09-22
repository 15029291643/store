package com.example.room.network;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkhttpUtils {
    private static final String TAG = "OkhttpUtils";
    private static final OkHttpClient client = new OkHttpClient();

    // https://www.lanrentuku.com/topic/apptubiaodaquan.html
    public static String getHtml(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
