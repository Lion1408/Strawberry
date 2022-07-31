package com.example.strawberry.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Interfaces.OnClickUserChat;
import com.example.strawberry.Model.UserChat;
import com.example.strawberry.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.Viewholder>{
    List<UserChat> list;
    Context context;
    OnClickUserChat onClickUserChat;

    public void setOnClickUserChat(OnClickUserChat onClickUserChat) {
        this.onClickUserChat = onClickUserChat;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserChatAdapter(List<UserChat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserChatAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_chat, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        UserChat userChat = list.get(position);
        holder.username.setText(userChat.getFullName());
        Glide.with(holder.avt).load(userChat.getLinkAvt()).into(holder.avt);
        if (userChat.getStatus().equals(true)) {
            holder.status.setImageResource(R.drawable.background_online);
        } else {
            holder.status.setBackgroundResource(R.drawable.background_off);
        }
        holder.roomchat.setOnClickListener(v -> {
            onClickUserChat.onClick(userChat);
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView username;
        ImageView status;
        CircleImageView avt;
        ConstraintLayout roomchat;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            avt = itemView.findViewById(R.id.imageProfile);
            status = itemView.findViewById(R.id.status);
            roomchat = itemView.findViewById(R.id.roomchat);
        }
    }
}