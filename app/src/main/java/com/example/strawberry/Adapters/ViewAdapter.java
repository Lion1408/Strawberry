package com.example.strawberry.Adapters;


import android.app.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.DhcpInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Activities.InforUserActivity;
import com.example.strawberry.Activities.SignInActivity;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.InforUserOnClick;
import com.example.strawberry.Interfaces.OnClickUpPost;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Post;
import com.example.strawberry.R;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> list;
    private Context context;
    private PostOnClick postOnClick;
    private InforUserOnClick inforUserOnClick;
    private OnClickUpPost onClickUpPost;

    public void PostOnClick(PostOnClick postOnClick) {
        this.postOnClick = postOnClick;
    }

    public InforUserOnClick getInforUserOnClick() {
        return inforUserOnClick;
    }

    public void setInforUserOnClick(InforUserOnClick inforUserOnClick) {
        this.inforUserOnClick = inforUserOnClick;
    }

    public void setOnClickUpPost(OnClickUpPost onClickUpPost) {
        this.onClickUpPost = onClickUpPost;
    }

    public ViewAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.UP_POST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_up_post, parent, false);
            return new UpPostViewHolder(view);
        } else if (viewType == Constants.POST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view);
        } else if (viewType == Constants.HEAD_PROFILE_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head_user, parent, false);
            return new ProfileViewHolder(view);
        } else if (viewType == Constants.INFOR_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infor_user, parent, false);
            return new InforUserViewHolder(view);
        } else if (viewType == Constants.COMMENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder x, int position) {
        Post post = list.get(position);
        if (post.getItemType() == Constants.UP_POST) {
            UpPostViewHolder holder = (UpPostViewHolder) x;
            Glide.with(holder.avt).load(post.getLinkAvt()).into(holder.avt);
            holder.uppost.setOnClickListener(v -> {
                onClickUpPost.onClick();
            });
        }

        if (post.getItemType() == Constants.POST) {
            PostViewHolder holder = (PostViewHolder) x;
            holder.fullname.setText(post.getFullName());
            holder.content.setText(post.getContent());
            if (!post.getActionReact()) {
                holder.reactImg.setColorFilter(ContextCompat.getColor(context, R.color.gray), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                holder.reactImg.clearColorFilter();
            }

            holder.react.setText(post.getReaction().toString());
            holder.cmt.setText(post.getComment() + " lượt bình luận");
            Date dateOld = new Date();
            dateOld.setTime(Long.valueOf(post.getTime()));
            Date dateNew = new Date();
            String time = "";
            Long total = dateNew.getTime() - dateOld.getTime();
            long h = total/(60*60*1000);
            total -= h * (60*60*1000);
            long m = total/(60*1000);
            total -= m * (60*1000);
            long s = total/1000;
            if (h >= 24) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
                String ss = simpleDateFormat.format(dateOld);
                time = ss.substring(0, 2) + " Thg " + ss.substring(3, 5) + " " + ss.substring(11, 16);
            } else
            if (h > 0) {
                time = h + " giờ trước";
            } else if (m > 0) {
                time = m + " phút trước";
            } else {
                time = s + " giây trước";
            }
            holder.time.setText(time);
            Glide.with(holder.avt).load(post.getLinkAvt()).into(holder.avt);
            if (post.getIdUser() == post.getIdLog()) {
                holder.deletePost.setVisibility(View.VISIBLE);
            } else {
                holder.deletePost.setVisibility(View.GONE);
            }
            holder.deletePost.setOnClickListener(v -> {
                postOnClick.OnClickDelete(post);
            });
            if (!post.getLinkVideo().equals("null")) {
                Uri uri = Uri.parse(post.getLinkVideo());
                holder.video.setVideoURI(uri);
                holder.video.start();
                holder.video.setVisibility(View.VISIBLE);
                holder.img.setVisibility(View.GONE);
            }  else if (!post.getLinkImage().equals("null")) {
                Glide.with(holder.img).load(post.getLinkImage()).into(holder.img);
                holder.img.setVisibility(View.VISIBLE);
                holder.video.setVisibility(View.GONE);
            } else {
                holder.img.setVisibility(View.GONE);
                holder.video.setVisibility(View.GONE);
            }

            holder.avt.setOnClickListener(v -> {
                postOnClick.OnClickAvt(post);
            });

            holder.fullname.setOnClickListener(v -> {
                postOnClick.OnClickAvt(post);
            });

            holder.viewPost.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

            holder.content.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

            holder.video.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

            holder.img.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

            holder.layerReaction.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

            holder.cmt.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

            holder.reactPost.setOnClickListener(v -> {
                postOnClick.OnClickReact(post);
            });

            holder.comment.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
            });

        }

        if (post.getItemType() == Constants.HEAD_PROFILE_USER) {
            ProfileViewHolder holder = (ProfileViewHolder) x;
            holder.username.setText(post.getFullName());
            holder.biography.setText("Tiểu sử: " + (post.getBiography() == null?"":post.getBiography()));
            Glide.with(holder.avt).load(post.getLinkAvt()).into(holder.avt);
            Glide.with(holder.cover).load(post.getLinkCover()).into(holder.cover);
        }

        if (post.getItemType() == Constants.INFOR_USER) {
            InforUserViewHolder holder = (InforUserViewHolder) x;
            holder.imageVideoUser.setOnClickListener(v -> {
                inforUserOnClick.OnClickImageVideo();
            });

            holder.inforOfUser.setOnClickListener(v -> {
                inforUserOnClick.OnClickInfor();
            });

        }

        if (post.getItemType() == Constants.COMMENT) {
            CommentViewHolder holder = (CommentViewHolder) x;
            Glide.with(holder.avtComment).load(post.getLinkAvt()).into(holder.avtComment);
            holder.username.setText(post.getFullName());
            holder.content.setText(post.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avt;
        TextView time, fullname, content, cmt, react, textReact;
        ImageView img, reactImg, deletePost;
        ConstraintLayout reactPost, layerReaction, comment;
        VideoView video;
        View viewPost;
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
            viewPost = itemView.findViewById(R.id.viewPost);
            reactPost = itemView.findViewById(R.id.reactPost);
            layerReaction = itemView.findViewById(R.id.layerReaction);
            reactImg = itemView.findViewById(R.id.reactImg);
            comment = itemView.findViewById(R.id.comment);
            deletePost = itemView.findViewById(R.id.deletePost);
        }
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView username, biography;
        ImageView cover;
        CircleImageView avt;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            biography = itemView.findViewById(R.id.biography);
            avt = itemView.findViewById(R.id.avtUser);
            cover = itemView.findViewById(R.id.imgCoverUser);
        }
    }

    public class UpPostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avt;
        ConstraintLayout uppost;
        public UpPostViewHolder(@NonNull View itemView) {
            super(itemView);
            avt = itemView.findViewById(R.id.icInforUser);
            uppost = itemView.findViewById(R.id.uppost);
        }
    }

    public class InforUserViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout imageVideoUser, inforOfUser;
        public InforUserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageVideoUser = itemView.findViewById(R.id.imageUser);
            inforOfUser = itemView.findViewById(R.id.infoOfUser);
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avtComment;
        TextView content, username;
        ImageView deleteComment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            avtComment = itemView.findViewById(R.id.avtComment);
            content = itemView.findViewById(R.id.textComment);
            deleteComment = itemView.findViewById(R.id.deleteComment);
            username = itemView.findViewById(R.id.fullName);
        }
    }
}