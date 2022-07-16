package com.example.strawberry.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Define.RealPathUtil;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.ListImage;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.databinding.ActivityUpPostBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpPostActivity extends AppCompatActivity {
    ActivityUpPostBinding binding;
    private static final int PICK_IMAGES_CODE = 0;
    private Integer pos = 0;
    private List<Uri> listImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        binding.post.setOnClickListener(v -> {
//            Map <String, String> map = new HashMap<>();
//            map.put("access", "PUBLIC");
//            map.put("contentPost", binding.content.getText().toString().trim());
            Uri uri = listImages.get(0);
            String realPathUtil = RealPathUtil.getRealPath(this, uri);
            File file = new File(realPathUtil);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part mul = MultipartBody.Part.createFormData("fileImages", file.getName(), requestBody);
            ApiService.apiService.upPost(1, mul).enqueue(new Callback<RequestBody>() {
                @Override
                public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                    if (response.isSuccessful()) {
                        finish();
                    } else {
                        Constants.showToast(Constants.ERROR, getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<RequestBody> call, Throwable t) {
                    Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                }
            });
        });

        binding.selectImage.setOnClickListener(v -> {
            pickImages();
        });

        binding.imageview.setOnClickListener(v -> {
            pickImages();
        });

        binding.next.setOnClickListener(v -> {
            binding.imageview.setImageURI(listImages.get((pos + 1) % listImages.size()));
            pos = (pos + 1) % listImages.size();
        });

        binding.previous.setOnClickListener(v -> {
            binding.imageview.setImageURI(listImages.get((pos - 1 + listImages.size()) % listImages.size()));
            pos = (pos - 1 + listImages.size()) % listImages.size();
        });
    }

    private void pickImages() {
        listImages.clear();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_CODE)  {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    // pick multiple images
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        listImages.add(uri);
                        binding.imageview.setImageURI(listImages.get(0));
                    }
                } else {
                    Uri uri = data.getData();
                    listImages.add(uri);
                    binding.imageview.setImageURI(listImages.get(0));
                }

            }
        }
        if (listImages.size() != 0) {
            binding.selectImage.setVisibility(View.GONE);
            if (listImages.size() > 1) {
                binding.previous.setVisibility(View.VISIBLE);
                binding.next.setVisibility(View.VISIBLE);
                binding.imageview.setVisibility(View.VISIBLE);
            } else {
                binding.imageview.setVisibility(View.VISIBLE);
            }
        } else {
            binding.selectImage.setVisibility(View.VISIBLE);
            binding.imageview.setVisibility(View.GONE);
        }
    }
}