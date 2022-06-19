package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Adapters.ImageAdapter;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.ImageOnClick;
import com.example.strawberry.Model.Image;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityImageUserBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUserActivity extends AppCompatActivity {
    ActivityImageUserBinding binding;
    GridView gridView;
    List<Image> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gridView = findViewById(R.id.gridview);
        binding.backProfileUser.setOnClickListener(v -> {
            finish();
        });

        ApiService.apiService.getAllImageUser(1).enqueue(new Callback<ResponseObject<List<Image>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Image>>> call, Response<ResponseObject<List<Image>>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); ++i) {
                        Image image = response.body().getData().get(i);
                        list.add(image);
                    }
                }

                ImageAdapter adapter = new ImageAdapter(getApplicationContext(), list, R.layout.item_image);
                adapter.setImageOnClick(new ImageOnClick() {
                    @Override
                    public void onclickImage(String image) {
                        Dialog dialog = new Dialog(ImageUserActivity.this, R.style.Theme_Strawberry);
                        dialog.setContentView(R.layout.dialog_show_image);
                        ImageView img, backImage;
                        img = dialog.findViewById(R.id.showImage);
                        backImage = dialog.findViewById(R.id.backImage);
                        Glide.with(img).load(image).into(img);
                        backImage.setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        dialog.show();
                    }
                });
                gridView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ResponseObject<List<Image>>> call, Throwable t) {

            }
        });

    }
}