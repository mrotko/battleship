package com.example.michal.battleship.views.gameView.board.fieldType;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.example.michal.battleship.model.SimpleObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShipFieldType extends SimpleObject implements FieldType {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private FieldStatus fieldStatus = FieldStatus.VISIBLE;

    private transient Drawable hiddenDrawable;

    private transient Drawable shipDrawable;

    private transient Drawable hitDrawable;

    public ShipFieldType() {
        hiddenDrawable = new ColorDrawable(Resources.getSystem().getColor(android.R.color.darker_gray, Resources.getSystem().newTheme()));
        shipDrawable = new ColorDrawable(Resources.getSystem().getColor(android.R.color.holo_orange_dark, Resources.getSystem().newTheme()));
        hitDrawable = new ColorDrawable(Resources.getSystem().getColor(android.R.color.holo_red_dark, Resources.getSystem().newTheme()));
    }


    @Override
    public void setFieldStatus(FieldStatus status) {
        FieldStatus oldValue = fieldStatus;
        fieldStatus = status;
        propertyChangeSupport.firePropertyChange(this.getClass().getSimpleName(), oldValue, fieldStatus);
    }

    @Override
    public FieldStatus getFieldStatus() {
        return fieldStatus;
    }

    @Override
    public Drawable getDrawable() {
        switch (fieldStatus) {
            case HIT:
                return hitDrawable;
            case HIDDEN:
                return hiddenDrawable;
            case VISIBLE:
                return shipDrawable;
            default:
                return null;
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void hit() {
        setFieldStatus(FieldStatus.HIT);
    }


}
