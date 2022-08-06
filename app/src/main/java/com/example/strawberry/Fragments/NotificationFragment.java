package com.example.strawberry.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.strawberry.Activities.MainActivity;
import com.example.strawberry.Activities.PostActivity;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotificationFragment extends Fragment {
    Boolean isFirstCall = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        User user = getActivity().getIntent().getParcelableExtra("User");
        RecyclerView recyclerView = view.findViewById(R.id.recy_noti);
        List<Post> list = new ArrayList<>();
        ViewAdapter viewAdapter = new ViewAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {

            }

            @Override
            public void OnClickPost(Post post) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra("Post", post);
                intent.putExtra("User", user);
                startActivity(intent);
            }

            @Override
            public void OnClickReact(Post post) {

            }

            @Override
            public void OnClickDelete(Post post) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot i : snapshot.child("notifications").getChildren()) {
                    Post post = i.getValue(Post.class);
                    post.setItemType(Constants.NOTIFICATION);
                    User user = snapshot.child("users/idUser" + post.getIdUser()).getValue(User.class);
                    post.setLinkAvt(user.getLinkAvt());
                    list.add(post);
                }
                Collections.sort(list.subList(0, list.size()), new Comparator<Post>() {
                    @Override
                    public int compare(Post x, Post y) {
                        String xx = x.getTime();
                        String yy = y.getTime();
                        return yy.compareTo(xx);
                    }
                });
                if (isFirstCall) {
                    recyclerView.setAdapter(viewAdapter);
                    isFirstCall = false;
                } else {
                    viewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}