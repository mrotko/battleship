package com.example.michal.battleship.model;

/**
 * Created by michal on 18.12.17.
 */

public class MoveDTO extends SimpleObject {

    int rowId;

    int fieldId;

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
}
