package com.example.michal.battleship.connection.communication;

import com.example.michal.battleship.model.MoveDTO;
import com.example.michal.battleship.model.Player;

/**
 * Created by michal on 15.12.17.
 */

public interface ICommunication {
    void sendMe(Player me);
    Player retrieveOpponent();

    void sendMove(MoveDTO moveDTO);
    MoveDTO retrieveMove();

    void sendRevenge(boolean revenge);
    boolean retrieveRevenge();
}
