package com.gns.androidcourse.fragmentnavigation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Pager extends FragmentStateAdapter {

    private String[] pages;

    public Pager(@NonNull FragmentActivity fragmentActivity, String[] pages) {
        super(fragmentActivity);
        this.pages = pages;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
        }
        return new FirstFragment();
    }

    @Override
    public int getItemCount() {
        return pages.length;
    }
}
