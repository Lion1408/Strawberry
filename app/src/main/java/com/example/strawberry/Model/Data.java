package com.example.strawberry.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Data implements Parcelable {
    private Integer idPost, itemType;
    private String updatedAt, contentPost;
    private User user;
    private List<Image> images;
    private Reaction reactions;
    private List<Video> videos;
    private Integer countComments;

    public Data() {
    }

    public Data(Integer idPost, Integer itemType, String updatedAt, String contentPost, User user, List<Image> images, Reaction reactions, List<Video> videos, Integer countComments) {
        this.idPost = idPost;
        this.itemType = itemType;
        this.updatedAt = updatedAt;
        this.contentPost = contentPost;
        this.user = user;
        this.images = images;
        this.reactions = reactions;
        this.videos = videos;
        this.countComments = countComments;
    }

    protected Data(Parcel in) {
        if (in.readByte() == 0) {
            idPost = null;
        } else {
            idPost = in.readInt();
        }
        if (in.readByte() == 0) {
            itemType = null;
        } else {
            itemType = in.readInt();
        }
        updatedAt = in.readString();
        contentPost = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        if (in.readByte() == 0) {
            countComments = null;
        } else {
            countComments = in.readInt();
        }
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
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

    public Integer getCountComments() {
        return countComments;
    }

    public void setCountComments(Integer countComments) {
        this.countComments = countComments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "idPost=" + idPost +
                ", itemType=" + itemType +
                ", updatedAt='" + updatedAt + '\'' +
                ", contentPost='" + contentPost + '\'' +
                ", user=" + user +
                ", images=" + images +
                ", reactions=" + reactions +
                ", videos=" + videos +
                ", countComments=" + countComments +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idPost == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idPost);
        }
        if (itemType == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(itemType);
        }
        parcel.writeString(updatedAt);
        parcel.writeString(contentPost);
        parcel.writeParcelable(user, i);
        if (countComments == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(countComments);
        }
    }
}