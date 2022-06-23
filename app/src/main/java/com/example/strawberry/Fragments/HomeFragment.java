package com.example.strawberry.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.strawberry.Model.ResponseObject;
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
        Data data1 = getActivity().getIntent().getParcelableExtra("Data");
        List<Data> list = new ArrayList<>();
        list.add(data1);
        list.get(0).setItemType(0);
        ApiService.apiService.getAllPublicPost().enqueue(new Callback<ResponseObject<List<Data>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Data>>> call, Response<ResponseObject<List<Data>>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); ++i) {
                        Data data = response.body().getData().get(i);
                        data.setItemType(1);
                        data.setIdLog(data1.getIdLog());
                        list.add(data);
                    }
                    RecyclerView recyclerView = view.findViewById(R.id.recy_post);
                    ViewAdapter viewAdapter = new ViewAdapter(list, getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    viewAdapter.PostOnClick(new PostOnClick() {
                        @Override
                        public void OnClickAvt(Data data1) {
                            Intent intent = new Intent(getContext(), ProfileUserActivity.class);
                            intent.putExtra("Data", data1);
                            startActivity(intent);
                        }

                        @Override
                        public void OnClickPost(Data data1) {
                            Intent intent = new Intent(getContext(), PostActivity.class);
                            intent.putExtra("Data",  data1);
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