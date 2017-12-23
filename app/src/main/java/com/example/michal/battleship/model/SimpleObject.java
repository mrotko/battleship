package com.example.michal.battleship.model;

import java.io.Serializable;

/**
 * Created by michal on 17.12.17.
 */

public abstract class SimpleObject implements Serializable {

    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
