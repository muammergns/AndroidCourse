package com.gns.fragmentnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.gns.fragmentnavigation.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    /** View Binding
     * eski usül R.id lerle çalışmak yerine binding kullanmak daha mantıklı
     * ActivityMainBinding direk xml referans veriyor
     * yani xml içerisinde yapılan her değişiklik bu sınıfıda değiştiriyor
     */
    ActivityMainBinding binding;
    String[] pages = new String[]{"First","Second"};

    /** Fragment kullanım amacı
     * Bir uygulamaya kaydırmalı gezinme eklemek için, her ekran için bir Fragment uygulayın,
     * UI düzenine bir ViewPager yerleştirin ve ViewPager'ı bir FragmentPagerAdapter'a bağlayın.
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //bir görünüm oluştur
        View mainView = binding.getRoot();
        //bu görünümü aktiviteye bağla
        setContentView(mainView);

        createClassicFragment();
        //createPagerFragment();


        binding.floatingActionButton.setOnClickListener(view ->{
            Snackbar.make(view,"My Own Action",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        });

    }

    private void createPagerFragment(){
        Pager pager = new Pager(this,pages);
        binding.viewPager2.setAdapter(pager);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(pages[position]);
            }
        }).attach();
    }

    private void createClassicFragment(){

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(pages[0]));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(pages[1]));

        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout,firstFragment)
                .commit();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabLayout.getSelectedTabPosition()){
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,firstFragment)
                                .commit();
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,secondFragment)
                                .commit();
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }



}