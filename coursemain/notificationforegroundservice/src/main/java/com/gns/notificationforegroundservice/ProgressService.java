package com.gns.notificationforegroundservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.format.DateUtils;

import androidx.annotation.Nullable;

public class ProgressService extends Service {

    Handler handler;Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        showNotification(SystemClock.elapsedRealtime()/1000, 120);
    }


    private void showNotification(long startRealTime, int maxTime){
        int currentTime = 0;
        Notification.Builder builder;
        String CHANNEL_ID = "com.gns.notificationforegroundservice";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.app_name));
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this,CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setSubText("setSubText")
                .setContentTitle("setContentTitle")
                .setOnlyAlertOnce(true)
                .setProgress(maxTime,currentTime,false)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentText(DateUtils.formatElapsedTime(maxTime));
        startForeground(2,builder.build());

        handler = new Handler();
        runnable = () -> {
            long currentRealTime = (SystemClock.elapsedRealtime()/1000);
            builder.setContentText(DateUtils.formatElapsedTime(maxTime-(currentRealTime-startRealTime)));
            builder.setProgress(maxTime, (int) (currentRealTime-startRealTime),false);
            startForeground(2,builder.build());
            if ((currentRealTime-startRealTime)>=maxTime){
                handler.removeCallbacks(runnable);
                stopForeground(true);
                stopSelf();
            }else {
                handler.postDelayed(runnable,1000);
            }
        };
        handler.post(runnable);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
