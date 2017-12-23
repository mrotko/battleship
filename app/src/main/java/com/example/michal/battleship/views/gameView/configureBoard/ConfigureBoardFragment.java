
package com.example.michal.battleship.views.gameView.configureBoard;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.michal.battleship.R;
import com.example.michal.battleship.utils.SnackbarFactory;
import com.example.michal.battleship.views.gameView.GameActivity;
import com.example.michal.battleship.views.gameView.GameState;
import com.example.michal.battleship.views.gameView.board.Board;
import com.example.michal.battleship.views.gameView.board.BoardView;

public class ConfigureBoardFragment extends Fragment {

    private GameActivity gameActivity;

    private LinearLayout configureBoardActivityLL;

    private BoardView boardView;

    private ConfigureBoardChooseShip configureBoardChooseShip;

    private ConfigureBoardButtonsGroup buttonsGroup;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameActivity = (GameActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_configure_board, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        configureBoardActivityLL = getView().findViewById(R.id.gl_configure_board);
        boardView = new BoardView(getContext(), new Board());
        configureBoardChooseShip = new ConfigureBoardChooseShip(getContext(), boardView);
        buttonsGroup = new ConfigureBoardButtonsGroup(getContext());
        configureBoardActivityLL.addView(boardView);
        configureBoardActivityLL.addView(configureBoardChooseShip);
        configureBoardActivityLL.addView(buttonsGroup);
        createAutoArrangeOnClickListener();
        createResetOnClickListener();
        createStartGameOnClickListener();
    }

    private void createAutoArrangeOnClickListener() {
        buttonsGroup.getAutoArrangeBtn().setOnClickListener(view -> {
            if(!configureBoardChooseShip.empty()) {
                IAutoArrange autoArrange = new AutoArrange();
                autoArrange.setBoard(boardView);
                autoArrange.setConfigureBoardChooseShip(configureBoardChooseShip);
                autoArrange.start();
                configureBoardChooseShip.redraw();
            } else {
                SnackbarFactory
                        .getWarning(view, getResources().getString(R.string.nothingToDo))
                        .show();
            }
        });
    }

    private void createResetOnClickListener() {
        buttonsGroup.getResetBoardBtn().setOnClickListener(view -> {
            boardView.reset();
            configureBoardChooseShip.reset();
        });
    }

    private void createStartGameOnClickListener() {
        buttonsGroup.getStartGameBtn().setOnClickListener(view -> {
            if(!configureBoardChooseShip.empty()) {
                SnackbarFactory
                        .getError(view, getResources().getString(R.string.putAllShipsOnBoard))
                        .show();
            } else {
                gameActivity.setGameState(GameState.BOARD_CONFIGURED);
                gameActivity.getMe().setBoard(boardView.getBoard());
                getFragmentManager().beginTransaction().remove(this).commit();
                gameActivity.controller();
            }
        });
    }
}
