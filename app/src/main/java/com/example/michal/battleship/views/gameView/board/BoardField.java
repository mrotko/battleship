package com.example.michal.battleship.views.gameView.board;

import android.os.Parcel;

import com.example.michal.battleship.model.SimpleObject;
import com.example.michal.battleship.views.gameView.board.fieldType.IFieldType;

/**
 * Created by michal on 17.12.17.
 */

public class BoardField extends SimpleObject {

    private IFieldType fieldType;

    private int rowId;

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

    public IFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(IFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void hit() {
        fieldType.hit();
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getRowId(){
        return this.rowId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.fieldType, flags);
        dest.writeInt(this.rowId);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
    }

    protected BoardField(Parcel in) {
        this.fieldType = in.readParcelable(IFieldType.class.getClassLoader());
        this.rowId = in.readInt();
        this.active = in.readByte() != 0;
        this.id = in.readInt();
    }

    public static final Creator<BoardField> CREATOR = new Creator<BoardField>() {
        @Override
        public BoardField createFromParcel(Parcel source) {
            return new BoardField(source);
        }

        @Override
        public BoardField[] newArray(int size) {
            return new BoardField[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardField)) return false;

        BoardField that = (BoardField) o;

        if (rowId != that.rowId) return false;
        if (active != that.active) return false;
        return fieldType != null ? fieldType.equals(that.fieldType) : that.fieldType == null;
    }

    @Override
    public int hashCode() {
        int result = fieldType != null ? fieldType.hashCode() : 0;
        result = 31 * result + rowId;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}