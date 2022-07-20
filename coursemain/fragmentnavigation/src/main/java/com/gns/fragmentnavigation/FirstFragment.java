package com.gns.fragmentnavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gns.fragmentnavigation.databinding.FragmentFirstBinding;


/**
 * Fragment için bir sınıf oluşturulur ve Fragment extend edilir
 * onCreateView methodu eklenip dönüş değeri aşağıdaki gibi değiştirilir
 * bir layout oluşturulur, oluşturulan layout sınıfa bağlanır
 * ardından bu sınıf aktiviteye bağlanır
 * istenen yerde bu görünümler gösterilip çalışılabilir
 *
 */
public class FirstFragment extends Fragment {
    FragmentFirstBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        binding.textView.setText("First Example");
        return binding.getRoot();
    }
}
