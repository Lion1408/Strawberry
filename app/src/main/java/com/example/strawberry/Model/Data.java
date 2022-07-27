package com.example.strawberry.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Data implements Parcelable {
    private User user;

    public Data() {
    }

    public Data(User user) {
        this.user = user;
    }

    protected Data(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Data{" +
                "user=" + user +
                '}';
    }
}