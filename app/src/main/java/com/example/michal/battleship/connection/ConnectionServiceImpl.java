package com.example.michal.battleship.connection;

import com.example.michal.battleship.connection.communication.BluetoothCommunication;
import com.example.michal.battleship.connection.communication.ComputerBeginnerCommunication;
import com.example.michal.battleship.connection.communication.ComputerNormalCommunication;
import com.example.michal.battleship.connection.communication.ICommunication;
import com.example.michal.battleship.connection.communication.WifiCommunication;
import com.example.michal.battleship.views.gameView.chooseGameType.GameTypeOption;

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
