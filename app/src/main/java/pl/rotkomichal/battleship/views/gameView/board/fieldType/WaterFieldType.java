package pl.rotkomichal.battleship.views.gameView.board.fieldType;

import android.os.Parcel;

import pl.rotkomichal.battleship.R;
import pl.rotkomichal.battleship.model.SimpleObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WaterFieldType extends SimpleObject implements IFieldType {

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private FieldStatus fieldStatus = FieldStatus.VISIBLE;

    private int hiddenResourceId;

    private int waterResourceId;

    private int hitResourceId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaterFieldType)) return false;

        WaterFieldType that = (WaterFieldType) o;

        if (hiddenResourceId != that.hiddenResourceId) return false;
        if (waterResourceId != that.waterResourceId) return false;
        if (hitResourceId != that.hitResourceId) return false;
        return fieldStatus == that.fieldStatus;
    }

    @Override
    public int hashCode() {
        int result = fieldStatus != null ? fieldStatus.hashCode() : 0;
        result = 31 * result + hiddenResourceId;
        result = 31 * result + waterResourceId;
        result = 31 * result + hitResourceId;
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fieldStatus == null ? -1 : this.fieldStatus.ordinal());
        dest.writeInt(this.hiddenResourceId);
        dest.writeInt(this.waterResourceId);
        dest.writeInt(this.hitResourceId);
        dest.writeInt(this.id);
    }

    protected WaterFieldType(Parcel in) {
        int tmpFieldStatus = in.readInt();
        this.fieldStatus = tmpFieldStatus == -1 ? null : FieldStatus.values()[tmpFieldStatus];
        this.hiddenResourceId = in.readInt();
        this.waterResourceId = in.readInt();
        this.hitResourceId = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<WaterFieldType> CREATOR = new Creator<WaterFieldType>() {
        @Override
        public WaterFieldType createFromParcel(Parcel source) {
            return new WaterFieldType(source);
        }

        @Override
        public WaterFieldType[] newArray(int size) {
            return new WaterFieldType[size];
        }
    };
}
