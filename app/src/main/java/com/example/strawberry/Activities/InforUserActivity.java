package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityInforUserBinding;

public class InforUserActivity extends AppCompatActivity {
    ActivityInforUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInforUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}