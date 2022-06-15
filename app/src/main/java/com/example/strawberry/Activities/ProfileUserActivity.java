package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.Data;
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
        User user = getIntent().getParcelableExtra("User");
        binding.username.setText(user.getFullName());
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        List <Data> list = new ArrayList<>();
        list.add(new Data());
        list.get(0).setItemType(2);
        list.get(0).setUser(user);
        list.add(new Data());
        list.get(1).setItemType(3);
        ApiService.apiService.getAllPostUser(user.getIdUser()).enqueue(new Callback<ResponseObject<List<Data>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Data>>> call, Response<ResponseObject<List<Data>>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); ++i) {
                        Data data = response.body().getData().get(i);
                        data.setItemType(1);
                        list.add(data);
                        RecyclerView recyclerView = findViewById(R.id.recy_profile_user);
                        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(viewAdapter);
                    }
                } else {
                    Constants.showToast(Constants.ERROR, getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Data>>> call, Throwable t) {
                Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
            }
        });
    }
}