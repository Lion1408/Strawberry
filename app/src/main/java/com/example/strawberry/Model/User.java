package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Parcelable{
    private String firstName, lastName, fullName, email, password,
            linkAvt, linkCover, gender, birthday, address, biography,
            place;
    private Integer idUser;
    private Boolean status;

    public User() {
    }

    public User(String firstName, String lastName, String fullName, String email, String password, String linkAvt, String linkCover, String gender, String birthday, String address, String biography, String place, Integer idUser, Boolean status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.linkAvt = linkAvt;
        this.linkCover = linkCover;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.biography = biography;
        this.place = place;
        this.idUser = idUser;
        this.status = status;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        fullName = in.readString();
        email = in.readString();
        password = in.readString();
        linkAvt = in.readString();
        linkCover = in.readString();
        gender = in.readString();
        birthday = in.readString();
        address = in.readString();
        biography = in.readString();
        place = in.readString();
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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(linkAvt);
        dest.writeString(linkCover);
        dest.writeString(gender);
        dest.writeString(birthday);
        dest.writeString(address);
        dest.writeString(biography);
        dest.writeString(place);
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

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
    }

    public String getLinkCover() {
        return linkCover;
    }

    public void setLinkCover(String linkCover) {
        this.linkCover = linkCover;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", linkAvt='" + linkAvt + '\'' +
                ", linkCover='" + linkCover + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", biography='" + biography + '\'' +
                ", place='" + place + '\'' +
                ", idUser=" + idUser +
                ", status=" + status +
                '}';
    }
}
