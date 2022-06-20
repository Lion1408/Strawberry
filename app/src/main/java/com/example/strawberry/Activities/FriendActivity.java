package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityFriendBinding;

public class FriendActivity extends AppCompatActivity {
    ActivityFriendBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}