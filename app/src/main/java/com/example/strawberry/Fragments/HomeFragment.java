package com.example.strawberry.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.strawberry.Adapters.PostAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        User user = getActivity().getIntent().getParcelableExtra("User");
        List<Post> list = new ArrayList<>();
//        ApiService.apiService.getAllPublicPost().enqueue(new Callback<ResponseObject<List<Post>>>() {
//            @Override
//            public void onResponse(Call<ResponseObject<List<Post>>> call, Response<ResponseObject<List<Post>>> response) {
//                if (response.isSuccessful()) {
//                    for (int i = 0; i < response.body().getData().size(); ++i) {
//                        Post post = response.body().getData().get(i);
//                        post.setItemType(1);
//                        list.add(post);
//                        System.out.println(post.toString());
//                    }
//                    RecyclerView recyclerView = view.findViewById(R.id.recy_post);
//                    PostAdapter postAdapter = new PostAdapter(list, getContext());
//                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                    postAdapter.PostOnClick(new PostOnClick() {
//                        @Override
//                        public void OnClickAvt() {
//                            //startActivity(new Intent(getContext(), ProfileUserActivity.class));
//                        }
//                    });
//                    recyclerView.setAdapter(postAdapter);
//                } else {
//                    Constants.showToast(Constants.ERROR, getContext());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseObject<List<Post>>> call, Throwable t) {
//                Constants.showToast(Constants.ERROR_INTERNET, getContext());
//            }
//        });
        return view;
    }
}