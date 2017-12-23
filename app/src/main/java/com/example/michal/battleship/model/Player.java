package com.example.michal.battleship.model;

import com.example.michal.battleship.views.gameView.board.Board;

import java.io.Serializable;

/**
 * Created by michal on 15.12.17.
 */

public class Player extends SimpleObject {

    private Board board;

    private User user;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
