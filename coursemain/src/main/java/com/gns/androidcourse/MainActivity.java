package com.gns.androidcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gns.androidcourse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fragmentnavigation.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.fragmentnavigation"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.notificationforegroundservice.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.notificationforegroundservice.MainActivity"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.recyclerviewcommands.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.recyclerviewcommands"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.roomdatabase.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.roomdatabase"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.sqlitedatabase.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.sqlitedatabase"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.trendviewer.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.trendviewer"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.workmanager.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.workmanager"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        binding.alarmremindertimer.setOnClickListener(v -> {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gns.androidcourse","com.gns.alarmremindertimer"));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}