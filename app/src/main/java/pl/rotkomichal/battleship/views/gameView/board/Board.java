package pl.rotkomichal.battleship.views.gameView.board;

import android.os.Parcel;

import pl.rotkomichal.battleship.model.SimpleObject;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.FieldStatus;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.WaterFieldType;
import pl.rotkomichal.battleship.views.gameView.board.ship.Ship;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by michal on 17.12.17.
 */

public class Board extends SimpleObject {

    private final int ROW_LENGTH = 10;

    private final int COLUMN_LENGTH = 10;

    private final int FIELD_SIZE = 50;

    private List<Ship> ships = new ArrayList<>();

    private List<BoardRow> boardRows = new ArrayList<>();

    public Board() {
        createTable();
    }

    public Board(List<Ship> ships) {
        super();
        this.ships = ships;
        createTable();
    }

    public void createTable() {
        boardRows.clear();
        IntStream
                .range(0, COLUMN_LENGTH)
                .forEach(i -> boardRows.add(createBoardRow(i)));
        applyShips();
    }

    private void applyShips() {
        ships.forEach(ship -> ship.getFields().forEach(boardField -> {
            boardRows.get(boardField.getRowId())
                    .getFields().set(boardField.getId(), boardField);
        }));
    }

    private BoardRow createBoardRow(int id) {
        BoardRow boardRow = new BoardRow(id);
        IntStream.range(0, ROW_LENGTH).forEach(i -> {
            BoardField boardField = new BoardField(i);
            boardField.setRowId(id);
            boardField.setFieldType(new WaterFieldType());
            boardRow.getFields().add(boardField);
        });
        return boardRow;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<BoardRow> getRows() {
        return boardRows;
    }

    public void setBoardRows(List<BoardRow> boardRows) {
        this.boardRows = boardRows;
    }


    public int getRowLength() {
        return ROW_LENGTH;
    }

    public int getColumnLength() {
        return COLUMN_LENGTH;
    }

    public int getFieldSize() {
        return FIELD_SIZE;
    }

    public void setFieldStatusToAll(FieldStatus status) {
        getRows().forEach(boardRow -> boardRow.getFields()
                .forEach(boardField -> boardField.getFieldType().setFieldStatus(status)));

    }

    public void setActiveToAll(boolean active) {
        boardRows.forEach(boardRow -> boardRow.getFields()
                .forEach(boardField -> boardField.setActive(active)));
    }

    public void addFieldStatusListener(PropertyChangeListener propertyChangeListener) {
        boardRows.forEach(boardRow -> {
            boardRow.getFields().forEach(boardField -> {
                boardField.getFieldType().addPropertyChangeListener(propertyChangeListener);
            });
        });
    }

    public boolean isEndGame() {
        return ships.stream().allMatch(Ship::isSunken);
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        if (!getShips().equals(board.getShips())) return false;
        return boardRows.equals(board.boardRows);
    }

    @Override
    public int hashCode() {
        int result = ROW_LENGTH;
        result = 31 * result + COLUMN_LENGTH;
        result = 31 * result + FIELD_SIZE;
        result = 31 * result + getShips().hashCode();
        result = 31 * result + boardRows.hashCode();
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ships);
        dest.writeTypedList(this.boardRows);
        dest.writeInt(this.id);
    }

    protected Board(Parcel in) {
        this.ships = in.createTypedArrayList(Ship.CREATOR);
        this.boardRows = in.createTypedArrayList(BoardRow.CREATOR);
        this.id = in.readInt();
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel source) {
            return new Board(source);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };
}
