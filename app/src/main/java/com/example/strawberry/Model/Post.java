package com.example.strawberry.Model;

public class Post {
    private String access, contentPost;
    private Integer idUser;

    public Post() {
    }

    public Post(String access, String contentPost, Integer idUser) {
        this.access = access;
        this.contentPost = contentPost;
        this.idUser = idUser;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Post{" +
                "access='" + access + '\'' +
                ", contentPost='" + contentPost + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
