package com.example.michal.battleship.model;

import android.os.Parcel;

/**
 * Created by michal on 12.12.17.
 */

public class User extends SimpleObject {

    private int id;

    private String email;

    private String customName = "none";

    private Integer points = 0;

    private Integer level = 0;

    private String hashPass;

    private String googleId = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleId() {
        return googleId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.email);
        dest.writeString(this.customName);
        dest.writeValue(this.points);
        dest.writeValue(this.level);
        dest.writeString(this.hashPass);
        dest.writeString(this.googleId);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.email = in.readString();
        this.customName = in.readString();
        this.points = (Integer) in.readValue(Integer.class.getClassLoader());
        this.level = (Integer) in.readValue(Integer.class.getClassLoader());
        this.hashPass = in.readString();
        this.googleId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
