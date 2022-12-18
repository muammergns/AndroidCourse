package com.gns.notificationforegroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "MainActivity";
    public static final String CHANNEL_ID = "com.gns.notificationforegroundservice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateNotification();
    }

    private void onCreateNotification(){
        final int flag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE :
                PendingIntent.FLAG_UPDATE_CURRENT;

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flag);

        Intent actionIntent = new Intent(this, NotificationReceiver.class);
        actionIntent.putExtra("putExtra","actionIntent");
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(this, 1, actionIntent, flag);

        Intent startProgressIntent = new Intent(this, NotificationReceiver.class);
        startProgressIntent.putExtra("putExtra","startProgressIntent");
        PendingIntent startProgressPendingIntent = PendingIntent.getBroadcast(this, 2, startProgressIntent, flag);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSubText("setSubText")
                .setContentTitle("setContentTitle")
                .setContentText("setContentText: onCreate")
                .setContentIntent(notificationPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Action", actionPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Progress", startProgressPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = builder.build();
        notificationManagerCompat.notify(0, notification);
    }
}