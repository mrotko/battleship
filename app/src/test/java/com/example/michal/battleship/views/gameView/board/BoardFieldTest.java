package com.example.michal.battleship.views.gameView.board;

import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.WaterFieldType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michal on 15.01.18.
 */
public class BoardFieldTest {
    @Test
    public void hit() {
        BoardField boardField = new BoardField();
        boardField.setFieldType(new WaterFieldType());
        boardField.hit();
        assertEquals(FieldStatus.HIT, boardField.getFieldType().getFieldStatus());
    }
}