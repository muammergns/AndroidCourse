package com.example.animatorandanimations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.animatorandanimations.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Animation alphaAnimation;
    Animation rotateAnimation;
    Animation scaleAnimation;
    Animation translateAnimation;
    AnimationSet animationSetSameTime;
    Animation fadeIn,fadeOut;
    AnimationSet alphaFadeInAndOut;
    @Override
    protected void onStart() {
        super.onStart();
        float fromAlpha = 0f;
        float toAlpha = 1f;
        alphaAnimation = new AlphaAnimation(fromAlpha,toAlpha);
        alphaAnimation.setDuration(1000);
        float fromDegrees = 0f;
        float toDegrees = 360f;
        rotateAnimation = new RotateAnimation(
                fromDegrees,
                toDegrees,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f
        );
        rotateAnimation.setDuration(1000);
        scaleAnimation = new ScaleAnimation(
                1.0f, 2.0f, 1.0f, 2.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(1000);
        translateAnimation = new TranslateAnimation(0,100,0,100);
        translateAnimation.setDuration(1000);
        //false parametresi interpolatorlerini paylaşmamasını sağlar
        //normalde animasyonlar lineer ilerler
        //interpolator ile örneğin sinüsoidal hareket yapılabilir
        animationSetSameTime = new AnimationSet(false);
        animationSetSameTime.addAnimation(alphaAnimation);
        animationSetSameTime.addAnimation(rotateAnimation);
        animationSetSameTime.addAnimation(scaleAnimation);
        animationSetSameTime.addAnimation(translateAnimation);
        fadeIn = new AlphaAnimation(0,1);
        fadeOut = new AlphaAnimation(1,0);
        fadeIn.setDuration(1000);
        fadeOut.setDuration(1000);
        fadeIn.setStartOffset(1000);
        fadeIn.setRepeatCount(3);
        fadeOut.setRepeatCount(3);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeOut.setInterpolator(new AccelerateInterpolator());
        alphaFadeInAndOut = new AnimationSet(false);
        alphaFadeInAndOut.addAnimation(fadeIn);
        alphaFadeInAndOut.addAnimation(fadeOut);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //alpha animasyon güzel çalışıyor tıklama işlevini ya da buraya tıklaman gerekiyor gibi işlevler için kullanılabilir
        binding.alphaAnimButton.setOnClickListener(v -> binding.alphaAnimButton.startAnimation(alphaAnimation));
        //rotate animation çok kötü merkezi sol üst köşe aldığı için iyice çirkin görünüyor
        //parametrelerde pivot değeri ile ayarlanabiliyor ama uygulama açıldıktan sonra ekran boyutu değişirse tamamen kullanışsız olur
        //ayrıca RotateAnimation.RELATIVE_TO_SELF ile yapılabiliyormuş bunu keşfettim
        binding.rotateAnimButton.setOnClickListener(v -> binding.rotateAnimButton.startAnimation(rotateAnimation));
        //rotate animasyon gibi scale içinde ScaleAnimation.RELATIVE_TO_SELF kullanarak boyut büyümesini merkeze alabiliriz
        binding.scaleAnimButton.setOnClickListener(v -> binding.scaleAnimButton.startAnimation(scaleAnimation));
        //görünümün pozisyonunu değiştiriyor piksel mantığı pek bana göre değil ama yinede kaydırma animasyonlarında kullanılabilir
        binding.translateAnimButton.setOnClickListener(v -> binding.translateAnimButton.startAnimation(translateAnimation));
        //tüm animasyonlar aynı anda tek butonda çalışır
        binding.animationsetSameTimeButton.setOnClickListener(v -> binding.animationsetSameTimeButton.startAnimation(animationSetSameTime));

        binding.sequentialAnimButton.setOnClickListener(v -> {
            binding.sequentialAnimButton.startAnimation(alphaAnimation);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.sequentialAnimButton.startAnimation(rotateAnimation);
                    rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            binding.sequentialAnimButton.startAnimation(scaleAnimation);
                            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override public void onAnimationStart(Animation animation) {}
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    binding.sequentialAnimButton.startAnimation(translateAnimation);
                                    //son animasyon olduğu için translate animasyon listener oluşturulmadı
                                }
                                @Override public void onAnimationRepeat(Animation animation) {}
                            });
                        }
                        @Override public void onAnimationRepeat(Animation animation) {}
                    });
                }
                @Override public void onAnimationRepeat(Animation animation) {}
            });
        });

        //animationset ile sıralı çalışma denedim ama istediğim gibi çalışmadı. en güzeli listener ile çalışıyor.
        binding.blinkAnimButton.setOnClickListener(v -> binding.blinkAnimButton.startAnimation(alphaFadeInAndOut));
    }
}