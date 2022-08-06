package com.example.strawberry.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.strawberry.Fragments.GroupFragment;
import com.example.strawberry.Fragments.HomeFragment;
import com.example.strawberry.Fragments.MenuFragment;
import com.example.strawberry.Fragments.NotificationFragment;
import com.example.strawberry.Fragments.WatchFragment;

public class StrawberryAdapter extends FragmentStatePagerAdapter {


    public StrawberryAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return new HomeFragment();
            case 1 :
                return new WatchFragment();
            case 2:
                return new NotificationFragment();
            case 3 :
                return new MenuFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
