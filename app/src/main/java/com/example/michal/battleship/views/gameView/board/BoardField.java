package com.example.michal.battleship.views.gameView.board;

import com.example.michal.battleship.model.SimpleObject;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldType;

import java.beans.PropertyChangeSupport;

/**
 * Created by michal on 17.12.17.
 */

public class BoardField extends SimpleObject {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private FieldType fieldType;

    private boolean active;

    public BoardField() {
        this.id = -1;
    }

    public BoardField(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {this.active = active; }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void hit() {
        fieldType.hit();
    }
}