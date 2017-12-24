package com.example.michal.battleship.views.gameView.endGame;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.michal.battleship.R;
import com.example.michal.battleship.views.gameView.GameActivity;
import com.example.michal.battleship.views.gameView.GameState;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by michal on 18.12.17.
 */

public class EndGameFragment extends Fragment {

    private GameActivity gameController;

    private Button endGameBtn;

    private Button revengeBtn;

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
        createEndGameBtn();
        createRevengeBtn();
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
