package com.example.strawberry.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.strawberry.Activities.ChatActivity;
import com.example.strawberry.Activities.MainActivity;
import com.example.strawberry.Activities.PostActivity;
import com.example.strawberry.Activities.ProfileUserActivity;
import com.example.strawberry.Activities.UpPostActivity;
import com.example.strawberry.Adapters.ViewAdapter;
import com.example.strawberry.Define.Constants;
import com.example.strawberry.Interfaces.ImageOnClick;
import com.example.strawberry.Interfaces.OnClickUpPost;
import com.example.strawberry.Interfaces.OnClickVideo;
import com.example.strawberry.Interfaces.PostOnClick;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.User;
import com.example.strawberry.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class WatchFragment extends Fragment {
    Boolean isFirstCall = true;
    ViewAdapter viewAdapter;
    List<Post> list;
    String linkavt = "https://firebasestorage.googleapis.com/v0/b/strawberry-7ebce.appspot.com/o/default%2FAnime-chibi%20(7).jpg?alt=media&token=82f0b4dc-d3a0-4b2e-aeb4-b2fb70abab99";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch, container, false);
        SearchView searchView = view.findViewById(R.id.searchWatch);
        RecyclerView recyclerView = view.findViewById(R.id.recy_post);
        list = new ArrayList<>();
        viewAdapter = new ViewAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView.clearFocus();

        recyclerView.setHasFixedSize(true);
        searchView.setOnClickListener(v -> {
            searchView.setMaxWidth(200);
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        viewAdapter.PostOnClick(new PostOnClick() {
            @Override
            public void OnClickAvt(Post post) {

            }

            @Override
            public void OnClickPost(Post post) {

            }

            @Override
            public void OnClickReact(Post post) {

            }

            @Override
            public void OnClickDelete(Post post) {

            }


        });
        viewAdapter.setOnClickVideo(new OnClickVideo() {
            @Override
            public void OnClick(String videoId) {
                Dialog dialog = new Dialog(getContext(), R.style.Theme_Strawberry);
                dialog.setContentView(R.layout.dialog_show_video);
                ImageView back;
                YouTubePlayerView video;
                video = dialog.findViewById(R.id.showVideo);
                video.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);
                        youTubePlayer.cueVideo(videoId, 0);
                    }
                });
                back = dialog.findViewById(R.id.back);
                back.setOnClickListener(v -> {
                    dialog.dismiss();
                });
                dialog.show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot i : snapshot.child("videos").getChildren()) {
                    Post post = i.getValue(Post.class);
                    post.setLinkAvt(linkavt);
                    post.setStatusUser(true);
                    post.setItemType(Constants.POST);
                    post.setIdLog(0);
                    post.setIdUser(1);
                    post.setFullName("Admin");
                    post.setActionReact(true);
                    post.setReaction(0);
                    post.setTime("1659538027637");
                    post.setComment(0);
                    post.setIdPost(0);
                    list.add(post);
                }
                if (isFirstCall) {
                    recyclerView.setAdapter(viewAdapter);
                    isFirstCall = false;
                } else {
                    viewAdapter.notifyDataSetChanged();
                }
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        filterList(s);
                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    public void filterList(String s) {
        List <Post> newList = new ArrayList<>();
        for (Post post : list) {
            if (post.getContent().toLowerCase().contains(s.toLowerCase())) {
                newList.add(post);
            }
        }
        viewAdapter.setList(newList);
        if (list.isEmpty()) {

        } else {
            viewAdapter.notifyDataSetChanged();
        }
    }
}