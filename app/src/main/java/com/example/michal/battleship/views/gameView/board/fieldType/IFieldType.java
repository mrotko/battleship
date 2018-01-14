package com.example.michal.battleship.views.gameView.board.fieldType;

import android.os.Parcelable;

import java.beans.PropertyChangeListener;

public interface IFieldType extends Parcelable {

    void setFieldStatus(FieldStatus status);

    FieldStatus getFieldStatus();

    int getDrawableResourceId();

    void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);

    void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);

    void hit();
}
