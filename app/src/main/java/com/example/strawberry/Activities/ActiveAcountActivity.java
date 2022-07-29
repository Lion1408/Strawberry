package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityActiveAcountBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveAcountActivity extends AppCompatActivity {
    ActivityActiveAcountBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActiveAcountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.backSignIn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            finishAffinity();
        });
        binding.activeConfirm.setOnClickListener(v -> {
            loading(true);
            User user = getIntent().getParcelableExtra("Data");
            String code = binding.num1.getText().toString().trim()
                    + binding.num2.getText().toString().trim()
                    + binding.num3.getText().toString().trim()
                    + binding.num4.getText().toString().trim();
            ApiService.apiService.activeUser(user.getIdUser(), code).enqueue(new Callback<ResponseObject<User>>() {
                @Override
                public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                    if (response.isSuccessful()) {
                        databaseReference.child("users/" + "idUser" + user.getIdUser()).setValue(user);
                        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                        Constants.showToast(Constants.SUCCESS_CREAT_A_ACCOUNT, getApplicationContext());
                        finishAffinity();
                        loading(false);
                    } else {
                        Constants.showToast(Constants.ERROR_CODE, getApplicationContext());
                        loading(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                    Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                    loading(false);
                }
            });
        });
        binding.sendcode.setOnClickListener(vv -> {
            loading(true);
            User user1 = getIntent().getParcelableExtra(Constants.DATA);
            ApiService.apiService.sendCode(user1.getIdUser()).enqueue(new Callback<ResponseObject<User>>() {
                @Override
                public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                    if (response.isSuccessful()) {
                        Constants.showToast(Constants.SEND_CODE, getApplicationContext());
                        loading(false);
                    } else {
                        Constants.showToast(Constants.ERROR, getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                    Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                }
            });
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.activeConfirm.setTextColor(getResources().getColor(R.color.signin));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.activeConfirm.setTextColor(getResources().getColor(R.color.white));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

}