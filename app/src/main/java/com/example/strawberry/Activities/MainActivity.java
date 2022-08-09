package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.strawberry.Adapters.StrawberryAdapter;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Fragments.HomeFragment;
import com.example.strawberry.Fragments.MenuFragment;
import com.example.strawberry.Fragments.NotificationFragment;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityMainBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    User user = new User();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user1 = getIntent().getParcelableExtra("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child("users/idUser" + user1.getIdUser()).getValue(User.class);
                Intent intentHome = new Intent(getApplicationContext(), HomeFragment.class);
                intentHome.putExtra("User", user);
                Intent intentMenu = new Intent(getApplicationContext(), MenuFragment.class);
                intentMenu.putExtra("User", user);
                Intent intentNoti = new Intent(getApplicationContext(), NotificationFragment.class);
                intentNoti.putExtra("User", user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        StrawberryAdapter adapter = new StrawberryAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpaper.setAdapter(adapter);
        binding.bottomNav.add(new MeowBottomNavigation.Model(0, R.drawable.ic_home));
        binding.bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.ic_watch));
        binding.bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.ic_notification));
        binding.bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.ic_menu));
        binding.bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                binding.viewpaper.setCurrentItem(item.getId());
            }
        });
        binding.bottomNav.setOnReselectListener(v -> {});
        binding.bottomNav.setOnClickMenuListener(v -> {});
        binding.bottomNav.show(0, true);
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
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child("users/idUser" + user.getIdUser() + "/status").setValue(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user.getIdUser() != null) {
            databaseReference.child("users/idUser" + user.getIdUser() + "/status").setValue(true);
        }
    }
}