package com.example.alarmremindertimer;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long millis = intent.getLongExtra("title",0);
        int type = intent.getIntExtra("type",0);
        long current = type == AlarmManager.RTC_WAKEUP ? System.currentTimeMillis() : type == AlarmManager.ELAPSED_REALTIME_WAKEUP ? SystemClock.elapsedRealtime() : 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getPackageName())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSubText("current:"+current+" millis:"+millis)
                .setContentTitle("setContentTitle must be:"+60000)
                .setContentText("setContentText result"+ String.valueOf(current-millis));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = builder.build();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompat.notify(0, notification);
        }
    }
}