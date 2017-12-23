package com.example.michal.battleship.views.gameView.board;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.widget.LinearLayout;

import com.example.michal.battleship.R;
import com.example.michal.battleship.views.gameView.board.ship.Ship;
import com.example.michal.battleship.views.gameView.board.ship.ShipView;
import com.example.michal.battleship.views.gameView.configureBoard.DragShipDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by michal on 15.12.17.
 */

public class BoardRowView extends LinearLayout {

    private LinearLayout boardRowLL;

    private BoardRow boardRow;

    public BoardRowView(Context context, BoardRow boardRow) {
        super(context);
        this.boardRow = boardRow;
        initialize();
    }

    public BoardRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initialize() {
        inflate(getContext(), R.layout.layout_board_row, this);
        boardRowLL = findViewById(R.id.llBoardRow);
    }

    public int getRowId() {
        return boardRow.getId();
    }

    public BoardField getField(int id) {
        return boardRow.getFields().stream()
                .filter(field -> field.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void redraw() {
        boardRowLL.removeAllViews();

        boardRow.getFields().forEach(field -> {
            BoardFieldView boardFieldView = new BoardFieldView(getContext(), field);
            boardFieldView.redraw();
            boardRowLL.addView(boardFieldView);
        });
    }


    public List<BoardField> getFields() {
        return boardRow.getFields();
    }

    public BoardRow getBoardRow() {
        return boardRow;
    }

    public List<BoardFieldView> getChildBoardFieldViews() {
        List<BoardFieldView> boardFieldViews = new ArrayList<>();
        IntStream.range(0, boardRowLL.getChildCount())
                .forEach(id -> boardFieldViews.add((BoardFieldView) boardRowLL.getChildAt(id)));
        return boardFieldViews;
    }
}
