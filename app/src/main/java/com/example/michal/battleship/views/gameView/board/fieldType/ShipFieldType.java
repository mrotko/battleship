package com.example.michal.battleship.views.gameView.board.fieldType;

import android.os.Parcel;

import com.example.michal.battleship.R;
import com.example.michal.battleship.model.SimpleObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShipFieldType extends SimpleObject implements IFieldType {

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private FieldStatus fieldStatus = FieldStatus.VISIBLE;

    private transient int hiddenResourceId;

    private transient int shipResourceId;

    private transient int hitResourceId;

    public ShipFieldType() {
        hiddenResourceId = R.drawable.hidden_field;
        shipResourceId = R.drawable.ship;
        hitResourceId = R.drawable.ship_hit;
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
                return shipResourceId;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fieldStatus == null ? -1 : this.fieldStatus.ordinal());
        dest.writeInt(this.id);
    }

    protected ShipFieldType(Parcel in) {
        int tmpFieldStatus = in.readInt();
        this.fieldStatus = tmpFieldStatus == -1 ? null : FieldStatus.values()[tmpFieldStatus];
        this.id = in.readInt();
    }

    public static final Creator<ShipFieldType> CREATOR = new Creator<ShipFieldType>() {
        @Override
        public ShipFieldType createFromParcel(Parcel source) {
            return new ShipFieldType(source);
        }

        @Override
        public ShipFieldType[] newArray(int size) {
            return new ShipFieldType[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShipFieldType)) return false;

        ShipFieldType that = (ShipFieldType) o;

        if (hiddenResourceId != that.hiddenResourceId) return false;
        if (shipResourceId != that.shipResourceId) return false;
        if (hitResourceId != that.hitResourceId) return false;
        if (propertyChangeSupport != null ? !propertyChangeSupport.equals(that.propertyChangeSupport) : that.propertyChangeSupport != null)
            return false;
        return fieldStatus == that.fieldStatus;
    }

    @Override
    public int hashCode() {
        int result = propertyChangeSupport != null ? propertyChangeSupport.hashCode() : 0;
        result = 31 * result + (fieldStatus != null ? fieldStatus.hashCode() : 0);
        result = 31 * result + hiddenResourceId;
        result = 31 * result + shipResourceId;
        result = 31 * result + hitResourceId;
        return result;
    }
}
