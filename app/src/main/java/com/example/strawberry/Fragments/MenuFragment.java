package com.example.strawberry.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.strawberry.Activities.ProfileUserActivity;
import com.example.strawberry.Activities.SignInActivity;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Model.Data;
import com.example.strawberry.R;
import com.example.strawberry.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {
    FragmentMenuBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Data data = getActivity().getIntent().getParcelableExtra("Data");
        binding.logout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SignInActivity.class));
            Constants.showToast("Đăng xuất thành công!", getContext());
            getActivity().finishAffinity();
        });

        binding.profileUser.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileUserActivity.class);
            intent.putExtra("Data", data);
            startActivity(intent);
        });
        return view;
    }
}