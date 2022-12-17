package com.gns.workmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefreshDatabase extends Worker {

    Context context;

    public RefreshDatabase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    /**
     * asıl iş burada yapılacak
     * Result sınıfından obje döndürmek istiyor
     * Data sınıfından bir veri alıyoruz ve bunu istediğimiz gibi kullanabiliriz
     * getInputData fonksiyonu direk olarak worker sınıfından veriyi alacak
     */
    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        int myNumber = data.getInt("intKey",0);
        refreshDatabase(myNumber);
        return Result.success();
    }

    /**
     * burada deneme amaçlı bir sayıya ekleme yapıyoruz ve database e kaydediyoruz
     */
    private void refreshDatabase(int myNumber){
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.gns.workmanager", Context.MODE_PRIVATE);
        int mySavedNumber = sharedPreferences.getInt("mySavedNumber",0);
        mySavedNumber+=myNumber;
        System.out.println(mySavedNumber);
        sharedPreferences.edit().putInt("mySavedNumber",mySavedNumber).apply();
    }
}
