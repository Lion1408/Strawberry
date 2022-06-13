package com.example.strawberry.Adapters;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Post;
import com.example.strawberry.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    List<Post> list;
    Context context;

    PostOnClick postOnClick;

    public void PostOnClick(PostOnClick postOnClick) {
        this.postOnClick = postOnClick;
    }

    public PostAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemType();
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        if (list.get(position).getItemType() == 1) {
            Post post = list.get(position);

            //
            holder.fullname.setText(post.getUser().getFullName());
            holder.content.setText(post.getContentPost());
            holder.react.setText(post.getReactions().getALL().toString());
            holder.cmt.setText(post.getComments().size() + " lượt bình luận");
            holder.time.setText("timeup");
            Glide.with(holder.avt).load(post.getUser().getLinkAvt()).into(holder.avt);
            if (post.getImages().size() > 0) {
                Glide.with(holder.img).load(post.getImages().get(0).getLinkImage()).into(holder.img);
                holder.video.setVisibility(View.GONE);
                holder.img.setVisibility(View.VISIBLE);
            }  else if (post.getVideos().size() > 0) {
                Uri uri = Uri.parse(post.getVideos().get(0).getLinkVideo());
                holder.video.setVideoURI(uri);
                holder.video.setMediaController(new MediaController(holder.video.getContext()));
                holder.video.start();
                holder.img.setVisibility(View.GONE);
                holder.video.setVisibility(View.VISIBLE);
            } else {
                holder.img.setVisibility(View.GONE);
                holder.video.setVisibility(View.GONE);
            }

            holder.likePost.setOnClickListener(v -> {
                holder.list_react.setVisibility(View.VISIBLE);
                holder.react_default.setVisibility(View.GONE);
            });
            holder.reactLike.setOnClickListener(v -> {

                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.like);
                holder.reactMain.setImageResource(R.drawable.ic_like);
                holder.react_like.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactLove.setOnClickListener(v -> {

                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.love);
                holder.reactMain.setImageResource(R.drawable.ic_love);
                holder.react_love.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactWow.setOnClickListener(v -> {

                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.wow);
                holder.reactMain.setImageResource(R.drawable.ic_wow);
                holder.react_wow.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactHaha.setOnClickListener(v -> {

                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.haha);
                holder.reactMain.setImageResource(R.drawable.ic_haha);
                holder.react_haha.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactSad.setOnClickListener(v -> {

                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.sad);
                holder.reactMain.setImageResource(R.drawable.ic_sad);
                holder.react_sad.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactCare.setOnClickListener(v -> {

                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.care);
                holder.reactMain.setImageResource(R.drawable.ic_care);
                holder.react_care.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });

            holder.reactAngry.setOnClickListener(v -> {
                holder.react.setText(post.getReactions().getALL().toString());
                holder.textReact.setText(R.string.angry);
                holder.reactMain.setImageResource(R.drawable.ic_angry);
                holder.react_angry.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });

            holder.avt.setOnClickListener(v -> {
                postOnClick.OnClickAvt();
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avt;
        TextView time, fullname, content, cmt, react, textReact;
        ImageView img, react_like, react_love, react_sad, react_care, react_angry, react_wow, react_default,react_haha,
                        reactLike, reactLove, reactSad, reactCare, reactAngry, reactWow, reactHaha, reactMain;
        ConstraintLayout likePost;
        LinearLayout list_react;
        VideoView video;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeup);
            fullname = itemView.findViewById(R.id.fullName);
            content = itemView.findViewById(R.id.conTent);
            cmt = itemView.findViewById(R.id.cmt);
            textReact = itemView.findViewById(R.id.textReact);
            react = itemView.findViewById(R.id.react);
            avt = itemView.findViewById(R.id.avatar);
            img = itemView.findViewById(R.id.img);
            video = itemView.findViewById(R.id.video);
            likePost = itemView.findViewById(R.id.likePost);
            react_like = itemView.findViewById(R.id.react_like);
            react_love = itemView.findViewById(R.id.react_love);
            react_sad = itemView.findViewById(R.id.react_sad);
            react_angry = itemView.findViewById(R.id.react_angry);
            react_care = itemView.findViewById(R.id.react_care);
            react_wow = itemView.findViewById(R.id.react_wow);
            react_haha = itemView.findViewById(R.id.react_haha);
            react_default = itemView.findViewById(R.id.react_default);
            list_react = itemView.findViewById(R.id.list_react);

            reactLike = itemView.findViewById(R.id.reactLike);
            reactLove = itemView.findViewById(R.id.reactLove);
            reactSad = itemView.findViewById(R.id.reactSad);
            reactAngry = itemView.findViewById(R.id.reactAngry);
            reactCare = itemView.findViewById(R.id.reactCare);
            reactWow = itemView.findViewById(R.id.reactWow);
            reactHaha = itemView.findViewById(R.id.reactHaha);

            reactMain = itemView.findViewById(R.id.reactMain);
        }
    }

}
