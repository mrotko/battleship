package com.example.michal.battleship.views.gameView.board;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.michal.battleship.R;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldType;

import java.util.Optional;

@SuppressLint("ViewConstructor")
public class BoardFieldView extends FrameLayout {

    private ImageView fieldIv;

    private BoardField boardField;

    public BoardFieldView(Context context, BoardField boardField) {
        super(context);
        this.boardField = boardField;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_board_field, this);
        fieldIv = findViewById(R.id.iv_field);
    }

    public void hit() {
        Optional.ofNullable(boardField.getFieldType())
                .ifPresent(field -> {
                    field.hit();
                    draw(field.getDrawable());
                });
    }

    private void draw(Drawable drawable) {
        fieldIv.setImageDrawable(drawable);
    }

    public void redraw() {
        draw(boardField.getFieldType().getDrawable());
    }

    public void setFieldStatus(FieldStatus fieldStatus) {
        Optional.ofNullable(getFieldType())
                .ifPresent(field -> field.setFieldStatus(fieldStatus));
    }

    public FieldType getFieldType() {
        return boardField.getFieldType();
    }

    public BoardField getBoardField() {
        return boardField;
    }

    public void setBoardField(BoardField boardField) {
        this.boardField = boardField;
    }

    public boolean clickPermission() {
        return boardField.isActive() &&
                boardField.getFieldType().getFieldStatus().equals(FieldStatus.HIDDEN);
    }
}
