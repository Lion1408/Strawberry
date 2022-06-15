package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String linkImage;

    public Image() {
    }

    public Image(String linkImage) {
        this.linkImage = linkImage;
    }

    protected Image(Parcel in) {
        linkImage = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    @Override
    public String toString() {
        return "Image{" +
                "linkImage='" + linkImage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(linkImage);
    }
}
