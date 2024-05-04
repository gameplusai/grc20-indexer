package com.gameplus.indexer.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.Objects;

@Slf4j
public class HttpUtil {

    private static final OkHttpClient client = new OkHttpClient();

    public static String get(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            if (Objects.isNull(response.body())) return "";
            return response.body().string();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }

}
