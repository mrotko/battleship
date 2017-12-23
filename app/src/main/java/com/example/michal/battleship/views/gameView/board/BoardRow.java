package com.example.michal.battleship.views.gameView.board;

import com.example.michal.battleship.model.SimpleObject;

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

    public void setFields(List<BoardField> fields) {
        this.fields = fields;
    }
}
