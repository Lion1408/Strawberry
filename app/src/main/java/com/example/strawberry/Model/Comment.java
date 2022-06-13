package com.example.strawberry.Model;

public class Comment {
    private String cmt;

    public Comment() {
    }

    public Comment(String cmt) {
        this.cmt = cmt;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cmt='" + cmt + '\'' +
                '}';
    }
}
