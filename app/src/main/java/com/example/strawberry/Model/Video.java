package com.example.strawberry.Model;

public class Video {
    private String linkVideo;

    public Video() {
    }

    public Video(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    @Override
    public String toString() {
        return "Video{" +
                "linkVideo='" + linkVideo + '\'' +
                '}';
    }
}
