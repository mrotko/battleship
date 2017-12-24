package com.example.michal.battleship.views.gameView.board;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.widget.LinearLayout;

import com.example.michal.battleship.R;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.ship.Direction;
import com.example.michal.battleship.views.gameView.board.ship.Ship;
import com.example.michal.battleship.views.gameView.configureBoard.DragShipDTO;
import com.example.michal.battleship.utils.validator.DragCoordinatesCalculator;
import com.example.michal.battleship.utils.validator.FieldsAroundBoardValidator;
import com.example.michal.battleship.utils.validator.FieldsUnderBoardValidator;
import com.example.michal.battleship.utils.validator.IValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SuppressLint("ViewConstructor")
public class BoardView extends LinearLayout {

    private ShipDragManager shipDragManager;

    private Board board;

    private LinearLayout boardLL;

    public BoardView(Context context, Board board) {
        super(context);
        this.board = board;
        shipDragManager = new ShipDragManager();
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_board, this);
        boardLL = findViewById(R.id.llBoard);
        redraw();
        createShipSunkedListener();
    }

    public void redraw() {
        boardLL.removeAllViews();
        board.getRows().forEach(boardRow -> {
            BoardRowView boardRowView = new BoardRowView(getContext(), boardRow);
            boardRowView.redraw();
            boardLL.addView(boardRowView);
        });

        createBoardFieldsDragListener();
    }

    private void createShipSunkedListener() {
        board.getShips().forEach(ship -> {
            ship.getFields().forEach(boardField -> {
                boardField.getFieldType().addPropertyChangeListener(event -> {
                    if(event.getNewValue().equals(FieldStatus.HIT) &&
                            ship.isSunken()) {
                        showFieldsAroundSunkedShip(ship);
                    }
                });
            });
        });
    }

    private void showFieldsAroundSunkedShip(Ship ship) {
        List<Integer> rowIdRange = new ArrayList<>();
        List<Integer> fieldIdRange = new ArrayList<>();
        BoardField shipHeadBoardField = ship.getFields().get(0);

        if(ship.getDirection().equals(Direction.VERTICAL)) {
            rowIdRange = IntStream.rangeClosed(shipHeadBoardField.getRowId() - 1, shipHeadBoardField.getRowId() + ship.getSize())
                    .filter(id -> id >= 0)
                    .filter(id -> id < board.getColumnLength())
                    .boxed()
                    .collect(Collectors.toList());
            fieldIdRange = IntStream.rangeClosed(shipHeadBoardField.getId() - 1, shipHeadBoardField.getId() + 1)
                    .filter(id -> id >= 0)
                    .filter(id -> id < board.getRowLength())
                    .boxed()
                    .collect(Collectors.toList());
        } else if(ship.getDirection().equals(Direction.HORIZONTAL)) {
            rowIdRange = IntStream.rangeClosed(shipHeadBoardField.getRowId() - 1, shipHeadBoardField.getRowId() + 1)
                    .filter(id -> id >= 0)
                    .filter(id -> id < board.getColumnLength())
                    .boxed()
                    .collect(Collectors.toList());
            fieldIdRange = IntStream.rangeClosed(shipHeadBoardField.getId() - 1, shipHeadBoardField.getId() + ship.getSize())
                    .filter(id -> id >= 0)
                    .filter(id -> id < board.getRowLength())
                    .boxed()
                    .collect(Collectors.toList());
        }

        for (Integer rowId : rowIdRange) {
            for(Integer fieldId : fieldIdRange) {
                BoardFieldView boardFieldView = getChildRowViews().get(rowId)
                        .getChildBoardFieldViews().get(fieldId);
                if(boardFieldView.getFieldType().getFieldStatus().equals(FieldStatus.HIDDEN)) {
                    boardFieldView.setFieldStatus(FieldStatus.VISIBLE);
                }
            }
        }
    }


    public int getColumnLength() {
        return board.getColumnLength();
    }

    public int getRowLength() {
        return board.getRowLength();
    }

    public void reset() {
        board.getShips().clear();
        board.getRows().clear();
        board.createTable();
        redraw();
    }

    public Board getBoard() {
        return board;
    }

    public List<BoardRowView> getChildRowViews() {
        List<BoardRowView> boardRowViews = new ArrayList<>();
        IntStream
                .range(0, boardLL.getChildCount())
                .forEach(id -> boardRowViews.add((BoardRowView) boardLL.getChildAt(id)));
        return boardRowViews;
    }

    public boolean checkDropShipValidity() {
        return  !shipDragManager.isDragNullable() &&
                shipDragManager.checkFieldsValidity();
    }

    public void acceptDragShip() {
        board.getShips().add(shipDragManager.getDragShipDTO().getShip());
        shipDragManager.applyDropShip();
    }

    private void createBoardFieldsDragListener() {
        getChildRowViews().forEach(boardRowView -> boardRowView.getChildBoardFieldViews().forEach(boardFieldView -> {
            boardFieldView.setOnDragListener(createFieldOnDragListener(boardRowView.getBoardRow(), boardFieldView.getBoardField()));
        }));
    }

    private OnDragListener createFieldOnDragListener(BoardRow boardRow, BoardField boardField) {
        return (view, dragEvent) -> {
            DragShipDTO dragShipDTO = new DragShipDTO();
            dragShipDTO.setBoardField(boardField);
            dragShipDTO.setBoardRow(boardRow);
            dragShipDTO.setShip((Ship) dragEvent.getLocalState());
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DROP:
                    notifyAboutDropCandidate(dragShipDTO);
                    clearFieldsBackground();
                    return checkDropShipValidity();
                case DragEvent.ACTION_DRAG_LOCATION:
                    notifyAboutDropCandidate(dragShipDTO);
                    shipDragManager.fillBackgroundUnderShip();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    clearFieldsBackground();
                    break;
            }
            return true;
        };
    }

    public void notifyAboutDropCandidate(DragShipDTO dragShipDTO) {
        shipDragManager.setDragShipDTO(dragShipDTO);
        shipDragManager.definePosition();
    }

    private void clearFieldsBackground() {
        getChildRowViews().forEach(boardRowView -> boardRowView.getChildBoardFieldViews().forEach(boardFieldView -> {
            boardFieldView.setBackground(getResources().getDrawable(R.color.fieldBackgroundNormal, null));
        }));
    }

    private class ShipDragManager {
        
        private DragCoordinatesCalculator coordinates;

        private DragShipDTO dragShipDTO;

        public void setDragShipDTO(DragShipDTO dragShipDTO) {
            this.dragShipDTO = dragShipDTO;
        }

        public DragShipDTO getDragShipDTO() {
            return dragShipDTO;
        }

        public boolean isDragNullable() {
            return  dragShipDTO == null ||
                    dragShipDTO.getBoardField() == null ||
                    dragShipDTO.getBoardRow() == null ||
                    dragShipDTO.getShip() == null;
        }

        public void applyDropShip() {
            int currentFieldId = coordinates.getShipHeadFieldId();
            int currentRowId = coordinates.getShipHeadRowId();

            for (BoardField field : dragShipDTO.getShip().getFields()) {
                field.setId(currentFieldId);
                field.setRowId(currentRowId);
                BoardRow currentRow = board.getRows().get(currentRowId);
                currentRow.getFields().set(currentFieldId, field);

                BoardRowView boardRowView = getChildRowViews().get(currentRowId);
                BoardFieldView boardFieldView = boardRowView.getChildBoardFieldViews()
                        .get(currentFieldId);
                boardFieldView.setBoardField(field);
                boardFieldView.redraw();

                currentRowId += coordinates.getVerticalIncrease();
                currentFieldId += coordinates.getHorizontalIncrease();
            }
        }

        public void definePosition() {
            coordinates = new DragCoordinatesCalculator(dragShipDTO);
        }

        public boolean checkFieldsValidity() {
            return  checkFieldsUnderShip() &&
                    checkFieldsAroundShip();
        }

        private boolean checkFieldsUnderShip() {
            IValidator validator = new FieldsUnderBoardValidator(board, dragShipDTO);
            return validator.isValid();
        }

        private boolean checkFieldsAroundShip() {
            IValidator validator = new FieldsAroundBoardValidator(board, dragShipDTO);
            return validator.isValid();
        }

        public void fillBackgroundUnderShip() {
            int currentFieldId = coordinates.getShipHeadFieldId();
            int currentRowId = coordinates.getShipHeadRowId();

            clearFieldsBackground();

            Drawable backgroundColor;

            if(checkFieldsValidity()) {
                backgroundColor = getResources().getDrawable(R.color.fieldBackgroundGood, null);
            } else {
                backgroundColor = getResources().getDrawable(R.color.fieldBackgroundError, null);
            }

            for(int i = 0; i < dragShipDTO.getShip().getSize(); i++) {
                if(currentFieldId >= 0 && currentFieldId < board.getRowLength() &&
                        currentRowId >= 0 && currentRowId < board.getColumnLength()) {
                    getChildRowViews().get(currentRowId)
                            .getChildBoardFieldViews().get(currentFieldId)
                            .setBackground(backgroundColor);
                }

                currentFieldId += coordinates.getHorizontalIncrease();
                currentRowId += coordinates.getVerticalIncrease();
            }
        }

    }
}
