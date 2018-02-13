package pl.rotkomichal.battleship.model;

import android.os.Parcel;

/**
 * Created by michal on 18.12.17.
 */

public class MoveDTO extends SimpleObject {

    private int rowId;

    private int fieldId;

    public MoveDTO(int rowId, int fieldId) {
        this.rowId = rowId;
        this.fieldId = fieldId;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveDTO)) return false;

        MoveDTO moveDTO = (MoveDTO) o;

        if (rowId != moveDTO.rowId) return false;
        return fieldId == moveDTO.fieldId;
    }

    @Override
    public int hashCode() {
        int result = rowId;
        result = 31 * result + fieldId;
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rowId);
        dest.writeInt(this.fieldId);
        dest.writeInt(this.id);
    }

    protected MoveDTO(Parcel in) {
        this.rowId = in.readInt();
        this.fieldId = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<MoveDTO> CREATOR = new Creator<MoveDTO>() {
        @Override
        public MoveDTO createFromParcel(Parcel source) {
            return new MoveDTO(source);
        }

        @Override
        public MoveDTO[] newArray(int size) {
            return new MoveDTO[size];
        }
    };
}
