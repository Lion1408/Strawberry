package com.example.strawberry.Fragments;

import android.content.Intent;
import android.media.tv.TvView;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.strawberry.Activities.ChatActivity;
import com.example.strawberry.Activities.PostActivity;
import com.example.strawberry.Activities.ProfileUserActivity;
import com.example.strawberry.Activities.UpPostActivity;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.OnClickUpPost;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        User user = getActivity().getIntent().getParcelableExtra("Data");
        List<Post> list = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recy_post);
        ViewAdapter viewAdapter = new ViewAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {
                Intent intent = new Intent(getContext(), ProfileUserActivity.class);
                intent.putExtra("Data", post);
                startActivity(intent);
            }

            @Override
            public void OnClickPost(Post post) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra("Data", post);
                System.out.println(post.toString());
                startActivity(intent);
            }
        });
        viewAdapter.setOnClickUpPost(new OnClickUpPost() {
            @Override
            public void onClick() {
                startActivity(new Intent(getContext(), UpPostActivity.class));
            }
        });
        ImageView chat = view.findViewById(R.id.chat);
        chat.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ChatActivity.class));
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Post post = new Post();
                post.setIdUser(user.getIdUser());
                post.setItemType(0);
                list.add(post);
                for (DataSnapshot i : snapshot.child("posts").getChildren()) {
                    Post post1 = i.getValue(Post.class);
                    System.out.println(post1.toString());
                    post1.setItemType(1);
                    list.add(post1);
                }
                System.out.println(list.get(1).toString());
                recyclerView.setAdapter(viewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}