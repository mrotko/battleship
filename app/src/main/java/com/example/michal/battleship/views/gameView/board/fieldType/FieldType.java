package com.example.michal.battleship.views.gameView.board.fieldType;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.beans.PropertyChangeListener;

public interface FieldType {

    void setFieldStatus(FieldStatus status);

    FieldStatus getFieldStatus();

    Drawable getDrawable();

    void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);

    void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);

    void hit();
}
