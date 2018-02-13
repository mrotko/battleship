package pl.rotkomichal.battleship.connection.communication;

import pl.rotkomichal.battleship.model.MoveDTO;
import pl.rotkomichal.battleship.model.Player;

/**
 * Created by michal on 18.12.17.
 */

public class WifiCommunication implements ICommunication {
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
