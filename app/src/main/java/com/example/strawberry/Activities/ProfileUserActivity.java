package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.InforUserOnClick;
import com.example.strawberry.Interfaces.OnClickUpPost;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityProfileUserBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUserActivity extends AppCompatActivity {
    ActivityProfileUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = getIntent().getParcelableExtra(Constants.DATA);
        binding.username.setText(user.getFullName());
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        List <Post> list = new ArrayList<>();
        Post item1 = new Post();
        item1.setItemType(Constants.HEAD_PROFILE_USER);
        item1.setBiography(user.getBiography());
        item1.setLinkCover(user.getLinkCover());
        item1.setFullName(user.getFullName());
        item1.setLinkAvt(user.getLinkAvt());
        list.add(item1);
        Post item2 = new Post();
        item2.setItemType(Constants.INFOR_USER);
        item2.setIdUser(user.getIdUser());
        list.add(item2);
        Post item3 = new Post();
        item3.setItemType(0);
        item3.setIdUser(user.getIdUser());
        item3.setLinkAvt(user.getLinkAvt());
        list.add(item3);
        RecyclerView recyclerView = findViewById(R.id.recy_profile_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());

        recyclerView.setAdapter(viewAdapter);
    }
}