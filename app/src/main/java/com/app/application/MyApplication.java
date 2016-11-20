package com.app.application;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;

import com.app.service.MediaPlayerService;
import com.app.urldatas.UrlData;
import com.app.utils.MyUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hao on 2016-09-25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        x.Ext.init(this);
        x.Ext.setDebug(true);
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);
        Intent intent = new Intent(this, MediaPlayerService.class);
        startService(intent);
    }
}
