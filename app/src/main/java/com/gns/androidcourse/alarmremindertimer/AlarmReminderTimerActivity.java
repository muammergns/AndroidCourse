package com.gns.androidcourse.alarmremindertimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.gns.androidcourse.R;
import com.gns.androidcourse.databinding.ActivityAlarmReminderTimerBinding;

public class AlarmReminderTimerActivity extends AppCompatActivity {

    private static final String TAG = "AlarmReminderTimer";
    public static final String CHANNEL_ID = "com.gns.alarmremindertimer";
    ActivityAlarmReminderTimerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmReminderTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String CHANNEL_ID = getPackageName();
        String CHANNEL_NAME = "Alarm Bildirimleri";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(getPackageName(), CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Alarm bildirimlerini alabilmek için buna izin verin");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)){
                    Toast.makeText(AlarmReminderTimerActivity.this,
                            "Bildirim göstermem için izin vermen lazım. yoksa bildirimleri göremezsin.",
                            Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.POST_NOTIFICATIONS},123);
            }
        }
        binding.setAlarmOneMinuteButton.setOnClickListener(v -> {
            // Şu anki zamanı alın
            int hour , minute;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
            }else {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
                minute = calendar.get(java.util.Calendar.MINUTE);
            }
            // 1 dakika sonrasını hesaplayın
            minute += 1;
            if (minute >= 60) {
                minute -= 60;
                hour += 1;
                if (hour >= 24) {
                    hour -= 24;
                }
            }
            // Alarmı ayarlamak için bir Intent oluşturun
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_HOUR, hour)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minute)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, "Alarm Mesajı")
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            // Eğer cihaz bu eylemi gerçekleştirebilecek bir uygulamaya sahipse, Intent'i başlatın
            try {
                startActivity(intent);
            }catch (Exception e){
                Log.e(TAG, "onCreate: ", e);
            }
        });
        binding.setReminderOneMinuteButton.setOnClickListener(v -> {
            // Hatırlatıcıyı ayarlamak için bir Intent oluşturun
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis() + 60 * 1000) // 1 dakika sonrası
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + 2 * 60 * 1000) // 2 dakika sonrası
                    .putExtra(CalendarContract.Events.TITLE, "Benim Hatırlatıcı Başlığım")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Benim Hatırlatıcı Açıklamam")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
            // Eğer cihaz bu eylemi gerçekleştirebilecek bir uygulamaya sahipse, Intent'i başlatın
            try {
                startActivity(intent);
            }catch (Exception e){
                Log.e(TAG, "onCreate: ", e);
            }
        });
        binding.setTimerOneMinuteButton.setOnClickListener(v -> {
            //<uses-permission android:name="com.android.alarm.permission.SET_ALARM"/> izni gerekiyor
            int seconds = 60; // Zamanlayıcının süresi (saniye cinsinden)
            String message = "Zamanlayıcı Bitti"; // Zamanlayıcının bitiş mesajı
            // Zamanlayıcıyı başlatmak için bir Intent oluşturun
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);

            // Eğer cihaz bu eylemi gerçekleştirebilecek bir uygulamaya sahipse, Intent'i başlatın
            try {
                startActivity(intent);
            }catch (Exception e){
                Log.e(TAG, "onCreate: ", e);
            }
        });

        binding.setAlarmManagerOneMinuteButton.setOnClickListener(v -> {
            Context context = AlarmReminderTimerActivity.this;
            int code = 123;
            int flag;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R){
                flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            }else {
                flag = PendingIntent.FLAG_UPDATE_CURRENT;
            }
            Intent intent = new Intent(AlarmReminderTimerActivity.this, AlarmReceiver.class);
            int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
            long millis = SystemClock.elapsedRealtime() + 60000;
            intent.putExtra("title",SystemClock.elapsedRealtime());
            intent.putExtra("type",type);
            intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            PendingIntent pintent = PendingIntent.getBroadcast( context, code, intent, flag);
            AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                    if (manager.canScheduleExactAlarms()){
                        manager.setExactAndAllowWhileIdle( type,  millis, pintent );
                        Toast.makeText(AlarmReminderTimerActivity.this,
                                "setExactAndAllowWhileIdle > S",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
                        manager.set( type,  millis, pintent );
                        Toast.makeText(AlarmReminderTimerActivity.this,
                                "set",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    manager.setExactAndAllowWhileIdle( type,  millis, pintent );
                    Toast.makeText(AlarmReminderTimerActivity.this,
                            "setExactAndAllowWhileIdle < S",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                manager.setExact( type,  millis, pintent );
                Toast.makeText(AlarmReminderTimerActivity.this,
                        "setExact",
                        Toast.LENGTH_SHORT).show();
            }
            /** Alarm kurmanın en iyi yolu
             * bu bölümde kısa süreli alarm kurmanın en az pil tüketen her android sürümüyle uyumlu çalışan bir versiyonu tasarlandı
             * ancak bu sadece kısa süreli alarmlar için tasarlandı. örneğin 5 10 25dklık alarmlar kurmak isteniyorsa kullanılabilir
             * yani cihaz kapanıp açıldığında iptal edilecek alarmlar.
             * daha uzun süreli alarmlar için workmanager daha oturaklı olabilir. eğer çok büyük kesinlikle alarm kurulacaksa
             * RTC_WAKEUP kullanılabilir bunun içinde kullanıcıya pil tüketimi hakkında bilgi vermek elzemdir.
             * ayrıca cihaz kapanıp açıldığında alarmlar iptal olur. bunu bir yere kaydedip telefon açıldığında alarmlar tekrar kurulmalıdır
             */
        });

        binding.setRtcAlarmAfterOneMinute.setOnClickListener(v -> {
            Context context = AlarmReminderTimerActivity.this;
            int code = 123;
            int flag;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R){
                flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            }else {
                flag = PendingIntent.FLAG_UPDATE_CURRENT;
            }
            Intent intent = new Intent(AlarmReminderTimerActivity.this, AlarmReceiver.class);
            int type = AlarmManager.RTC_WAKEUP;
            long millis = System.currentTimeMillis() + 60000;
            intent.putExtra("title",System.currentTimeMillis());
            intent.putExtra("type",type);
            intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            PendingIntent pintent = PendingIntent.getBroadcast( context, code, intent, flag);
            AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                    if (manager.canScheduleExactAlarms()){
                        manager.setExactAndAllowWhileIdle( type,  millis, pintent );
                        Toast.makeText(AlarmReminderTimerActivity.this,
                                "setExactAndAllowWhileIdle > S",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));
                        manager.set( type,  millis, pintent );
                        Toast.makeText(AlarmReminderTimerActivity.this,
                                "set",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    manager.setExactAndAllowWhileIdle( type,  millis, pintent );
                    Toast.makeText(AlarmReminderTimerActivity.this,
                            "setExactAndAllowWhileIdle < S",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                manager.setExact( type,  millis, pintent );
                Toast.makeText(AlarmReminderTimerActivity.this,
                        "setExact",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}