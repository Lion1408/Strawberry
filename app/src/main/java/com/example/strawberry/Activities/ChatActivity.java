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
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.example.strawberry.Adapters.MessAdapter;
import com.example.strawberry.Adapters.UserChatAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.OnClickUserChat;
import com.example.strawberry.Model.Message;
import com.example.strawberry.Model.User;
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
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = getIntent().getParcelableExtra("User");
        binding.back.setOnClickListener(v -> {
            finish();
        });
        RecyclerView recyclerView = findViewById(R.id.recy_chat);
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
            }

            @Override
            public void onDel(UserChat userChat) {
                Dialog dialog = new Dialog(ChatActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_check);
                dialog.show();
                TextView title = dialog.findViewById(R.id.contentCheck);
                TextView no = dialog.findViewById(R.id.no);
                TextView yes = dialog.findViewById(R.id.yes);
                title.setText("Xoá tin nhắn");
                no.setOnClickListener(vv -> {
                    dialog.dismiss();
                });
                yes.setOnClickListener(vv -> {
                    databaseReference.child("chats/idUser" + user.getIdUser() + "/idUser" + userChat.getIdUser()).removeValue();
                    databaseReference.child("chats/idUser" + userChat.getIdUser() + "/idUser" + user.getIdUser()).removeValue();
                    Constants.showToast("Xoá tin nhắn thành công!", ChatActivity.this);
                    dialog.dismiss();
                });
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mp.clear();
                for (DataSnapshot i : snapshot.child("chats/idUser" + user.getIdUser()).getChildren()) {
                    String idUser = i.getKey();
                    mp.put(idUser, "true");
                    Message message = new Message();
                    for (DataSnapshot j :  snapshot.child("chats/" + "idUser" + user.getIdUser() + "/" + idUser).getChildren()) {
                        message = j.getValue(Message.class);
                    }
                    mp.put("time" + idUser, message.getTime());
                    mp.put("content" + idUser, message.getContent());
                }
                list.clear();
                for (DataSnapshot i : snapshot.child("users").getChildren()) {
                    if (mp.get(i.getKey()) != null) {
                        UserChat userChat = i.getValue(UserChat.class);
                        userChat.setTime(mp.get("time" + "idUser" + userChat.getIdUser()));
                        userChat.setContent(mp.get("content" + "idUser" + userChat.getIdUser()));
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
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.NewChat.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListChatActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        });
    }
}