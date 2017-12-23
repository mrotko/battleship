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

/**
 * Created by michal on 18.12.17.
 */

public class EndGameFragment extends Fragment {

    private GameActivity gameActivity;

    private Button endGameBtn;

    private Button revengeBtn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameActivity = (GameActivity) getActivity();
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

    }

    private void createRevengeBtn() {
        revengeBtn = getView().findViewById(R.id.btnRevenge);
        revengeBtn.setOnClickListener(view -> {
//            TODO komunikacja z serwerem i oczekiwanie aż z drugiej strony będzie potwierdzenie
            getFragmentManager().beginTransaction().remove(this).commit();
            gameActivity.setGameState(GameState.REVENGE);
            gameActivity.controller();
        });
    }
}
