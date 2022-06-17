package com.example.strawberry.Adapters;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
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
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Image;
import com.example.strawberry.Model.Reaction;
import com.example.strawberry.Model.User;
import com.example.strawberry.Model.Video;
import com.example.strawberry.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Integer UP_POST = 0;
    private Integer POST = 1;
    private Integer HEAD_PROFILE_USER = 2;
    private Integer INFOR_USER = 3;
    private List<Data> list;
    private Context context;
    private PostOnClick postOnClick;
    public void PostOnClick(PostOnClick postOnClick) {
        this.postOnClick = postOnClick;
    }

    public ViewAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == UP_POST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_up_post, parent, false);
            return new UpPostViewHolder(view);
        } else if (viewType == POST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view);
        } else if (viewType == HEAD_PROFILE_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head_user, parent, false);
            return new ProfileViewHolder(view);
        } else if (viewType == INFOR_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infor_user, parent, false);
            return new InforUserViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder x, int position) {
        Data data = list.get(position);
        if (list.get(position).getItemType() == UP_POST) {
            UpPostViewHolder holder = (UpPostViewHolder) x;
            Glide.with(holder.avt).load("https://img5.thuthuatphanmem.vn/uploads/2021/11/12/hinh-anh-anime-don-gian-hinh-nen-anime-don-gian-ma-dep_092443354.png").into(holder.avt);
            holder.uppost.setOnClickListener(v -> {

            });
        }

        if (list.get(position).getItemType() == POST) {
            PostViewHolder holder = (PostViewHolder) x;
            //
            holder.fullname.setText(data.getUser().getFullName());
            holder.content.setText(data.getContentPost());
            holder.react.setText(data.getReactions().getALL().toString());
            holder.cmt.setText(data.getCountComments() + " lượt bình luận");
            holder.time.setText("timeup");
            Glide.with(holder.avt).load(data.getUser().getLinkAvt()).into(holder.avt);
            if (data.getImages().size() > 0) {
                Glide.with(holder.img).load(data.getImages().get(0).getLinkImage()).into(holder.img);
                holder.img.setVisibility(View.VISIBLE);
                holder.video.setVisibility(View.GONE);
            }  else if (data.getVideos().size() > 0) {
                Uri uri = Uri.parse(data.getVideos().get(0).getLinkVideo());
                holder.video.setVideoURI(uri);
                MediaController mediaController = new MediaController(holder.video.getContext());
                holder.video.setMediaController(mediaController);
                mediaController.setAnchorView(holder.itemView);
                holder.video.start();
                holder.video.setVisibility(View.VISIBLE);
                holder.img.setVisibility(View.GONE);
                holder.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        holder.video.start();
                    }
                });
            } else {
                holder.img.setVisibility(View.GONE);
                holder.video.setVisibility(View.GONE);
            }

            holder.likePost.setOnClickListener(v -> {
                holder.list_react.setVisibility(View.VISIBLE);
                holder.react_default.setVisibility(View.GONE);
            });
            holder.reactLike.setOnClickListener(v -> {

                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.like);
                holder.reactMain.setImageResource(R.drawable.ic_like);
                holder.react_like.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactLove.setOnClickListener(v -> {

                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.love);
                holder.reactMain.setImageResource(R.drawable.ic_love);
                holder.react_love.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactWow.setOnClickListener(v -> {

                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.wow);
                holder.reactMain.setImageResource(R.drawable.ic_wow);
                holder.react_wow.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactHaha.setOnClickListener(v -> {

                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.haha);
                holder.reactMain.setImageResource(R.drawable.ic_haha);
                holder.react_haha.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactSad.setOnClickListener(v -> {

                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.sad);
                holder.reactMain.setImageResource(R.drawable.ic_sad);
                holder.react_sad.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });
            holder.reactCare.setOnClickListener(v -> {

                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.care);
                holder.reactMain.setImageResource(R.drawable.ic_care);
                holder.react_care.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });

            holder.reactAngry.setOnClickListener(v -> {
                holder.react.setText(data.getReactions().getALL().toString());
                holder.textReact.setText(R.string.angry);
                holder.reactMain.setImageResource(R.drawable.ic_angry);
                holder.react_angry.setVisibility(View.VISIBLE);
                holder.list_react.setVisibility(View.GONE);
            });

            holder.avt.setOnClickListener(v -> {
                postOnClick.OnClickAvt(data.getUser());
            });

            holder.fullname.setOnClickListener(v -> {
                postOnClick.OnClickAvt(data.getUser());
            });

            holder.viewPost.setOnClickListener(v -> {
                postOnClick.OnclickPost(data);
            });

            holder.content.setOnClickListener(v -> {
                postOnClick.OnclickPost(data);
            });

            holder.video.setOnCompletionListener(v -> {
                postOnClick.OnclickPost(data);
            });

            holder.img.setOnClickListener(v -> {
                postOnClick.OnclickPost(data);
            });

            holder.layerReaction.setOnClickListener(v -> {
                postOnClick.OnclickPost(data);
            });

            holder.cmt.setOnClickListener(v -> {
                postOnClick.OnclickPost(data);
            });

        }

        if (list.get(position).getItemType() == HEAD_PROFILE_USER) {
            ProfileViewHolder holder = (ProfileViewHolder) x;
            holder.headprofileFullName.setText(data.getUser().getFullName());
        }

        if (list.get(position).getItemType() == INFOR_USER) {

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
        ConstraintLayout likePost, layerReaction;
        LinearLayout list_react;
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
            likePost = itemView.findViewById(R.id.likePost);
            react_like = itemView.findViewById(R.id.actionLike);
            react_love = itemView.findViewById(R.id.actionLove);
            react_sad = itemView.findViewById(R.id.actionSad);
            react_angry = itemView.findViewById(R.id.actionAngry);
            react_care = itemView.findViewById(R.id.actionCare);
            react_wow = itemView.findViewById(R.id.actionWow);
            react_haha = itemView.findViewById(R.id.actionHaha);
            react_default = itemView.findViewById(R.id.actionDefault);
            list_react = itemView.findViewById(R.id.listReact);
            reactLike = itemView.findViewById(R.id.reactLike);
            reactLove = itemView.findViewById(R.id.reactLove);
            reactSad = itemView.findViewById(R.id.reactSad);
            reactAngry = itemView.findViewById(R.id.reactAngry);
            reactCare = itemView.findViewById(R.id.reactCare);
            reactWow = itemView.findViewById(R.id.reactWow);
            reactHaha = itemView.findViewById(R.id.reactHaha);
            reactMain = itemView.findViewById(R.id.reactMain);
            layerReaction = itemView.findViewById(R.id.layerReaction);
        }
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView headprofileFullName;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            headprofileFullName = itemView.findViewById(R.id.headprofileFullName);
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

        public InforUserViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}