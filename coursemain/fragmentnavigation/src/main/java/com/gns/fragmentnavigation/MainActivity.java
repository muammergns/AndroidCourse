package com.gns.fragmentnavigation;

import androidx.appcompat.app.AppCompatActivity;

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
    String[] pages = new String[]{"First","Second","Third"};

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


        createNavigationFragment();
        //createClassicFragment();
        //createPagerFragment();

        binding.floatingActionButton.setOnClickListener(view ->{
            Snackbar.make(view,"My Own Action",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        });

    }

    private void createNavigationFragment(){
        binding.frameLayout.setVisibility(View.GONE);
        binding.viewPager2.setVisibility(View.GONE);
    }

    private void createPagerFragment(){
        binding.frameLayout.setVisibility(View.GONE);
        binding.fragmentContainerView.setVisibility(View.GONE);
        Pager pager = new Pager(this,pages);
        binding.viewPager2.setAdapter(pager);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> {
            tab.setText(pages[position]);
        }).attach();
    }

    /**
     * klasik fragment oluştuma yöntemi çok sorunlu bunu tekrar kullanmayacağım
     * sorunun ne olduğunu bulamadım ancak pager ve navigation çok iyi çalışıyor
     */
    private void createClassicFragment(){
        binding.viewPager2.setVisibility(View.GONE);
        binding.fragmentContainerView.setVisibility(View.GONE);
        for (String s : pages){
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(s));
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (binding.tabLayout.getSelectedTabPosition()){
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,new FirstFragment())
                                .commit();
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,new SecondFragment())
                                .commit();
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,new ThirdFragment())
                                .commit();
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }



}