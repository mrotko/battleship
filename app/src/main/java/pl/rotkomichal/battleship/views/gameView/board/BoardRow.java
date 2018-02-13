package pl.rotkomichal.battleship.views.gameView.board;

import android.os.Parcel;

import pl.rotkomichal.battleship.model.SimpleObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 17.12.17.
 */

public class BoardRow extends SimpleObject {

    private List<BoardField> fields = new ArrayList<>();

    public BoardRow(int id) {
        this.id = id;
    }


    public List<BoardField> getFields() {
        return fields;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.fields);
        dest.writeInt(this.id);
    }

    protected BoardRow(Parcel in) {
        this.fields = in.createTypedArrayList(BoardField.CREATOR);
        this.id = in.readInt();
    }

    public static final Creator<BoardRow> CREATOR = new Creator<BoardRow>() {
        @Override
        public BoardRow createFromParcel(Parcel source) {
            return new BoardRow(source);
        }

        @Override
        public BoardRow[] newArray(int size) {
            return new BoardRow[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardRow)) return false;

        BoardRow boardRow = (BoardRow) o;

        return fields != null ? fields.equals(boardRow.fields) : boardRow.fields == null;
    }

    @Override
    public int hashCode() {
        return fields != null ? fields.hashCode() : 0;
    }
}
