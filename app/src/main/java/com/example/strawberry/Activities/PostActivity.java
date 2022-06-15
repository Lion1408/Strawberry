package com.example.strawberry.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.strawberry.Model.Data;
import com.example.strawberry.databinding.ActivityPostBinding;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    ActivityPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        List<Data> list = new ArrayList<>();
        Integer idPost = getIntent().getParcelableExtra("Data");

        binding.contenPost.setText("Bài viết của " + data.getUser().getFullName());
        list.add(data);
//        RecyclerView recyclerView = findViewById(R.id.recy_user_post);
//        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(viewAdapter);
    }
}