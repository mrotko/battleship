package com.example.michal.battleship.views.gameView.game;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.michal.battleship.R;
import com.example.michal.battleship.model.MoveDTO;
import com.example.michal.battleship.views.gameView.GameActivity;
import com.example.michal.battleship.views.gameView.GameState;
import com.example.michal.battleship.views.gameView.board.Board;
import com.example.michal.battleship.views.gameView.board.BoardView;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.ShipFieldType;
import com.example.michal.battleship.views.gameView.board.fieldType.WaterFieldType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by michal on 18.12.17.
 */

public class GameFragment extends Fragment {

    private GameActivity gameActivity;

    private FrameLayout meFl;

    private FrameLayout opponentFl;

    private BoardView myBoardView;

    private BoardView opponentBoardView;

    private List<AsyncTask<Void, Void, Void>> workingAsyncTasks = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameActivity = (GameActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        createOpponentBoard();
        createMyBoard();
//     TODO   serwer losuje kto pierwszy zaczyna
        setFirstMove(true);
    }

    private void createMyBoard() {
        meFl = getView().findViewById(R.id.flMe);
        myBoardView = createPreparedMyBoardView();
        meFl.addView(myBoardView);
    }

    private BoardView createPreparedMyBoardView() {
        Board board = gameActivity.getMe().getBoard();
        board.setFieldStatusToAll(FieldStatus.VISIBLE);
        board.setActiveToAll(false);
        board.setFieldStatusListener(event -> {
            if (event.getNewValue().equals(FieldStatus.HIT)) {
                if (event.getPropertyName().equals(WaterFieldType.class.getSimpleName())) {
                    prepareForMyMove();
                } else if (event.getPropertyName().equals(ShipFieldType.class.getSimpleName())) {
                    myShipHit();
                }
            }
        });
        return new BoardView(getContext(), board);
    }

    private void opponentShipHit() {
        if(opponentBoardView.getBoard().isEndGame()) {
            endGame();
        }
    }

    private void endGame() {
        waitUntilTasksEnd();
        gameActivity.setGameState(GameState.END_GAME);
        getFragmentManager().beginTransaction().remove(this).commit();
        gameActivity.doThings();
    }

    public void waitUntilTasksEnd() {
        while (workingAsyncTasks.size() > 0) {
            workingAsyncTasks.get(0).cancel(true);
        }
    }

    private void createOpponentBoard() {
        opponentFl = getView().findViewById(R.id.flOpponent);
        opponentBoardView = createPreparedOpponentBoardView();
        opponentBoardView.getChildRowViews().forEach(boardRowView -> {
            boardRowView.getChildBoardFieldViews().forEach(boardFieldView -> {
                int rowId = boardRowView.getBoardRow().getId();
                int fieldId = boardFieldView.getBoardField().getId();

                boardFieldView.setOnClickListener(view -> {
                    if(boardFieldView.clickPermission()) {
                        sendMove(rowId, fieldId);
                        boardFieldView.hit();
                    }
                });
            });
        });
        opponentFl.addView(opponentBoardView);
    }

    private BoardView createPreparedOpponentBoardView() {
        Board board = new Board(gameActivity.getOpponent().getBoard().getShips());
        board.setFieldStatusToAll(FieldStatus.HIDDEN);
        board.setActiveToAll(true);
        board.setFieldStatusListener(event -> {
            if (event.getNewValue().equals(FieldStatus.HIT)) {
                if (event.getPropertyName().equals(WaterFieldType.class.getSimpleName())) {
                    prepareForOpponentMove();
                    new RetrieveMoveAsyncTask(this).execute();
                } else if (event.getPropertyName().equals(ShipFieldType.class.getSimpleName())) {
                    opponentShipHit();
                }
            }
        });
        return new BoardView(getContext(), board);
    }

    private void myShipHit() {
        if(gameActivity.getMe().getBoard().isEndGame()) {
            endGame();
        } else {
            new RetrieveMoveAsyncTask(this).execute();
        }
    }

    private void sendMove(int rowId, int fieldId) {
        MoveDTO moveDTO = new MoveDTO(rowId, fieldId);
        gameActivity.getCommunication().sendMove(moveDTO);
    }

    private void prepareForOpponentMove() {
        opponentBoardView.setBackgroundColor(getResources().getColor(R.color.boardViewBackgroundNormal, null));
        myBoardView.setBackgroundColor(getResources().getColor(R.color.boardViewBackgroundMove, null));
        opponentBoardView.getBoard().setActiveToAll(false);
    }

    private void prepareForMyMove() {
        opponentBoardView.setBackgroundColor(getResources().getColor(R.color.boardViewBackgroundMove, null));
        myBoardView.setBackgroundColor(getResources().getColor(R.color.boardViewBackgroundNormal, null));
        opponentBoardView.getBoard().setActiveToAll(true);
    }

    public void setFirstMove(boolean myMove) {
       if(myMove) {
           prepareForMyMove();
       } else {
           prepareForOpponentMove();
       }
    }

    private static class RetrieveMoveAsyncTask extends AsyncTask<Void, Void, Void> {

        private int rowId;

        private int fieldId;

        private WeakReference<GameFragment> gameFragment;

        RetrieveMoveAsyncTask(GameFragment gameFragment) {
            this.gameFragment = new WeakReference<>(gameFragment);
        }

        @Override
        protected void onPreExecute() {
            gameFragment.get().workingAsyncTasks.add(this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MoveDTO moveDTO = gameFragment.get().gameActivity.getCommunication().retrieveMove();
            rowId = moveDTO.getRowId();
            fieldId = moveDTO.getFieldId();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            gameFragment.get().myBoardView.getBoard().getRows().get(rowId).getFields().get(fieldId).hit();
            gameFragment.get().myBoardView.redraw();
            gameFragment.get().workingAsyncTasks.remove(this);
        }
    }
}
