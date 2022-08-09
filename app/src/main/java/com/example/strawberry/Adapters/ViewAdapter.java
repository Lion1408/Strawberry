package com.example.strawberry.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ImageOnClick;
import com.example.strawberry.Interfaces.InforUserOnClick;
import com.example.strawberry.Interfaces.OnClickChange;
import com.example.strawberry.Interfaces.OnClickUpPost;
import com.example.strawberry.Interfaces.OnClickVideo;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Post;
import com.example.strawberry.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> list;
    private Context context;
    private PostOnClick postOnClick;
    private InforUserOnClick inforUserOnClick;
    private OnClickUpPost onClickUpPost;
    private ImageOnClick imageOnClick;
    private OnClickChange onClickChange;
    private OnClickVideo onClickVideo;

    public void setOnClickVideo(OnClickVideo onClickVideo) {
        this.onClickVideo = onClickVideo;
    }

    public void setOnClickChange(OnClickChange onClickChange) {
        this.onClickChange = onClickChange;
    }
    public void setImageOnClick(ImageOnClick imageOnClick) {
        this.imageOnClick = imageOnClick;
    }

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

    public void setList(List<Post> list) {
        this.list = list;
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
        } else if (viewType == Constants.NOTIFICATION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new NotiViewHolder(view);
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
            if (post.getStatusUser()) {
                holder.statusUser.setBackgroundResource(R.drawable.background_online);
            } else {
                holder.statusUser.setBackgroundResource(R.drawable.background_off);
            }
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
            if (post.getStatusUser()) {
                holder.statusUser.setBackgroundResource(R.drawable.background_online);
            } else {
                holder.statusUser.setBackgroundResource(R.drawable.background_off);
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
            if (post.getIdLog() == 0) {
                time = "time up";
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
                holder.video.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = post.getLinkVideo();
                        youTubePlayer.cueVideo(videoId, 0);
                        DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(holder.video, youTubePlayer);
                        holder.video.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                        defaultPlayerUiController.setFullScreenButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onClickVideo.OnClick(post.getLinkVideo());                            }
                        });
                    }

                });
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
                imageOnClick.onclickImage(post.getLinkImage());
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
            Glide.with(holder.avt).load(post.getLinkAvt()).into(holder.avt);
            Glide.with(holder.cover).load(post.getLinkCover()).into(holder.cover);
            holder.cover.setOnClickListener(v -> {
                imageOnClick.onclickImage(post.getLinkCover());
            });

            holder.avt.setOnClickListener(v -> {
                imageOnClick.onclickImage(post.getLinkAvt());
            });

            holder.changeCover.setOnClickListener(v -> {
                onClickChange.OnClickChangeCover();
            });

            holder.changeAvt.setOnClickListener(v -> {
                onClickChange.OnClickChangeAvt();
            });
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

        if (post.getItemType() == Constants.NOTIFICATION) {
            NotiViewHolder holder = (NotiViewHolder) x;
            Glide.with(holder.avt).load(post.getLinkAvt()).into(holder.avt);
            holder.text.setText(post.getFullName() +" có một bài viết mới!");
            holder.noti.setOnClickListener(v -> {
                postOnClick.OnClickPost(post);
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
        ImageView img, reactImg, deletePost, statusUser;
        ConstraintLayout reactPost, layerReaction, comment;
        YouTubePlayerView video;
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
            statusUser = itemView.findViewById(R.id.statusUser);
        }
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView username, biography;
        ImageView cover;
        CircleImageView avt;
        ImageView changeCover, changeAvt;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);

            avt = itemView.findViewById(R.id.avtUser);
            cover = itemView.findViewById(R.id.imgCoverUser);
            changeCover = itemView.findViewById(R.id.changeCover);
            changeAvt = itemView.findViewById(R.id.changeAvt);
        }
    }

    public class UpPostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avt;
        ConstraintLayout uppost;
        ImageView statusUser;
        public UpPostViewHolder(@NonNull View itemView) {
            super(itemView);
            avt = itemView.findViewById(R.id.icInforUser);
            uppost = itemView.findViewById(R.id.uppost);
            statusUser = itemView.findViewById(R.id.statusUser);
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
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            avtComment = itemView.findViewById(R.id.avtComment);
            content = itemView.findViewById(R.id.textComment);
            username = itemView.findViewById(R.id.fullName);
        }
    }

    public class NotiViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avt;
        TextView text;
        ConstraintLayout noti;
        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            avt = itemView.findViewById(R.id.avtnoti);
            text = itemView.findViewById(R.id.noti);
            noti = itemView.findViewById(R.id.notiFy);
        }
    }
}