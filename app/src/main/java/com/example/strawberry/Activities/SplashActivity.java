package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.UserDTO;
import com.example.strawberry.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        UserDTO userDTO = new UserDTO(
                email,
                password,
                email
        );
        Handler handler = new Handler();
        if (email.length() == 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                }
            }, 1400);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ApiService.apiService.checkLogin(userDTO).enqueue(new Callback<ResponseObject<Data>>() {
                        @Override
                        public void onResponse(Call<ResponseObject<Data>> call, Response<ResponseObject<Data>> response) {
                            if (response.isSuccessful()) {
                                Data data = response.body().getData();
                                data.setIdLog(data.getUser().getIdUser());
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                intent.putExtra("Data", data);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                Constants.showToast(Constants.ERROR_LOGIN, SplashActivity.this);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseObject<Data>> call, Throwable t) {
                            Constants.showToast(Constants.ERROR_INTERNET, getApplicationContext());
                        }
                    });
                }
            }, 1400);
        }
    }
}