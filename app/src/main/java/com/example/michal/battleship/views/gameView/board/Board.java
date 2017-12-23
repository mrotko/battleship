package com.example.michal.battleship.views.gameView.board;

import com.example.michal.battleship.model.SimpleObject;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.WaterFieldType;
import com.example.michal.battleship.views.gameView.board.ship.Ship;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
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

    public void createTable() {
        IntStream
                .range(0, COLUMN_LENGTH)
                .forEach(i -> boardRows.add(createBoardRow(i)));
    }

    private BoardRow createBoardRow(int id) {
        BoardRow boardRow = new BoardRow(id);
        IntStream.range(0, ROW_LENGTH).forEach(i -> {
            BoardField boardField = new BoardField(i);
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

    public void setFieldStatusListener(PropertyChangeListener propertyChangeListener) {
        boardRows.forEach(boardRow -> {
            boardRow.getFields().forEach(boardField -> {
                boardField.getFieldType().addPropertyChangeListener(propertyChangeListener);
            });
        });
    }

    public boolean isEndGame() {
        return ships.stream().allMatch(Ship::isSunken);
    }
}
