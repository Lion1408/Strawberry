package com.example.strawberry.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.strawberry.Adapters.MessAdapter;
import com.example.strawberry.Adapters.UserChatAdapter;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.OnClickUserChat;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Message;
import com.example.strawberry.Model.UserChat;
import com.example.strawberry.R;
import com.example.strawberry.databinding.ActivityChatRoomBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomChatActivity extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    List<Message> list = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    MessAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UserChat userChat = getIntent().getParcelableExtra("Data");
        binding.username.setText(userChat.getUsername());
        binding.back.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_roomchat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot i: snapshot.child("chats/id0/id" + userChat.getIdUser()).getChildren()) {
                    Message message = i.getValue(Message.class);
                    list.add(message);
                }
                adapter = new MessAdapter(list, getApplicationContext());
                recyclerView.scrollToPosition(list.size() - 1);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.boxcontent.getText().toString().trim().isEmpty()) {
                    Constants.showToast("Nh???p n???i dung", getApplicationContext());
                } else {
                    Date date = new Date();
                    Message message = new Message(
                            binding.boxcontent.getText() + "",
                            date.getTime() + "",
                            0);

                    databaseReference.child("chats")
                            .child("id0")
                            .child("id" + userChat.getIdUser())
                            .child(date.getTime() + "")
                            .setValue(message);
                    if (userChat.getIdUser() != 0) message.setItemtype(1);
                    databaseReference.child("chats")
                            .child("id" + userChat.getIdUser())
                            .child("id0")
                            .child(date.getTime() + "")
                            .setValue(message);
                    binding.boxcontent.setText("");
                }
            }
        });
    }
}