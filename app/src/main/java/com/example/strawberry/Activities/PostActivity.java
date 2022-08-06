package com.example.strawberry.Activities;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ImageOnClick;
import com.example.strawberry.Interfaces.PostOnClick;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    ActivityPostBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    User user = new User();
    List<Post> list = new ArrayList<>();
    Boolean isFirstCall = true;
    Integer n = 0;
    Post postcmt;
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
        viewAdapter.setImageOnClick(new ImageOnClick() {
            @Override
            public void onclickImage(String image) {
                Dialog dialog = new Dialog(PostActivity.this, R.style.Theme_Strawberry);
                dialog.setContentView(R.layout.dialog_show_image);
                ImageView img, backImage;
                img = dialog.findViewById(R.id.showImage);
                backImage = dialog.findViewById(R.id.backImage);
                Glide.with(img).load(image).into(img);
                backImage.setOnClickListener(v -> {
                    dialog.dismiss();
                });
                dialog.show();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n = 0;
                while (snapshot.child("comments/post" + post1.getIdPost() + "/comment" + n).getValue() != null) {
                    n++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.sendComment.setOnClickListener(v -> {
            if (binding.contentComment.getText().toString().trim().isEmpty()) {
                Constants.showToast("Nhập nội dung", getApplicationContext());
            } else {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Date date = new Date();
                        postcmt = snapshot.child("posts/post" + post1.getIdPost()).getValue(Post.class);
                        User userID = snapshot.child("users/idUser" + user.getIdUser()).getValue(User.class);
                        postcmt.setLinkAvt(userID.getLinkAvt());
                        postcmt.setFullName(userID.getFullName());
                        postcmt.setIdLog(userID.getIdUser());
                        postcmt.setTime(date.getTime() + "");
                        postcmt.setIdCmt(n);
                        postcmt.setContent(binding.contentComment.getText().toString());
                        databaseReference.child("comments/post" + post1.getIdPost() + "/comment" + n).setValue(postcmt);
                        databaseReference.
                                child("userPosts/" + "user" + postcmt.getIdUser() + "/post" + postcmt.getIdPost() + "/comment")
                                .setValue(postcmt.getComment() + 1);

                        databaseReference
                                .child("posts/" + "post" + postcmt.getIdPost() + "/comment")
                                .setValue(postcmt.getComment() + 1);
                        binding.contentComment.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Post post = snapshot.child("posts/post" + post1.getIdPost()).getValue(Post.class);
                if (post != null) {
                    User userPost = snapshot.child("users/idUser" + post.getIdUser()).getValue(User.class);
                    if (user.getIdUser() == post1.getIdUser()) {
                        binding.contenPost.setText("Bài viết của bạn");
                    } else {
                        binding.contenPost.setText("Bài viết của " + userPost.getFullName());
                    }
                    if (snapshot.child("reactions/post" + post1.getIdPost() + "/idUser" + user.getIdUser()).getValue() != null) {
                        post1.setActionReact(true);
                    } else {
                        post1.setActionReact(false);
                    }
                    post.setLinkAvt(userPost.getLinkAvt());
                    post.setItemType(Constants.POST);
                    post.setActionReact(post1.getActionReact());
                    post.setIdLog(user.getIdUser());
                    post.setStatusUser(userPost.getStatus());
                    list.add(post);
                    for (DataSnapshot i : snapshot.child("comments/post" + post1.getIdPost()).getChildren()) {
                        Post post2 = i.getValue(Post.class);
                        User userID = snapshot.child("users/idUser" + post2.getIdLog()).getValue(User.class);
                        post2.setLinkAvt(userID.getLinkAvt());
                        post2.setItemType(Constants.COMMENT);
                        list.add(post2);
                    }
                    if (isFirstCall) {
                        recyclerView.setAdapter(viewAdapter);
                        recyclerView.scrollToPosition(list.size() - 1);
                        isFirstCall = false;
                    } else {
                        viewAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(list.size() - 1);
                    }
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
    }
}