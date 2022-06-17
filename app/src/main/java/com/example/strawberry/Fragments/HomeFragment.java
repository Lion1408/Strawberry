package com.example.strawberry.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.strawberry.Activities.PostActivity;
import com.example.strawberry.Activities.ProfileUserActivity;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Image;
import com.example.strawberry.Model.Reaction;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.Model.Video;
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
        List<Data> list = new ArrayList<>();
        list.add(new Data());
        list.get(0).setItemType(0);
        User user1 = new User();
        user1.setLinkAvt("");
        list.get(0).setUser(user1);
        ApiService.apiService.getAllPublicPost().enqueue(new Callback<ResponseObject<List<Data>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Data>>> call, Response<ResponseObject<List<Data>>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); ++i) {
                        Data data = response.body().getData().get(i);
                        data.setItemType(1);
                        list.add(data);
                    }
                    RecyclerView recyclerView = view.findViewById(R.id.recy_post);
                    ViewAdapter viewAdapter = new ViewAdapter(list, getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    viewAdapter.PostOnClick(new PostOnClick() {
                        @Override
                        public void OnClickAvt(User user1) {
                            Intent intent = new Intent(getContext(), ProfileUserActivity.class);
                            intent.putExtra("Data", user1);
                            startActivity(intent);
                        }

                        @Override
                        public void OnclickPost(Data data) {
                            Intent intent = new Intent(getContext(), PostActivity.class);
                            intent.putExtra("Data",  data);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(viewAdapter);
                } else {
                    Constants.showToast(Constants.ERROR, getContext());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Data>>> call, Throwable t) {
                Constants.showToast(Constants.ERROR_INTERNET, getContext());
            }
        });
        return view;
    }
}