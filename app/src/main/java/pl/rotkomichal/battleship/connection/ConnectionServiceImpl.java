package pl.rotkomichal.battleship.connection;

import pl.rotkomichal.battleship.connection.communication.BluetoothCommunication;
import pl.rotkomichal.battleship.connection.communication.ComputerBeginnerCommunication;
import pl.rotkomichal.battleship.connection.communication.ComputerNormalCommunication;
import pl.rotkomichal.battleship.connection.communication.ICommunication;
import pl.rotkomichal.battleship.connection.communication.WifiCommunication;
import pl.rotkomichal.battleship.views.gameView.chooseGameType.GameTypeOption;

/**
 * Created by michal on 18.12.17.
 */

public class ConnectionServiceImpl implements IConnectionService {

    public ConnectionServiceImpl() {
//        TODO w przyszłości jakieś szczegóły
    }

    @Override
    public ICommunication getCommunication(GameTypeOption gameTypeOption) {
        switch (gameTypeOption) {
            case WIFI:
                return new WifiCommunication();
            case BLUETOOTH:
                return new BluetoothCommunication();
            case COMPUTER_BEGINNER:
                return new ComputerBeginnerCommunication();
            case COMPUTER_NORMAL:
                return new ComputerNormalCommunication();
        }
        return null;
    }
}
