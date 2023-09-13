package com.gns.trendviewer;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    SurfaceView surfaceView;
    SurfaceHolder sh;
    Paint paint;
    Paint grid;
    Canvas canvas;
    Bitmap bitmap;
    @ColorInt int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);
        //surfaceView.setBackgroundColor(Color.WHITE);
        sh = surfaceView.getHolder();
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        grid = new Paint();
        grid.setColor(Color.GRAY);
        grid.setAntiAlias(true);
        grid.setStyle(Paint.Style.FILL_AND_STROKE);
        grid.setStrokeWidth(1);
        grid.setTextSize(50);
        findViewById(R.id.button).setOnClickListener(view -> {
            if (canvas!=null){
                canvas.drawLine(0,surfaceView.getHeight(),surfaceView.getWidth(),0,paint);
            }
            Canvas c = sh.lockCanvas();
            c.drawColor(color);
            c.drawBitmap(bitmap,0,0,null);
            sh.unlockCanvasAndPost(c);
        });
        sh.addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("ResourceType")
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

                bitmap = Bitmap.createBitmap(surfaceView.getWidth(),surfaceView.getHeight(),Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                float dx = Integer.valueOf(surfaceView.getWidth()).floatValue()/10;
                float dy = Integer.valueOf(surfaceView.getHeight()).floatValue()/10;
                for (int i=1;i<10;i++){
                    float k = Integer.valueOf(i).floatValue();
                    float fx = k*dx;
                    float fy = k*dy;
                    int x = Float.valueOf(fx).intValue();
                    int y = Float.valueOf(fy).intValue();
                    canvas.drawLine(0,y,surfaceView.getWidth(),y,grid);
                    canvas.drawLine(x,0,x,surfaceView.getHeight(),grid);
                    canvas.drawText(String.valueOf(i),Float.valueOf(dx/4).intValue(),y,grid);
                    canvas.drawText(String.valueOf(i),x,Float.valueOf(dy/2).intValue(),grid);
                }
                Canvas c = surfaceHolder.lockCanvas();
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = getTheme();
                theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
                color = typedValue.data;
                c.drawColor(color);
                c.drawBitmap(bitmap,0,0,null);
                surfaceHolder.unlockCanvasAndPost(c);
                Log.d(TAG, "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
    }

}