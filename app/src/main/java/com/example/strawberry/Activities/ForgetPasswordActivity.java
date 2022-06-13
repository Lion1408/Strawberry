package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityForgetPasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();

    }

    private void setListeners() {
        binding.backSignIn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finishAffinity();
        });
        binding.confirmFogetpassword.setOnClickListener(v -> {
            loading(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSuccess();
                }
            }, 500);
        });
    }

    private void isSuccess() {
        if (binding.email.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_USER_NAME, getApplicationContext());
            loading(false);
        } else {
            ApiService.apiService.forgetPassword(binding.email.getText().toString().trim()).enqueue(new Callback<ResponseObject<User>>() {
                @Override
                public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                    Log.e("here", response.code() + "");
                    if (response.isSuccessful()) {
                        Constants.showToast(Constants.SUCCESS_FORGET_PASSWORD, getApplicationContext());
                        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                        finishAffinity();
                        loading(false);
                    } else {
                        Constants.showToast("Lỗi", getApplicationContext());
                        loading(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                    Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                    loading(false);
                }
            });
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.confirmFogetpassword.setTextColor(getResources().getColor(R.color.signin));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.confirmFogetpassword.setTextColor(getResources().getColor(R.color.white));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}