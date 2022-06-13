package com.example.strawberry.Model;


import java.util.List;

public class Post {
    private Integer id, itemType;
    private String updatedAt, contentPost;
    User user;
    List <Image> images;
    Reaction reactions;
    List <Video> videos;
    List <Comment> comments;

    public Post() {
    }

    public Post(Integer id, Integer itemType, String updatedAt, String contentPost, User user, List<Image> images, Reaction reactions, List<Video> videos, List<Comment> comments) {
        this.id = id;
        this.itemType = itemType;
        this.updatedAt = updatedAt;
        this.contentPost = contentPost;
        this.user = user;
        this.images = images;
        this.reactions = reactions;
        this.videos = videos;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Reaction getReactions() {
        return reactions;
    }

    public void setReactions(Reaction reactions) {
        this.reactions = reactions;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", itemType=" + itemType +
                ", updatedAt='" + updatedAt + '\'' +
                ", contentPost='" + contentPost + '\'' +
                ", user=" + user +
                ", images=" + images +
                ", reactions=" + reactions +
                ", videos=" + videos +
                ", comments=" + comments +
                '}';
    }
}