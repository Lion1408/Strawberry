package com.example.strawberry.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.strawberry.Activities.ProfileUserActivity;
import com.example.strawberry.Activities.SignInActivity;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.FragmentMenuBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuFragment extends Fragment {
    FragmentMenuBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        user = getActivity().getIntent().getParcelableExtra("User");
        binding.logout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SignInActivity.class));
            Constants.showToast("Đăng xuất thành công!", getContext());
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();
            getActivity().finishAffinity();
        });
        Glide.with(binding.imgUser).load(user.getLinkAvt()).into(binding.imgUser);

        binding.profileUser.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileUserActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        });
        return view;
    }
}