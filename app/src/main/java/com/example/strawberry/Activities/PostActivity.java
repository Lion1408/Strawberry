package com.example.strawberry.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Data;
import com.example.strawberry.R;
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
        Data data = getIntent().getParcelableExtra("Data");
        binding.contenPost.setText("Bài viết của " + data.getUser().getFullName());
        list.add(data);
        RecyclerView recyclerView = findViewById(R.id.recy_user_post);
        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Data data1) {
                Intent intent = new Intent(getApplicationContext(), ProfileUserActivity.class);
                intent.putExtra("Data", data1);
                startActivity(intent);
                finish();
            }

            @Override
            public void OnClickPost(Data data) {

            }

        });
        recyclerView.setAdapter(viewAdapter);
    }
}