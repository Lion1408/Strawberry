package com.example.strawberry.Interfaces;

import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Image;
import com.example.strawberry.Model.Post;
import com.example.strawberry.Model.Reaction;
import com.example.strawberry.Model.User;
import com.example.strawberry.Model.Video;

import java.util.List;

public interface PostOnClick {
    void OnClickAvt(Post post);
    void OnClickPost(Post post);
    void OnClickReact(Post post);
}
