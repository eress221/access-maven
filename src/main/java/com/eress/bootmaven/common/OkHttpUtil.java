package com.eress.bootmaven.common;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class OkHttpUtil {

    public String get(String url, boolean async) {
        log.debug("GET: " + url);
        String result = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            if (async) {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        logger.debug("FAIL: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        String res = response.body().string();
//                        logger.debug("SUCCESS: " + url);
                    }
                });
                result = "async";
            } else {
                try (Response response = client.newCall(request).execute()) {
                    result = response.body().string();
                    log.debug("SUCCESS: " + url);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    result = "error";
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = "error";
        }
        return result;
    }

    public String post(String url, boolean async) {
        log.debug("POST: " + url);
        String result = null;
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), new byte[]{});
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            if (async) {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        logger.debug("FAIL: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        String res = response.body().string();
//                        logger.debug("SUCCESS: " + url);
                    }
                });
                result = "async";
            } else {
                try (Response response = client.newCall(request).execute()) {
                    result = response.body().string();
                    log.debug("SUCCESS: " + url);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    result = "error";
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = "error";
        }
        return result;
    }
}
