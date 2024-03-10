package com.gns.androidcourse.workmanager;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.gns.androidcourse.R;

import java.util.concurrent.TimeUnit;

public class WorkManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Data data = new Data.Builder().putInt("intKey",1).build();

        //burada layout ile işlem yapılmadığına emin olmak gerekir
        Constraints constraints = new Constraints.Builder()
                //internete bağlıysa çalıştır
                //.setRequiredNetworkType(NetworkType.CONNECTED)
                //şarja bağlıysa çalıştır
                //.setRequiresCharging(true)
                .build();
        /*
        //workrequest ile yapılacak işlemlerin koşulları belirtiliyor
        //çalışma sınıfımızın tanımlaması yapılıyor
        //OneTimeWorkRequest bir defaya mahsus çalıştırmak için kullanılır
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(RefreshDatabase.class)
                //sınırlar bu objeye dahil olacak
                .setConstraints(constraints)
                //datalar bu objeye dahil olacak
                .setInputData(data)
                //çalışma için gecikme eklenebiliyor
                //.setInitialDelay(5, TimeUnit.MINUTES)
                //taglara göre ayrıştırılıyor buna bakılacak
                //.addTag("myTag")
                .build();
        //son olarak görevi başlatıyoruz

         */

        //minimum süre 15 dakika ve tam nokta atışı süre geçerli değil
        WorkRequest workRequest = new PeriodicWorkRequest.Builder(RefreshDatabase.class,15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .build();


        WorkManager.getInstance(this).enqueue(workRequest);

        int mySavedNumber = getSharedPreferences("com.gns.workmanager", Context.MODE_PRIVATE).getInt("mySavedNumber",0);
        System.out.println(mySavedNumber);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() == WorkInfo.State.RUNNING) System.out.println("RUNNING");
                else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) System.out.println("SUCCEEDED");
                else if (workInfo.getState() == WorkInfo.State.FAILED) System.out.println("FAILED");
                else System.out.println("onChanged");
            }
        });

    }
}