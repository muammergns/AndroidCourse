package com.gns.fragmentnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gns.fragmentnavigation.databinding.FragmentFirstChildBinding;


public class FirstChild extends Fragment {


    public FirstChild() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentFirstChildBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstChildBinding.inflate(inflater,container,false);//attachToParent false olmalı. true olursa çöküyor
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    /**
     * en güvenlisi onviewcreated fonksiyonu içinde yazmaktır
     * tüm görünüm işlemleri tamamlandıktan sonra butonlar vs aktif olacaktır
     * zaten bize bir view döndürüyor
     * ama biz zaten binding kullanıyoruz bir sorun olmayacak
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goToSecond.setOnClickListener(v -> {
            //burası yöntem 1 kotlinde burada constructor olarak argümanları istiyor
            //javada durum biraz farklı getter ve setter oluşturuyor
            //NavDirections directions = FirstChildDirections.actionFirstChildToSecondChild();
            FirstChildDirections.ActionFirstChildToSecondChild directions =
                    FirstChildDirections.actionFirstChildToSecondChild();
            directions.setAge(50);
            Navigation.findNavController(v).navigate(directions);
        });
    }
}