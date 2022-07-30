package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ApiService;
import com.example.strawberry.Interfaces.InforUserOnClick;
import com.example.strawberry.Interfaces.OnClickUpPost;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityProfileUserBinding;
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

public class ProfileUserActivity extends AppCompatActivity {
    ActivityProfileUserBinding binding;
    List <Post> list = new ArrayList<>();
    Boolean isFirstCall = true;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user1 = getIntent().getParcelableExtra("User");
        Post post1 = getIntent().getParcelableExtra("Post");
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_profile_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
        viewAdapter.setInforUserOnClick(new InforUserOnClick() {
            @Override
            public void OnClickImageVideo() {

            }

            @Override
            public void OnClickInfor() {
                Intent intent = new Intent(ProfileUserActivity.this, InforUserActivity.class);
                intent.putExtra("User", user1);
                startActivity(intent);
            }
        });
        viewAdapter.setOnClickUpPost(new OnClickUpPost() {
            @Override
            public void onClick() {
                Intent intent = new Intent(getApplicationContext(), UpPostActivity.class);
                intent.putExtra("User", user1);
                startActivity(intent);
            }
        });
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {

            }

            @Override
            public void OnClickPost(Post post) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra("Post", post);
                intent.putExtra("User", user1);
                startActivity(intent);
                finish();
            }

            @Override
            public void OnClickReact(Post post) {
                Map<String, String> mp = new HashMap<>();
                mp.put("idUser" + user1.getIdUser(), "hihi");
                if (post.getActionReact() == true) {
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user1.getIdUser())
                            .removeValue();
                    databaseReference
                            .child("posts/" + "post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                    databaseReference
                            .child("userPosts/" + "user" + post1.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                } else {
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user1.getIdUser())
                            .setValue(mp);
                    databaseReference
                            .child("posts/" + "post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() + 1);
                    databaseReference.
                            child("userPosts/" + "user" + post1.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() + 1);
                }
            }

            @Override
            public void OnClickDelete(Post post) {
                Dialog dialog = new Dialog(ProfileUserActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_check);
                dialog.show();
                TextView no = dialog.findViewById(R.id.no);
                no.setOnClickListener(vv -> {
                    dialog.dismiss();
                });
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                User userPost = snapshot.child("users/idUser" + post1.getIdUser()).getValue(User.class);
                binding.username.setText(userPost.getFullName());
                Post item1 = new Post();
                item1.setItemType(Constants.HEAD_PROFILE_USER);
                item1.setBiography(userPost.getBiography());
                item1.setLinkCover(userPost.getLinkCover());
                item1.setFullName(userPost.getFullName());
                item1.setLinkAvt(userPost.getLinkAvt());
                list.add(item1);
                Post item2 = new Post();
                item2.setItemType(Constants.INFOR_USER);
                item2.setIdUser(userPost.getIdUser());
                list.add(item2);
                Post item3 = new Post();
                item3.setItemType(0);
                item3.setIdUser(userPost.getIdUser());
                item3.setLinkAvt(userPost.getLinkAvt());
                list.add(item3);
                for (DataSnapshot i : snapshot.child("userPosts/user" + userPost.getIdUser()).getChildren()) {
                    Post post2 = i.getValue(Post.class);
                    User user2 = snapshot.child("users/idUser" + post2.getIdUser()).getValue(User.class);
                    post2.setLinkAvt(user2.getLinkAvt());
                    post2.setItemType(Constants.POST);
                    post2.setIdLog(user1.getIdUser());
                    if (snapshot.child("reactions/post" + post2.getIdPost() + "/idUser" + user1.getIdUser()).getValue() != null) {
                        post2.setActionReact(true);
                    } else {
                        post2.setActionReact(false);
                    }
                    list.add(post2);
                }
                Collections.sort(list.subList(3, list.size()), new Comparator<Post>() {
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
    }
}