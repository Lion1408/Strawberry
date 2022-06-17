package com.example.strawberry.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class User  implements Parcelable{
    private String firstName, lastName, fullName, email, password, phoneNumber, linkAvt,
                    gender, birthday, address, biography;
    private Integer idUser;

    public User() {
    }

    public User(String firstName, String lastName, String fullName, String email, String password, String phoneNumber, String linkAvt, String gender, String birthday, String address, String biography, Integer idUser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.linkAvt = linkAvt;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.biography = biography;
        this.idUser = idUser;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        fullName = in.readString();
        email = in.readString();
        password = in.readString();
        phoneNumber = in.readString();
        linkAvt = in.readString();
        gender = in.readString();
        birthday = in.readString();
        address = in.readString();
        biography = in.readString();
        if (in.readByte() == 0) {
            idUser = null;
        } else {
            idUser = in.readInt();
        }
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", linkAvt='" + linkAvt + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", biography='" + biography + '\'' +
                ", idUser=" + idUser +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(fullName);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(phoneNumber);
        parcel.writeString(linkAvt);
        parcel.writeString(gender);
        parcel.writeString(birthday);
        parcel.writeString(address);
        parcel.writeString(biography);
        if (idUser == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idUser);
        }
    }
}
