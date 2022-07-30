package com.example.strawberry.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityPostBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    ActivityPostBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    User user = new User();
    List<Post> list = new ArrayList<>();
    Boolean isFirstCall = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = getIntent().getParcelableExtra("User");
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        Post post1 = getIntent().getParcelableExtra("Post");
        RecyclerView recyclerView = findViewById(R.id.recy_user_post);
        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Post post = snapshot.child("posts/post" + post1.getIdPost()).getValue(Post.class);
                User userPost = snapshot.child("users/idUser" + post.getIdUser()).getValue(User.class);
                binding.contenPost.setText("Bài viết của " + userPost.getFullName());
                post.setLinkAvt(userPost.getLinkAvt());
                post.setItemType(Constants.POST);
                post.setActionReact(post1.getActionReact());
                post.setIdLog(user.getIdUser());
                list.add(post);
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
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {
                Intent intent = new Intent(getApplicationContext(), ProfileUserActivity.class);
                User user1 = new User();
                user1.setIdUser(user.getIdUser());
                intent.putExtra("Post", post);
                intent.putExtra("User", user1);
                startActivity(intent);
                finish();
            }

            @Override
            public void OnClickPost(Post post) {

            }

            @Override
            public void OnClickReact(Post post) {
                Map<String, String> mp = new HashMap<>();
                mp.put("idUser" + user.getIdUser(), "hihi");
                if (post.getActionReact() == true) {
                    post1.setActionReact(false);
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user.getIdUser())
                            .removeValue();
                    databaseReference
                            .child("posts/" + "post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                    databaseReference.
                            child("userPosts/" + "user" + post1.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                } else {
                    post1.setActionReact(true);
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user.getIdUser())
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

            }
        });
        recyclerView.setAdapter(viewAdapter);
    }
}