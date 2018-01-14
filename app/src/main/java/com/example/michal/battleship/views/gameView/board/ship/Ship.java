package com.example.michal.battleship.views.gameView.board.ship;

import android.os.Parcel;

import com.example.michal.battleship.model.SimpleObject;
import com.example.michal.battleship.views.gameView.board.BoardField;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 17.12.17.
 */

public class Ship extends SimpleObject {

    private List<BoardField> fields = new ArrayList<>();

    private Direction direction;

    public List<BoardField> getFields() {
        return fields;
    }

    public void setFields(List<BoardField> fields) {
        this.fields = fields;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSize() {
        return fields.size();
    }

    public boolean isSunken() {
        return fields.stream()
                .map(BoardField::getFieldType)
                .allMatch(fieldType -> fieldType.getFieldStatus().equals(FieldStatus.HIT));
    }


    public Ship() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.fields);
        dest.writeInt(this.direction == null ? -1 : this.direction.ordinal());
        dest.writeInt(this.id);
    }

    protected Ship(Parcel in) {
        this.fields = in.createTypedArrayList(BoardField.CREATOR);
        int tmpDirection = in.readInt();
        this.direction = tmpDirection == -1 ? null : Direction.values()[tmpDirection];
        this.id = in.readInt();
    }

    public static final Creator<Ship> CREATOR = new Creator<Ship>() {
        @Override
        public Ship createFromParcel(Parcel source) {
            return new Ship(source);
        }

        @Override
        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;

        Ship ship = (Ship) o;

        if (fields != null ? !fields.equals(ship.fields) : ship.fields != null) return false;
        return direction == ship.direction;
    }

    @Override
    public int hashCode() {
        int result = fields != null ? fields.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
