package com.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hao on 2016-09-26.
 */
public class MyUtils {
    public static String getJsonStr(String urlStr) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlStr).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmap(String urlStr) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlStr).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] bytes = response.body().bytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
