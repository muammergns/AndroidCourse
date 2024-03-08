package com.gns.androidcourse.fragmentnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gns.androidcourse.R;
import com.gns.androidcourse.databinding.FragmentFirstChildBinding;


public class FirstChildFragment extends Fragment {



    public FirstChildFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentFirstChildBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            //NavDirections directions = FirstChildFragmentDirections.actionFirstChildToSecondChild();
            FirstChildFragmentDirections.ActionFirstChildFragmentToSecondChildFragment directions =
                    FirstChildFragmentDirections.actionFirstChildFragmentToSecondChildFragment();
            directions.setAge(50);
            Navigation.findNavController(v).navigate(directions);
        });
    }
}