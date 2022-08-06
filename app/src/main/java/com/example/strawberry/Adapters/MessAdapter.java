package com.example.strawberry.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Model.Message;
import com.example.strawberry.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Message> list;
    Context context;


    public void setContext(Context context) {
        this.context = context;
    }

    public MessAdapter(List<Message> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_right, parent, false);
            return new MessContentRightHolder(view);
        } else
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_left, parent, false);
            return new MessContentLeftHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder x, int position) {
        Message message = list.get(position);
        if (message.getItemtype() == 0) {
            MessContentRightHolder holder = (MessContentRightHolder) x;
            holder.mess_right.setText(message.getContent());
        } else {
            MessContentLeftHolder holder = (MessContentLeftHolder) x;
            holder.mess_left.setText(message.getContent());
            Glide.with(holder.avtChat).load(message.getLinkAvt()).into(holder.avtChat);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemtype();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MessContentLeftHolder  extends RecyclerView.ViewHolder {
        TextView mess_left;
        CircleImageView avtChat;
        public MessContentLeftHolder(@NonNull View itemView) {
            super(itemView);
            mess_left = itemView.findViewById(R.id.mess_left);
            avtChat = itemView.findViewById(R.id.avtChat);
        }
    }
    public class MessContentRightHolder extends RecyclerView.ViewHolder {
        TextView mess_right;
        public MessContentRightHolder(@NonNull View itemView) {
            super(itemView);
            mess_right = itemView.findViewById(R.id.mess_right);
        }
    }
}
