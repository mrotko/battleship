package pl.rotkomichal.battleship.connection.communication;

import pl.rotkomichal.battleship.model.MoveDTO;
import pl.rotkomichal.battleship.model.Player;

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
