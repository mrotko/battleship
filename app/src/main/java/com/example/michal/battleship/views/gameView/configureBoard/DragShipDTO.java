package com.example.michal.battleship.views.gameView.configureBoard;

import com.example.michal.battleship.views.gameView.board.BoardField;
import com.example.michal.battleship.views.gameView.board.BoardFieldView;
import com.example.michal.battleship.views.gameView.board.BoardRow;
import com.example.michal.battleship.views.gameView.board.ship.Ship;
import com.example.michal.battleship.views.gameView.board.ship.ShipView;

/**
 * Created by michal on 16.12.17.
 */

public class DragShipDTO {

    private BoardRow boardRow;

    private BoardField boardField;

    private Ship ship;

    public BoardRow getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(BoardRow boardRow) {
        this.boardRow = boardRow;
    }

    public BoardField getBoardField() {
        return boardField;
    }

    public void setBoardField(BoardField boardField) {
        this.boardField = boardField;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
