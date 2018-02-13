package pl.rotkomichal.battleship.connection;

import pl.rotkomichal.battleship.connection.communication.ICommunication;
import pl.rotkomichal.battleship.views.gameView.chooseGameType.GameTypeOption;

/**
 * Created by michal on 18.12.17.
 */

public interface IConnectionService {
    ICommunication getCommunication(GameTypeOption gameTypeOption);
}
