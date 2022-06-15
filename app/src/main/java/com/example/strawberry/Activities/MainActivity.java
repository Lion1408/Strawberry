package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.strawberry.Adapters.StrawberryAdapter;

import com.example.strawberry.Fragments.HomeFragment;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StrawberryAdapter adapter = new StrawberryAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpaper.setAdapter(adapter);
        binding.bottomNav.add(new MeowBottomNavigation.Model(0, R.drawable.ic_home));
        binding.bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.ic_watch));
        binding.bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.ic_group));
        binding.bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.ic_notification));
        binding.bottomNav.add(new MeowBottomNavigation.Model(4, R.drawable.ic_menu));
        User user1 = getIntent().getParcelableExtra("User");
        Intent intent = new Intent(MainActivity.this, HomeFragment.class);
        intent.putExtra("User", (Parcelable) user1);
        binding.bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                binding.viewpaper.setCurrentItem(item.getId());
            }
        });
        binding.bottomNav.setOnReselectListener(v -> {});
        binding.bottomNav.setOnClickMenuListener(v -> {});
        binding.bottomNav.show(0, true);
        binding.bottomNav.setCount(3, "9");
        binding.viewpaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0 :
                        binding.bottomNav.show(0, true);
                        break;
                    case 1 :
                        binding.bottomNav.show(1, true);
                        break;
                    case 2:
                        binding.bottomNav.show(2, true);
                        break;
                    case 3 :
                        binding.bottomNav.show(3, true);
                        break;
                    case 4:
                        binding.bottomNav.show(4, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}