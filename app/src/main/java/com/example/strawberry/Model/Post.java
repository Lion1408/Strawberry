package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Post implements Parcelable {
    private String content, time, linkAvt, linkImage, linkVideo, fullName;
    private Integer comment, reaction, idUser, itemType;

    public Post() {
    }

    public Post(String content, String time, String linkAvt, String linkImage, String linkVideo, String fullName, Integer comment, Integer reaction, Integer idUser, Integer itemType) {
        this.content = content;
        this.time = time;
        this.linkAvt = linkAvt;
        this.linkImage = linkImage;
        this.linkVideo = linkVideo;
        this.fullName = fullName;
        this.comment = comment;
        this.reaction = reaction;
        this.idUser = idUser;
        this.itemType = itemType;
    }

    protected Post(Parcel in) {
        content = in.readString();
        time = in.readString();
        linkAvt = in.readString();
        linkImage = in.readString();
        linkVideo = in.readString();
        fullName = in.readString();
        if (in.readByte() == 0) {
            comment = null;
        } else {
            comment = in.readInt();
        }
        if (in.readByte() == 0) {
            reaction = null;
        } else {
            reaction = in.readInt();
        }
        if (in.readByte() == 0) {
            idUser = null;
        } else {
            idUser = in.readInt();
        }
        if (in.readByte() == 0) {
            itemType = null;
        } else {
            itemType = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(time);
        dest.writeString(linkAvt);
        dest.writeString(linkImage);
        dest.writeString(linkVideo);
        dest.writeString(fullName);
        if (comment == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(comment);
        }
        if (reaction == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reaction);
        }
        if (idUser == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idUser);
        }
        if (itemType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemType);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getReaction() {
        return reaction;
    }

    public void setReaction(Integer reaction) {
        this.reaction = reaction;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Post{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", linkAvt='" + linkAvt + '\'' +
                ", linkImage='" + linkImage + '\'' +
                ", linkVideo='" + linkVideo + '\'' +
                ", fullName='" + fullName + '\'' +
                ", comment=" + comment +
                ", reaction=" + reaction +
                ", idUser=" + idUser +
                ", itemType=" + itemType +
                '}';
    }
}
