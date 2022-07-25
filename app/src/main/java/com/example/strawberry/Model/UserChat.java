package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserChat implements Parcelable {
    private String username, linkavt, status, time, content;
    private Integer idUser;

    public UserChat() {
    }

    public UserChat(String username, String linkavt, String status, String time, String content, Integer idUser) {
        this.username = username;
        this.linkavt = linkavt;
        this.status = status;
        this.time = time;
        this.content = content;
        this.idUser = idUser;
    }

    protected UserChat(Parcel in) {
        username = in.readString();
        linkavt = in.readString();
        status = in.readString();
        time = in.readString();
        content = in.readString();
        if (in.readByte() == 0) {
            idUser = null;
        } else {
            idUser = in.readInt();
        }
    }

    public static final Creator<UserChat> CREATOR = new Creator<UserChat>() {
        @Override
        public UserChat createFromParcel(Parcel in) {
            return new UserChat(in);
        }

        @Override
        public UserChat[] newArray(int size) {
            return new UserChat[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLinkavt() {
        return linkavt;
    }

    public void setLinkavt(String linkavt) {
        this.linkavt = linkavt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "username='" + username + '\'' +
                ", linkavt='" + linkavt + '\'' +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", idUser=" + idUser +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(linkavt);
        parcel.writeString(status);
        parcel.writeString(time);
        parcel.writeString(content);
        if (idUser == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idUser);
        }
    }
}
