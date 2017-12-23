package com.example.michal.battleship.views.gameView.configureBoard;

import com.example.michal.battleship.views.gameView.board.BoardView;

/**
 * Created by michal on 17.12.17.
 */

public interface IAutoArrange {
    void setBoard(BoardView boardView);

    void setConfigureBoardChooseShip(ConfigureBoardChooseShip configureBoardChooseShip);

    void start();
}
