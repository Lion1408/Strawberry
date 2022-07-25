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
        Post post = getIntent().getParcelableExtra("Data");
        binding.username.setText(post.getFullName());
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
//        List <Data> list = new ArrayList<>();
//        Data dt1 = new Data();
//        dt1.setItemType(2);
//        dt1.setUser(data1.getUser());
//        dt1.setIdLog(data1.getIdLog());
//        Data dt2 = new Data();
//        dt2.setItemType(3);
//        dt2.setUser(data1.getUser());
//        dt2.setIdLog(data1.getIdLog());
//        Data dt3 = new Data();
//        dt3.setItemType(0);
//        dt3.setUser(data1.getUser());
//        dt3.setIdLog(data1.getIdLog());
//        list.add(dt1);
//        list.add(dt2);
//        list.add(dt3);
//        RecyclerView recyclerView = findViewById(R.id.recy_profile_user);
//        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
//        viewAdapter.PostOnClick(new PostOnClick() {
//            @Override
//            public void OnClickAvt(Data data1) {
//
//            }
//
//            @Override
//            public void OnClickPost(Data data) {
//                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//                intent.putExtra("Data",  data);
//                startActivity(intent);
//                finish();
//            }
//        });
//        viewAdapter.setInforUserOnClick(new InforUserOnClick() {
//            @Override
//            public void OnClickImageVideo() {
//                startActivity(new Intent(getApplicationContext(), ImageVideoUserActivity.class));
//            }
//
//            @Override
//            public void OnClickInfor() {
//                startActivity(new Intent(getApplicationContext(), InforUserActivity.class));
//            }
//
//            @Override
//            public void OnClickFriend() {
//
//            }
//        });
//        viewAdapter.setOnClickUpPost(new OnClickUpPost() {
//            @Override
//            public void onClick() {
//                startActivity(new Intent(getApplicationContext(), UpPostActivity.class));
//            }
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        ApiService.apiService.getAllPostUser(data1.getUser().getIdUser()).enqueue(new Callback<ResponseObject<List<Data>>>() {
//            @Override
//            public void onResponse(Call<ResponseObject<List<Data>>> call, Response<ResponseObject<List<Data>>> response) {
//                if (response.isSuccessful()) {
//                    for (int i = 0; i < response.body().getData().size(); ++i) {
//                        Data data = response.body().getData().get(i);
//                        data.setItemType(1);
//                        data.setIdLog(data1.getIdLog());
//                        list.add(data);
//                    }
//                    recyclerView.setAdapter(viewAdapter);
//                } else {
//                    Constants.showToast(Constants.ERROR, getApplicationContext());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseObject<List<Data>>> call, Throwable t) {
//                Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
//            }
//        });
    }
}