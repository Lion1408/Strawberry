package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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
    User user = new User();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user1 = getIntent().getParcelableExtra("User");
        binding.username.setText(user1.getFullName());
        binding.backHome.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_profile_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ViewAdapter viewAdapter = new ViewAdapter(list, getApplicationContext());
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {
                Intent intent = new Intent(getApplicationContext(), ProfileUserActivity.class);
                User user1 = new User();
                user1.setIdUser(post.getIdUser());
                intent.putExtra("User", user1);
                startActivity(intent);
            }

            @Override
            public void OnClickPost(Post post) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra("Post", post);
                intent.putExtra("User", user);
                startActivity(intent);
            }

            @Override
            public void OnClickReact(Post post) {
                Map<String, String> mp = new HashMap<>();
                mp.put("idUser" + user.getIdUser(), "hihi");
                if (post.getActionReact() == true) {
                    databaseReference
                            .child("reactions/" + "post" + post.getIdPost() + "/idUser" + user.getIdUser())
                            .removeValue();
                    databaseReference
                            .child("posts/" + "post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                    databaseReference
                            .child("userPosts/" + "user" + user1.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() - 1);
                } else {
                    databaseReference.child("reactions/" + "post" + post.getIdPost() + "/idUser" + user.getIdUser()).setValue(mp);
                    databaseReference.child("posts/" + "post" + post.getIdPost() + "/reaction").setValue(post.getReaction() + 1);
                    databaseReference.
                            child("userPosts/" + "user" + user1.getIdUser() + "/post" + post.getIdPost() + "/reaction")
                            .setValue(post.getReaction() + 1);
                }
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                user = snapshot.child("users/idUser" + user1.getIdUser()).getValue(User.class);
                Post item1 = new Post();
                item1.setItemType(Constants.HEAD_PROFILE_USER);
                item1.setBiography(user.getBiography());
                item1.setLinkCover(user.getLinkCover());
                item1.setFullName(user.getFullName());
                item1.setLinkAvt(user.getLinkAvt());
                list.add(item1);
                Post item2 = new Post();
                item2.setItemType(Constants.INFOR_USER);
                item2.setIdUser(user.getIdUser());
                list.add(item2);
                Post item3 = new Post();
                item3.setItemType(0);
                item3.setIdUser(user.getIdUser());
                item3.setLinkAvt(user.getLinkAvt());
                list.add(item3);
                for (DataSnapshot i : snapshot.child("userPosts/user" + user.getIdUser()).getChildren()) {
                    Post post1 = i.getValue(Post.class);
                    User user2 = snapshot.child("users/idUser" + post1.getIdUser()).getValue(User.class);
                    post1.setLinkAvt(user2.getLinkAvt());
                    post1.setItemType(Constants.POST);
                    if (snapshot.child("reactions/post" + post1.getIdPost() + "/idUser" + user.getIdUser()).getValue() != null) {
                        post1.setActionReact(true);
                    } else {
                        post1.setActionReact(false);
                    }
                    list.add(post1);
                }
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