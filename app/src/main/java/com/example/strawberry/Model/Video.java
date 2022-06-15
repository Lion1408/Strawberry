package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    private String linkVideo;

    public Video() {
    }

    public Video(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    protected Video(Parcel in) {
        linkVideo = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(linkVideo);
    }
}
