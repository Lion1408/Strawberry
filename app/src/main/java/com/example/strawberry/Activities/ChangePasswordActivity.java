package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.Password;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityChangeAvatarBinding;
import com.example.strawberry.databinding.ActivityChangePasswordBinding;
import com.example.strawberry.databinding.ActivityInforUserBinding;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = getIntent().getParcelableExtra("User");
        binding.backSignIn.setOnClickListener(v -> {
            finish();
        });

        binding.signup.setOnClickListener(v -> {
            loading(true);
            if (binding.password.getText().toString().trim().isEmpty()) {
                Constants.showToast(Constants.ENTER_PASSWORD, getApplicationContext());
                loading(false);
            } else if (binding.confirmPassword.getText().toString().trim().isEmpty()){
                Constants.showToast(Constants.ENTER_CONFIRM_PASSWORD, getApplicationContext());
                loading(false);
            } else if (!binding.confirmPassword.getText().toString().trim().equals(binding.password.getText().toString().trim())) {
                Constants.showToast(Constants.PASSWORD_NOT_SAME, getApplicationContext());
                loading(false);
            } else {
                String oldPass = binding.email.getText().toString().trim();
                String newPass = binding.password.getText().toString().trim();
                Password password = new Password();
                password.setOldPassword(oldPass);
                password.setNewPassword(newPass);
                ApiService.apiService.changePassword(user.getIdUser(), password).enqueue(new Callback<ResponseObject<Password>>() {
                    @Override
                    public void onResponse(Call<ResponseObject<Password>> call, Response<ResponseObject<Password>> response) {
                        if (response.isSuccessful()) {
                            loading(false);
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            finishAffinity();
                            Constants.showToast("Thay đổi mật khẩu thành công!", getApplicationContext());
                            SharedPreferences.Editor editor = getSharedPreferences("Data", Context.MODE_PRIVATE).edit();
                            editor.clear();
                            editor.commit();
                        } else {
                            Constants.showToast("Mật khẩu cũ không đúng!", getApplicationContext());
                            loading(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseObject<Password>> call, Throwable t) {
                        Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                        loading(false);
                    }
                });
            }
        });
    }
    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.signup.setTextColor(getResources().getColor(R.color.signin));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.signup.setTextColor(getResources().getColor(R.color.white));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}