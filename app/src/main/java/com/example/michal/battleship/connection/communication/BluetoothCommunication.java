package com.example.michal.battleship.connection.communication;

import com.example.michal.battleship.model.MoveDTO;
import com.example.michal.battleship.model.Player;

/**
 * Created by michal on 18.12.17.
 */

public class BluetoothCommunication implements ICommunication {
    @Override
    public void sendMe(Player me) {

    }

    @Override
    public Player retrieveOpponent() {
        return null;
    }


    @Override
    public void sendMove(MoveDTO moveDTO) {

    }

    @Override
    public MoveDTO retrieveMove() {
        return null;
    }

    @Override
    public void sendRevenge(boolean revenge) {

    }

    @Override
    public boolean retrieveRevenge() {
        return false;
    }
}
