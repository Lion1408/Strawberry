package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.strawberry.Adapters.MessAdapter;
import com.example.strawberry.Adapters.UserChatAdapter;
import com.example.strawberry.Interfaces.OnClickUserChat;
import com.example.strawberry.Model.Message;
import com.example.strawberry.Model.UserChat;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityChatBinding;

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

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    UserChatAdapter adapter;
    Map <String, String> mp = new HashMap<>();
    List<UserChat> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mp.clear();
                for (DataSnapshot i : snapshot.child("chats/" + "id0" + "/").getChildren()) {
                    String idUser = i.getKey();
                    mp.put(idUser, "true");
                    System.out.println(idUser + "  :))");
                    Message message = new Message();
                    for (DataSnapshot j :  snapshot.child("chats/" + "id0/" + idUser).getChildren()) {
                        message = j.getValue(Message.class);
                    }
                    mp.put("time" + idUser, message.getTime());
                    mp.put("content" + idUser, message.getContent());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot i : snapshot.child("users").getChildren()) {
                    Log.e("Chat", i.getKey() + " " +  mp.get(i.getKey()));
                    if (mp.get(i.getKey()) != null) {
                        UserChat userChat = i.getValue(UserChat.class);
                        userChat.setTime(mp.get("time" + "id" + userChat.getIdUser()));
                        userChat.setContent(mp.get("content" + "id" + userChat.getIdUser()));
                        list.add(userChat);
                    }
                }
                Collections.sort(list, new Comparator<UserChat>() {
                    @Override
                    public int compare(UserChat x, UserChat y) {
                        String xx = x.getTime();
                        String yy = y.getTime();
                        return yy.compareTo(xx);
                    }
                });
                adapter = new UserChatAdapter(list, getApplicationContext());
                adapter.setOnClickUserChat(new OnClickUserChat() {
                    @Override
                    public void onClick(UserChat userChat) {
                        Intent intent = new Intent(getApplicationContext(), RoomChatActivity.class);
                        intent.putExtra("Data", (Parcelable) userChat);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.NewChat.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ListChatActivity.class));
        });
    }
}