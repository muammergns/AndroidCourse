package com.gns.androidcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gns.androidcourse.alarmremindertimer.AlarmReminderTimerActivity;
import com.gns.androidcourse.animatorandanimations.AnimatorAndAnimationsActivity;
import com.gns.androidcourse.databinding.ActivityMainBinding;
import com.gns.androidcourse.fragmentnavigation.FragmentNavigationActivity;
import com.gns.androidcourse.notificationforegroundservice.NotificationForegroundServiceActivity;
import com.gns.androidcourse.recyclerviewcommands.RecyclerViewCommandsActivity;
import com.gns.androidcourse.roomdatabase.RoomDatabaseActivity;
import com.gns.androidcourse.sqlitedatabase.SqliteDatabaseActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.alarmremindertimer.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, AlarmReminderTimerActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.fragmentnavigation.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, FragmentNavigationActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.animatorandanimations.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, AnimatorAndAnimationsActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.notificationforegroundservice.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, NotificationForegroundServiceActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.recyclerviewcommands.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, RecyclerViewCommandsActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.roomdatabase.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, RoomDatabaseActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.sqlitedatabase.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, SqliteDatabaseActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}