package com.example.michal.battleship.views.gameView.endGame;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.michal.battleship.R;
import com.example.michal.battleship.model.Statistics;
import com.example.michal.battleship.views.gameView.GameActivity;
import com.example.michal.battleship.views.gameView.GameState;

import java.util.Optional;

/**
 * Created by michal on 18.12.17.
 */

public class EndGameFragment extends Fragment {

    private GameActivity gameController;

    private Button endGameBtn;

    private Button revengeBtn;

    private Statistics myStatistics;

    private Statistics opponentStatistics;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameController = (GameActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_end_game, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        createStatisticsTable();
        createEndGameBtn();
        createRevengeBtn();
        createResultTextView();
    }

    private void createResultTextView() {
        TextView result = getView().findViewById(R.id.tv_result);
        if(myStatistics.getHittedShipFields() > opponentStatistics.getHittedShipFields()) {
            result.setText(R.string.youWin);
            result.setTextColor(getResources().getColor(R.color.green, null));
        } else {
            result.setText(R.string.youLose);
            result.setTextColor(getResources().getColor(R.color.red, null));
        }
    }

    private void createStatisticsTable() {
        opponentStatistics = getArguments().getParcelable("opponentStatistics");
        myStatistics = getArguments().getParcelable("myStatistics");

        ((TextView) getView().findViewById(R.id.tv_meName)).setText(myStatistics.getPlayerName());
        ((TextView) getView().findViewById(R.id.tv_meShipFields)).setText(String.valueOf((myStatistics.getHittedShipFields())));
        ((TextView) getView().findViewById(R.id.tv_meWaterFields)).setText(String.valueOf(myStatistics.getHittedWaterFields()));
        ((TextView) getView().findViewById(R.id.tv_mePercent)).setText(String.valueOf(myStatistics.getShotPercentage()));
        ((TextView) getView().findViewById(R.id.tv_mePoints)).setText(String.valueOf(myStatistics.getPoints()));

        ((TextView) getView().findViewById(R.id.tv_opponentName)).setText(opponentStatistics.getPlayerName());
        ((TextView) getView().findViewById(R.id.tv_opponentShipFields)).setText(String.valueOf(opponentStatistics.getHittedShipFields()));
        ((TextView) getView().findViewById(R.id.tv_opponentWaterFields)).setText(String.valueOf(opponentStatistics.getHittedWaterFields()));
        ((TextView) getView().findViewById(R.id.tv_opponentPercent)).setText(String.valueOf(opponentStatistics.getShotPercentage()));
        ((TextView) getView().findViewById(R.id.tv_opponentPoints)).setText(String.valueOf(opponentStatistics.getPoints()));
    }

    private void createEndGameBtn() {
        Optional.ofNullable(getView()).ifPresent(view -> {
            endGameBtn = view.findViewById(R.id.btnEndGame);
            endGameBtn.setOnClickListener(v -> {
                gameController.getCommunication().sendRevenge(false);
                getFragmentManager().beginTransaction().remove(this).commit();
                gameController.setGameState(GameState.EXIT);
                gameController.doThings();
            });
        });
    }

    private void createRevengeBtn() {
        Optional.ofNullable(getView()).ifPresent(view -> {
            revengeBtn = view.findViewById(R.id.btnRevenge);
            revengeBtn.setOnClickListener(v -> {
                gameController.getCommunication().sendRevenge(true);
                getFragmentManager().beginTransaction().remove(this).commit();
                gameController.setGameState(GameState.REVENGE);
                gameController.doThings();
            });
        });
    }
}
