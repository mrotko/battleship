package pl.rotkomichal.battleship.views.gameView.configureBoard;

import pl.rotkomichal.battleship.views.gameView.board.BoardField;
import pl.rotkomichal.battleship.views.gameView.board.BoardRow;
import pl.rotkomichal.battleship.views.gameView.board.ship.Ship;

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
