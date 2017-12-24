package com.example.michal.battleship.views.gameView.chooseGameType;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.michal.battleship.R;
import com.example.michal.battleship.connection.communication.ICommunication;
import com.example.michal.battleship.views.gameView.chooseGameType.view.ExpandedWidget;
import com.example.michal.battleship.views.gameView.GameActivity;
import com.example.michal.battleship.views.gameView.GameState;
import com.example.michal.battleship.views.gameView.chooseGameType.view.BluetoothTypeOptionView;
import com.example.michal.battleship.views.gameView.chooseGameType.view.ComputerTypeOptionView;
import com.example.michal.battleship.views.gameView.chooseGameType.view.WifiTypeOptionView;

public class ChooseGameTypeFragment extends Fragment {

    private GameActivity gameActivity;

    private ExpandedWidget playWithComputerEw;

    private ExpandedWidget playWifiEw;
    
    private ExpandedWidget playBluetoothEw;

    private LinearLayout chooseGameTypeLL;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameActivity = (GameActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_choose_game_type, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        chooseGameTypeLL = getView().findViewById(R.id.ll_choose_game_type);
        createViews();
    }

    private void createViews() {
        createPlayWithComputerExpandedWidget();
        createPlayWifiExpandedWidget();
        createPlayBluetoothExpandedWidget();
    }

    private void createPlayWithComputerExpandedWidget() {
        playWithComputerEw = new ExpandedWidget(getContext());
        ComputerTypeOptionView optionView = new ComputerTypeOptionView(getContext());
        optionView.setButtonOnClickAction((view -> {
            gameActivity.setGameState(GameState.GAME_TYPE_CHOSEN);
            gameActivity.setCommunication(createCommunication(optionView.getGameTypeOption()));
            getFragmentManager().beginTransaction().remove(this).commit();
            gameActivity.doThings();

        }));
        playWithComputerEw.setContent(optionView);
        playWithComputerEw.setHeader(getResources().getString(R.string.playWithComputer));
        chooseGameTypeLL.addView(playWithComputerEw);
        playWithComputerEw.addOnClickListenerAction(view -> {
            playWifiEw.collapse();
            playBluetoothEw.collapse();
        });
    }

    private ICommunication createCommunication(GameTypeOption gameTypeOption) {
//        TODO rzucanie wyjątku jeżeli nie udało się połączć
        try {
            return gameActivity.getConnectionService()
                    .getCommunication(gameTypeOption);
        } catch (Exception ignored) {

        }
        return null;
    }


    private void createPlayWifiExpandedWidget() {
        playWifiEw = new ExpandedWidget(getContext());
        playWifiEw.setHeader(getResources().getString(R.string.playViaWifi));
        WifiTypeOptionView optionView = new WifiTypeOptionView(getContext());
        playWifiEw.setContent(optionView);
        chooseGameTypeLL.addView(playWifiEw);
        playWifiEw.addOnClickListenerAction(view -> {
            playBluetoothEw.collapse();
            playWithComputerEw.collapse();
        });
    }

    private void createPlayBluetoothExpandedWidget() {
        playBluetoothEw = new ExpandedWidget(getContext());
        playBluetoothEw.setHeader(getResources().getString(R.string.playViaBluetooth));
        BluetoothTypeOptionView optionView = new BluetoothTypeOptionView(getContext());
        playBluetoothEw.setContent(optionView);
        chooseGameTypeLL.addView(playBluetoothEw);
        playBluetoothEw.addOnClickListenerAction(view -> {
            playWifiEw.collapse();
            playWithComputerEw.collapse();
        });
    }
}
