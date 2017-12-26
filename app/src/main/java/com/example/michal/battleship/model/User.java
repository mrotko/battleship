package com.example.michal.battleship.model;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by michal on 12.12.17.
 */

public class User {

    private int id;

    private String email;

    private String customName;

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
}
