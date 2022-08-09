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
import android.widget.TextView;

import com.example.strawberry.Adapters.UserChatAdapter;
import com.example.strawberry.Interfaces.OnClickUserChat;
import com.example.strawberry.Model.User;
import com.example.strawberry.Model.UserChat;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityListChatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListChatActivity extends AppCompatActivity {
    ActivityListChatBinding binding;
    List <UserChat> list = new ArrayList<>();
    UserChatAdapter adapter;
    User user;
    Boolean isFirstCall = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = getIntent().getParcelableExtra("User");
        binding.backmess.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new UserChatAdapter(list, getApplicationContext());
        adapter.setOnClickUserChat(new OnClickUserChat() {
            @Override
            public void onClick(UserChat userChat) {
                Intent intent = new Intent(getApplicationContext(), RoomChatActivity.class);
                intent.putExtra("Userchat", userChat);
                intent.putExtra("User", user);
                startActivity(intent);
                finish();
            }

            @Override
            public void onDel(UserChat userChat) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot i: snapshot.child("users").getChildren()) {
                    UserChat userChat = i.getValue(UserChat.class);
                    userChat.setCheck(false);
                    list.add(userChat);
                }
                if (isFirstCall) {
                    recyclerView.setAdapter(adapter);
                    isFirstCall = false;
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}