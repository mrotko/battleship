package com.example.michal.battleship.views.gameView.board.ship;

import com.example.michal.battleship.views.gameView.board.BoardField;
import com.example.michal.battleship.views.gameView.board.fieldType.ShipFieldType;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by michal on 15.01.18.
 */
public class ShipTest {

    @Test
    public void sunkedTest() {
        Ship ship = new Ship();
        ship.setDirection(Direction.HORIZONTAL);
        for(int i = 0; i < 3; i++) {
            BoardField boardField = new BoardField(i);
            boardField.setActive(true);
            boardField.setRowId(i);
            boardField.setFieldType(new ShipFieldType());
            ship.getFields().add(boardField);
        }

        assertFalse(ship.isSunken());

        ship.getFields().forEach(boardField -> boardField.getFieldType().hit());

        assertTrue(ship.isSunken());
    }
}