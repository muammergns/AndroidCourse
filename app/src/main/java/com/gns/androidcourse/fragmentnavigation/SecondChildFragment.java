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
import com.gns.androidcourse.databinding.FragmentSecondChildBinding;


public class SecondChildFragment extends Fragment {



    public SecondChildFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSecondChildBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSecondChildBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goToFirst.setOnClickListener(v -> {
            NavDirections directions = SecondChildFragmentDirections.actionSecondChildFragmentToFirstChildFragment();
            Navigation.findNavController(v).navigate(directions);
        });

        if (getArguments()!=null){//argüman boş gelebilir bu yüzden kontrol ediyoruz
            int age = SecondChildFragmentArgs.fromBundle(getArguments()).getAge();//yaşı bu şekilde aldık
            binding.textView2.setText("Age: "+age);//burada yazdırdık
        }
    }

}