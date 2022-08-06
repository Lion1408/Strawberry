package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Post implements Parcelable {
    private String content, time, linkAvt, linkImage, linkVideo, fullName, linkCover,biography;
    private Integer comment, reaction, idUser, itemType, idPost, idLog, idCmt;
    private Boolean actionReact, statusUser;

    public Post() {
    }

    public Post(String content, String time, String linkAvt, String linkImage, String linkVideo, String fullName, String linkCover, String biography, Integer comment, Integer reaction, Integer idUser, Integer itemType, Integer idPost, Integer idLog, Integer idCmt, Boolean actionReact, Boolean statusUser) {
        this.content = content;
        this.time = time;
        this.linkAvt = linkAvt;
        this.linkImage = linkImage;
        this.linkVideo = linkVideo;
        this.fullName = fullName;
        this.linkCover = linkCover;
        this.biography = biography;
        this.comment = comment;
        this.reaction = reaction;
        this.idUser = idUser;
        this.itemType = itemType;
        this.idPost = idPost;
        this.idLog = idLog;
        this.idCmt = idCmt;
        this.actionReact = actionReact;
        this.statusUser = statusUser;
    }

    protected Post(Parcel in) {
        content = in.readString();
        time = in.readString();
        linkAvt = in.readString();
        linkImage = in.readString();
        linkVideo = in.readString();
        fullName = in.readString();
        linkCover = in.readString();
        biography = in.readString();
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
        if (in.readByte() == 0) {
            idPost = null;
        } else {
            idPost = in.readInt();
        }
        if (in.readByte() == 0) {
            idLog = null;
        } else {
            idLog = in.readInt();
        }
        if (in.readByte() == 0) {
            idCmt = null;
        } else {
            idCmt = in.readInt();
        }
        byte tmpActionReact = in.readByte();
        actionReact = tmpActionReact == 0 ? null : tmpActionReact == 1;
        byte tmpStatusUser = in.readByte();
        statusUser = tmpStatusUser == 0 ? null : tmpStatusUser == 1;
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

    public String getLinkCover() {
        return linkCover;
    }

    public void setLinkCover(String linkCover) {
        this.linkCover = linkCover;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdCmt() {
        return idCmt;
    }

    public void setIdCmt(Integer idCmt) {
        this.idCmt = idCmt;
    }

    public Boolean getActionReact() {
        return actionReact;
    }

    public void setActionReact(Boolean actionReact) {
        this.actionReact = actionReact;
    }

    public Boolean getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(Boolean statusUser) {
        this.statusUser = statusUser;
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
                ", linkCover='" + linkCover + '\'' +
                ", biography='" + biography + '\'' +
                ", comment=" + comment +
                ", reaction=" + reaction +
                ", idUser=" + idUser +
                ", itemType=" + itemType +
                ", idPost=" + idPost +
                ", idLog=" + idLog +
                ", idCmt=" + idCmt +
                ", actionReact=" + actionReact +
                ", statusUser=" + statusUser +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeString(time);
        parcel.writeString(linkAvt);
        parcel.writeString(linkImage);
        parcel.writeString(linkVideo);
        parcel.writeString(fullName);
        parcel.writeString(linkCover);
        parcel.writeString(biography);
        if (comment == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(comment);
        }
        if (reaction == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(reaction);
        }
        if (idUser == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idUser);
        }
        if (itemType == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(itemType);
        }
        if (idPost == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idPost);
        }
        if (idLog == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idLog);
        }
        if (idCmt == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idCmt);
        }
        parcel.writeByte((byte) (actionReact == null ? 0 : actionReact ? 1 : 2));
        parcel.writeByte((byte) (statusUser == null ? 0 : statusUser ? 1 : 2));
    }
}
