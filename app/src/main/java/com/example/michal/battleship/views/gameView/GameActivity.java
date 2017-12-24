package com.example.michal.battleship.views.gameView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.michal.battleship.R;
import com.example.michal.battleship.connection.ConnectionServiceImpl;
import com.example.michal.battleship.connection.IConnectionService;
import com.example.michal.battleship.connection.communication.ICommunication;
import com.example.michal.battleship.model.Player;
import com.example.michal.battleship.model.User;
import com.example.michal.battleship.utils.SnackbarFactory;
import com.example.michal.battleship.views.MainActivity;
import com.example.michal.battleship.views.gameView.chooseGameType.ChooseGameTypeFragment;
import com.example.michal.battleship.views.gameView.chooseGameType.GameTypeOption;
import com.example.michal.battleship.views.gameView.configureBoard.ConfigureBoardFragment;
import com.example.michal.battleship.views.gameView.endGame.EndGameFragment;
import com.example.michal.battleship.views.gameView.game.GameFragment;

import java.util.concurrent.TimeUnit;

/**
 * Created by michal on 18.12.17.
 */

public class GameActivity extends AppCompatActivity {

    private GameState gameState = GameState.CHOOSE_GAME_TYPE;

    private LinearLayout gameActivityLL;

    private Player me;

    private Player opponent;

    private IConnectionService connectionService;

    private GameTypeOption gameTypeOption;

    private ICommunication communication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        doThings();
    }

    private void init() {
        this.connectionService = new ConnectionServiceImpl();
    }

    private void startChooseGameTypeFragment(){
        startFragment(new ChooseGameTypeFragment());
    }

    private void startFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.flGameActivity, fragment, fragment.getTag());
        transaction.commit();
    }

    private void startConfigureBoardFragment() {
        startFragment(new ConfigureBoardFragment());
    }

    private void startGameFragment() {
        startFragment(new GameFragment());
    }

    private void startEndGameFragment() {
        startFragment(new EndGameFragment());
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void doThings() {
        switch (gameState) {
            case CHOOSE_GAME_TYPE:
                startChooseGameTypeFragment();
                break;
            case GAME_TYPE_CHOSEN:
                me = createMe();
                communication.sendMe(me);
                opponent = communication.retrieveOpponent();
                setGameState(GameState.CONFIGURE_BOARD);
                doThings();
                break;
            case CONFIGURE_BOARD:
                startConfigureBoardFragment();
                break;
            case BOARD_CONFIGURED:
                setGameState(GameState.START_GAME);
                doThings();
                break;
            case START_GAME:
                startGameFragment();
                break;
            case END_GAME:
                startEndGameFragment();
                break;
            case REVENGE:
                if(getCommunication().retrieveRevenge()) {
                    setGameState(GameState.CONFIGURE_BOARD);
                } else {
                    SnackbarFactory.getInfo(gameActivityLL, getResources().getString(R.string.opponentLeftGame));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                doThings();
                break;
            case EXIT:
//                TODO pewnie potrzebne sprzątanie
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
    }

    public Player getMe() {
        return me;
    }

    public void setMe(Player me) {
        this.me = me;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public GameTypeOption getGameTypeOption() {
        return gameTypeOption;
    }

    public void setGameTypeOption(GameTypeOption gameTypeOption) {
        this.gameTypeOption = gameTypeOption;
    }

    public ICommunication getCommunication() {
        return communication;
    }

    public void setCommunication(ICommunication communication) {
        this.communication = communication;
    }

    public IConnectionService getConnectionService() {
        return connectionService;
    }

    public void setConnectionService(IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    private Player createMe() {
        Player me = new Player();
        User user = new User();
//        TODO dokończyć
//        User user = new User();
//        user.setId();
        me.setUser(user);
        return me;
    }
}
