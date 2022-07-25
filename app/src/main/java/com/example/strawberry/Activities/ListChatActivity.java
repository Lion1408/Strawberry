package com.example.strawberry.Activities;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.strawberry.Adapters.UserChatAdapter;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Interfaces.OnClickUserChat;
import com.example.strawberry.Model.UserChat;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityListChatBinding;
import com.google.firebase.database.ChildEventListener;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backmess.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot i: snapshot.child("users").getChildren()) {
                    UserChat userChat = i.getValue(UserChat.class);
                    list.add(userChat);
                }
                adapter = new UserChatAdapter(list, getApplicationContext());
                adapter.setOnClickUserChat(new OnClickUserChat() {
                    @Override
                    public void onClick(UserChat userChat) {
                        Intent intent = new Intent(getApplicationContext(), RoomChatActivity.class);
                        intent.putExtra("Data", (Parcelable) userChat);
                        startActivity(intent);
                        finish();
                    }
                });
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}