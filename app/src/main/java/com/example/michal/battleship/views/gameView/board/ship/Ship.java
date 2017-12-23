package com.example.michal.battleship.views.gameView.board.ship;

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
}
