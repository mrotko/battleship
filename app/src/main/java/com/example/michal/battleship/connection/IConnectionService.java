package com.example.michal.battleship.connection;

import com.example.michal.battleship.connection.communication.ICommunication;
import com.example.michal.battleship.views.gameView.chooseGameType.GameTypeOption;

/**
 * Created by michal on 18.12.17.
 */

public interface IConnectionService {
    ICommunication getCommunication(GameTypeOption gameTypeOption);
}
