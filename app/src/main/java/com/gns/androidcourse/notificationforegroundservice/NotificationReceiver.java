package com.gns.androidcourse.notificationforegroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Objects;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getStringExtra("putExtra"), "actionIntent")){
            Log.d(TAG, "onReceive: actionIntent");
        }else if(Objects.equals(intent.getStringExtra("putExtra"), "startProgressIntent")){
            Log.d(TAG, "onReceive: startProgressIntent");
            startProgressNotification(context);
        }else {
            Log.d(TAG, "onReceive: ");
        }

    }

    private void startProgressNotification(Context context){
        Intent intent = new Intent(context, ProgressService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }else {
            context.startService(intent);
        }
    }
}
