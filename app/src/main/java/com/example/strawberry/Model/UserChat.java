package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserChat implements Parcelable {
    private String fullName, linkAvt, time, content;
    private Integer idUser;
    private Boolean status;
    public UserChat() {
    }

    public UserChat(String fullName, String linkAvt, String time, String content, Integer idUser, Boolean status) {
        this.fullName = fullName;
        this.linkAvt = linkAvt;
        this.time = time;
        this.content = content;
        this.idUser = idUser;
        this.status = status;
    }

    protected UserChat(Parcel in) {
        fullName = in.readString();
        linkAvt = in.readString();
        time = in.readString();
        content = in.readString();
        if (in.readByte() == 0) {
            idUser = null;
        } else {
            idUser = in.readInt();
        }
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(linkAvt);
        dest.writeString(time);
        dest.writeString(content);
        if (idUser == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idUser);
        }
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
