package com.example.strawberry.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.example.strawberry.Activities.ChatActivity;
import com.example.strawberry.Activities.MainActivity;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Boolean isFirstCall = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recy_post);
        User user = getActivity().getIntent().getParcelableExtra("User");
        List<Post> list = new ArrayList<>();
        ViewAdapter viewAdapter = new ViewAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {
                Intent intent = new Intent(getContext(), ProfileUserActivity.class);
                intent.putExtra("User", user);
                intent.putExtra("Post", post);
                startActivity(intent);
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
                Map <String, String> mp = new HashMap<>();
                mp.put("idUser" + user.getIdUser(), "hihi");
                if (post.getActionReact() == true) {
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user.getIdUser())
                            .removeValue();
                    databaseReference
                            .child("posts/" + "post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                    databaseReference
                            .child("userPosts/" + "user" + post.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                } else {
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user.getIdUser())
                            .setValue(mp);
                    databaseReference
                            .child("posts/" + "post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() + 1);
                    databaseReference
                            .child("userPosts/" + "user" + post.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() + 1);
                }
            }

            @Override
            public void OnClickDelete(Post post) {
                Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_check);
                dialog.show();
                TextView no = dialog.findViewById(R.id.no);
                no.setOnClickListener(vv -> {
                    dialog.dismiss();
                });
            }
        });
        viewAdapter.setOnClickUpPost(new OnClickUpPost() {
            @Override
            public void onClick() {
                Intent intent = new Intent(getContext(), UpPostActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        ImageView chat = view.findViewById(R.id.chat);
        chat.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.child("users/idUser" + user.getIdUser()).getValue(User.class);
                list.clear();
                Post post = new Post();
                post.setIdUser(user1.getIdUser());
                post.setItemType(0);
                post.setLinkAvt(user1.getLinkAvt());
                list.add(post);
                for (DataSnapshot i : snapshot.child("posts").getChildren()) {
                    Post post1 = i.getValue(Post.class);
                    User user2 = snapshot.child("users/idUser" + post1.getIdUser()).getValue(User.class);
                    post1.setLinkAvt(user2.getLinkAvt());
                    post1.setItemType(Constants.POST);
                    post1.setIdLog(user.getIdUser());
                    if (snapshot.child("reactions/post" + post1.getIdPost() + "/idUser" + user.getIdUser()).getValue() != null) {
                        post1.setActionReact(true);
                    } else {
                        post1.setActionReact(false);
                    }
                    list.add(post1);
                }
                Collections.sort(list.subList(1, list.size()), new Comparator<Post>() {
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