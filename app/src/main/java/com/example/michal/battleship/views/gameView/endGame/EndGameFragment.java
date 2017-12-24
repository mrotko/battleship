package com.example.michal.battleship.views.gameView.endGame;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.michal.battleship.R;
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
//            TODO komunikacja z serwerem i oczekiwanie aż z drugiej strony będzie potwierdzenie
                getFragmentManager().beginTransaction().remove(this).commit();
                gameController.setGameState(GameState.REVENGE);
                gameController.doThings();
            });
        });
    }
}
