package com.gns.androidcourse.notificationforegroundservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.gns.androidcourse.R;
import com.gns.androidcourse.databinding.ActivityNotificationForegroundServiceBinding;

import java.util.Objects;

public class NotificationForegroundServiceActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "com.gns.notificationforegroundservice";
    ActivityNotificationForegroundServiceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationForegroundServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /** Notification Channel
         * bildirim kanalları artık önemli hale geldi
         * uygulamanın bir bildirimi ne için oluşturduğunu hangi bildirimlerin hangi kanallardan geleceğini belirtmesi gerekiyor
         * kullanıcı kendi isteğine göre bu kanalları aktif pasif yapabilir ve gereksiz bildirimlerden kurtulabilir
         * bu sözde reklamları vs engelleyecek ama uygulamalar reklamlarla önemli bildirimleri aynı kanaldan veriyor zaten
         * CHANNEL_ID görünmüyor. bunu bildirim kanalının numarası gibi düşün. silerken bile ihtiyacın olacak
         * channel.setDescription(description); ile bildirimlerin neden gönderildiğini açıklayabilirsin.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        binding.showNotificationButton.setOnClickListener(v -> {
            onCreateNotification();
        });


    }

    private void onCreateNotification() {
        final int flag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE :
                PendingIntent.FLAG_UPDATE_CURRENT;

        Intent notificationIntent = new Intent(this, NotificationForegroundServiceActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flag);

        Intent actionIntent = new Intent(this, NotificationReceiver.class);
        actionIntent.putExtra("putExtra", "actionIntent");
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(this, 1, actionIntent, flag);

        Intent startProgressIntent = new Intent(this, NotificationReceiver.class);
        startProgressIntent.putExtra("putExtra", "startProgressIntent");
        PendingIntent startProgressPendingIntent = PendingIntent.getBroadcast(this, 2, startProgressIntent, flag);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSubText("setSubText")
                .setContentTitle("setContentTitle")
                .setContentText("setContentText: onCreate")
                .setContentIntent(notificationPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Action", actionPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Progress", startProgressPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = builder.build();

        /*
         * yeni güncelleme ile birlikte artık bildirimleri gönderebilmek için POST_NOTIFICATIONS izni almak gerekiyor
         * manifest dosyasına <uses-permission android:name="android.permission.POST_NOTIFICATIONS" /> eklenmeli
         * android eski versiyonlar için bu izin zaten verilmiş durumda
         * api 34 ile birlikte verilmemiş durumda olacak ve bu izni istemek gerekecek
         * izni aldıktan sonra bildirimler artık daha sorumlu bir şekilde kullanılmalı
         */
        /*


        /** ilk açılışta izin isteniyor
         * izin verilmezse yani hayıra basılmışsa
         * uygulama tekrar açıldığında bu defa tekrar izin isteniyor ve açıklama gösteriliyor
         * tekrar hayır denirse artık izin isteği gösterilmiyor
         * olması gereken izin şekli bu
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                notificationManagerCompat.notify(3, notification);
            }else{
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)){
                    Toast.makeText(NotificationForegroundServiceActivity.this,
                            "Bildirim göstermem için izin vermen lazım. yoksa bildirimleri göremezsin.",
                            Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.POST_NOTIFICATIONS},123);
            }
        }else{
            notificationManagerCompat.notify(3, notification);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==123){
            if (Objects.equals(permissions[0], Manifest.permission.POST_NOTIFICATIONS)){
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]){
                    onCreateNotification();
                }
            }
        }
    }
}