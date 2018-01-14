package com.example.michal.battleship.views.gameView.board.fieldType;

import com.example.michal.battleship.R;
import com.example.michal.battleship.model.SimpleObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WaterFieldType extends SimpleObject implements FieldType {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private FieldStatus fieldStatus = FieldStatus.VISIBLE;

    private transient int hiddenResourceId;

    private transient int waterResourceId;

    private transient int hitResourceId;

    public WaterFieldType() {
        hiddenResourceId = R.drawable.hidden_field;
        waterResourceId = R.drawable.water;
        hitResourceId = R.drawable.water_hit;
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
    public int getDrawableResourceId() {
        switch (fieldStatus) {
            case HIT:
                return hitResourceId;
            case HIDDEN:
                return hiddenResourceId;
            case VISIBLE:
                return waterResourceId;
            default:
                return R.drawable.hidden_field;
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
