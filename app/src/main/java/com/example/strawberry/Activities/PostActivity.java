package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.strawberry.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {
    ActivityPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}