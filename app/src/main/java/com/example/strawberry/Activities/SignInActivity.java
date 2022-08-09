package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.Model.UserDTO;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivitySignInBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners() {
        binding.signin.setOnClickListener(v -> {
            loading(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSuccess();
                }
            }, 800);
        });
        binding.showHidepassword.setOnClickListener(v -> {
            if (binding.password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.showHidepassword.setBackgroundResource(R.drawable.ic_eyehide);
                binding.password.setSelection(binding.password.getText().length());
            } else {
                binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.showHidepassword.setBackgroundResource(R.drawable.ic_eyeshow);
                binding.password.setSelection(binding.password.getText().length());
            }
        });
        binding.iconSavepassword.setOnClickListener(v -> {
            if (binding.iconSavepassword.getContentDescription().equals("hide")) {
                binding.iconSavepassword.setBackgroundResource(R.drawable.ic_checkshow);
                binding.iconSavepassword.setContentDescription("show");
            } else {
                binding.iconSavepassword.setBackgroundResource(R.drawable.ic_checkhide);
                binding.iconSavepassword.setContentDescription("hide");
            }
        });
        binding.textSavepassword.setOnClickListener(v -> {
            if (binding.iconSavepassword.getContentDescription().equals("hide")) {
                binding.iconSavepassword.setBackgroundResource(R.drawable.ic_checkshow);
                binding.iconSavepassword.setContentDescription("show");
            } else {
                binding.iconSavepassword.setBackgroundResource(R.drawable.ic_checkhide);
                binding.iconSavepassword.setContentDescription("hide");
            }
        });
        binding.signup.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            finish();
        });
        binding.forgetpassword.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            finishAffinity();
        });
    }
    private void isSuccess() {
        if (binding.email.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_USER_NAME, getApplicationContext());
            loading(false);
        } else if (binding.password.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_PASSWORD, getApplicationContext());
            loading(false);
        } else {
            UserDTO userDTO = new UserDTO(
                    binding.email.getText().toString().trim(),
                    binding.password.getText().toString().trim()
            );
            ApiService.apiService.checkLogin(userDTO).enqueue(new Callback<ResponseObject<Data>>() {
                @Override
                public void onResponse(Call<ResponseObject<Data>> call, Response<ResponseObject<Data>> response) {
                    if (response.isSuccessful()) {
                        User user = response.body().getData().getUser();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtra("User", user);
                        databaseReference.child("users/idUser" + user.getIdUser() + "/status").setValue(true);
                        startActivity(intent);
                        if (binding.iconSavepassword.getContentDescription().equals("show")) {
                            SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                            editor.putString("email", binding.email.getText().toString().trim());
                            editor.putString("password", binding.password.getText().toString().trim());
                            editor.commit();
                            loading(false);
                        } else {
                            SharedPreferences.Editor editor = getSharedPreferences("Data", Context.MODE_PRIVATE).edit();
                            editor.clear();
                            editor.commit();
                        }
                        finishAffinity();
                    } else {
                        loading(false);
                        Constants.showToast(Constants.ERROR_LOGIN, SignInActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject<Data>> call, Throwable t) {
                    loading(false);
                    Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                }
            });
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.signin.setTextColor(getResources().getColor(R.color.signin));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.signin.setTextColor(getResources().getColor(R.color.white));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

}