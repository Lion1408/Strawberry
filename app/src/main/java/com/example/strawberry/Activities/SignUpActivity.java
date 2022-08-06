package com.example.strawberry.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivitySignUpBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.backSignIn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            finishAffinity();
        });
        binding.signup.setOnClickListener(v -> {
            loading(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSuccess();
                }
            }, 500);
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

        binding.showHideConfirmpassword.setOnClickListener(v -> {
            if (binding.confirmPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.showHideConfirmpassword.setBackgroundResource(R.drawable.ic_eyehide);
                binding.confirmPassword.setSelection(binding.password.getText().length());
            } else {
                binding.confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.showHideConfirmpassword.setBackgroundResource(R.drawable.ic_eyeshow);
                binding.confirmPassword.setSelection(binding.confirmPassword.getText().length());
            }
        });

    }

    private void isSuccess() {
        if (binding.firstname.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_FIRST_NAME, getApplicationContext());
            loading(false);
        } else if (binding.lastname.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_LAST_NAME, getApplicationContext());
            loading(false);
        } else if (binding.email.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_USER_NAME, getApplicationContext());
            loading(false);
        } else if (binding.password.getText().toString().trim().isEmpty()) {
            Constants.showToast(Constants.ENTER_PASSWORD, getApplicationContext());
            loading(false);
        } else if (binding.confirmPassword.getText().toString().trim().isEmpty()){
            Constants.showToast(Constants.ENTER_CONFIRM_PASSWORD, getApplicationContext());
            loading(false);
        } else if (!binding.confirmPassword.getText().toString().trim().equals(binding.password.getText().toString().trim())) {
            Constants.showToast(Constants.PASSWORD_NOT_SAME, getApplicationContext());
            loading(false);
        } else {
            User user = new User(
                    binding.firstname.getText().toString().trim() + "",
                    binding.lastname.getText().toString().trim() + "",
                    binding.firstname.getText().toString().trim() + binding.lastname.getText().toString().trim(),
                    binding.email.getText().toString().trim() + "",
                    binding.password.getText().toString().trim() + "",
                    "https://firebasestorage.googleapis.com/v0/b/strawberry-7ebce.appspot.com/o/default%2Fraf%2C750x1000%2C075%2Ct%2CFFFFFF_97ab1c12de.jpg?alt=media&token=50061099-5e61-4f7f-a2a2-1e9b0e0b7c46",
                    "https://firebasestorage.googleapis.com/v0/b/strawberry-7ebce.appspot.com/o/default%2Fcute-strawberry-seamless-pattern-vector.jpg?alt=media&token=f17a3733-d694-46bb-9daf-f7ed8c16bd86",
                    "",
                    "",
                    "",
                    "",
                    "",
                    1,
                    true
            );

            ApiService.apiService.createAccount(user).enqueue(new Callback<ResponseObject<User>>() {
                @Override
                public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), ActiveAcountActivity.class);
                        User user1 = response.body().getData();
                        user1.setLinkAvt(user.getLinkAvt());
                        user1.setLinkCover(user.getLinkCover());
                        user1.setStatus(true);
                        intent.putExtra("Data", user1);
                        startActivity(intent);
                        loading(false);
                    } else {
                        Constants.showToast(Constants.ERROR_SIGN_UP, getApplicationContext());
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
            binding.signup.setTextColor(getResources().getColor(R.color.signin));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.signup.setTextColor(getResources().getColor(R.color.white));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

}