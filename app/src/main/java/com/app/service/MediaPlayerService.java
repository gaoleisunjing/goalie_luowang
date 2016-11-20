package com.app.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hao on 2016-09-28.
 */
public class MediaPlayerService extends Service {
    MediaPlayer mediaPlayer;//用于播放音乐或者视频
    ProgressReceiver receiver;
    boolean isPlaying;
    String musicname, musictitle, musiccontent;

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        mediaPlayer.stop();
        mediaPlayer.release();
        unregisterReceiver(receiver);
    }

    /**
     * 切换歌曲
     */
    public void switchMusic(String path, boolean isRun) {
        if (!isRun) {
            mediaPlayer.reset();// 重置
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else {
            mediaPlayer.start();
            isPlaying = !isPlaying;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建MediaPlayer对象,将资源加载
        mediaPlayer = new MediaPlayer();
        receiver = new ProgressReceiver();
        registerReceiver(receiver, new IntentFilter("com.kygo.progress"));//注册广播接收器
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //判断播放状态
        Log.d("aaaaa", "service");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
//            String stringExtra = intent.getStringExtra("musicname");
//            try {
//                if (stringExtra != null) {
//                    Log.d("aaaaa", stringExtra);
//                    mediaPlayer.setDataSource(this, Uri.parse(stringExtra));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            mediaPlayer.start();
            new TimeThread().start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class ProgressReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.kygo.progress")) {
                musicname = intent.getStringExtra("musicname");
                musictitle = intent.getStringExtra("musictitle");
                musiccontent = intent.getStringExtra("musiccontent");
                boolean isPause = intent.getBooleanExtra("isPause", false);
                boolean isContinue = intent.getBooleanExtra("isContinue", false);
                if (musicname != null) {
                    switchMusic(musicname, isPlaying);
                }
                if (mediaPlayer.isPlaying() && isPause) {
                    mediaPlayer.pause();//如果接收到暂停广播则暂停音乐播放
                }
                if (!mediaPlayer.isPlaying() && isContinue) {
                    mediaPlayer.start();//如果接收到继续播放广播则继续播放
                }
                new TimeThread().start();
            } else if (intent.getAction().equals("com.play.music")) {

            }
        }
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                //在此线程里获取MediaPlayer的播放时间以及总时间
                //将这些值传递给MainActivity去更新界面
                int cur = mediaPlayer.getCurrentPosition();//当前播放时间
                int max = mediaPlayer.getDuration();//总时长
                Intent intent = new Intent("com.kygo.time");
                intent.putExtra("cur", cur);
                intent.putExtra("max", max);
                if (musictitle != null && musiccontent != null) {
                    intent.putExtra("musictitle", musictitle);
                    intent.putExtra("musiccontent", musiccontent);
                }
                sendBroadcast(intent);
            }
        }
    }
}
