package com.example.michal.battleship.connection.communication;

import com.example.michal.battleship.model.MoveDTO;
import com.example.michal.battleship.model.SimpleObject;
import com.example.michal.battleship.views.gameView.board.BoardField;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.ShipFieldType;
import com.example.michal.battleship.views.gameView.board.ship.Ship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by michal on 18.12.17.
 */

public class ComputerNormalCommunication extends ComputerCommunication {

    private List<Integer> predictedFields = new ArrayList<>();

    private Predicate<Integer> horizontalRange = (value) -> true;

    private Predicate<Integer> vertivalRange = (value) -> true;

    private List<MoveDTO> moves = new ArrayList<>();

    private int lastAvailableShipsSize = -1;

    private boolean onFire = false;



    @Override
    protected MoveDTO calculateMove() {
        removeFieldsAroundSunkedShip();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!onFire && !moves.isEmpty() && checkHit(moves.get(moves.size() - 1))) {
            onFire = true;
        }

        if(lastAvailableShipsSize > getAvailableShipsSize()) {
            moves.clear();
            onFire = false;
        } else if(onFire && !moves.isEmpty() && !checkHit(moves.get(moves.size() - 1))) {
            moves.remove(moves.size() - 1);
        }

        Integer movePosition = drawMove();
        availableFields.remove(movePosition);

        int rowId = movePosition / 10;
        int fieldId = movePosition % 10;

        MoveDTO move = new MoveDTO(rowId, fieldId);
        moves.add(move);

        lastAvailableShipsSize = getAvailableShipsSize();

        return move;
    }

    private Integer drawMove() {
        predictFields();
        predictedFields = availableFields.stream()
                .filter(fieldId -> horizontalRange.test(fieldId) || vertivalRange.test(fieldId))
                .collect(Collectors.toList());

        if(predictedFields.isEmpty() || !onFire) {
            predictedFields = availableFields;
        }

        return predictedFields.get(random.nextInt(predictedFields.size()));
    }

    private void predictFields() {
        List<MoveDTO> lastHittedMoves = new ArrayList<>();

        int index = moves.size() - 1;

        while (index >= 0) {
            if(checkHit(moves.get(index))) {
                lastHittedMoves.add(moves.get(index));
            } else {
                break;
            }
            index--;
        }

        if(!lastHittedMoves.isEmpty()) {
            int shipMaxSize = me.getBoard().getShips().stream()
                    .filter(ship -> !ship.isSunken())
                    .mapToInt(Ship::getSize)
                    .max()
                    .orElse(0);

            if(lastHittedMoves.size() == 1) {
                vertivalRange = (value) -> {
                    int hittedRowId = lastHittedMoves.get(0).getRowId();
                    int hittedFieldId = lastHittedMoves.get(0).getFieldId();
                    int moveFieldId = value % 10;
                    int moveRowId = value / 10;

                    return (moveFieldId == hittedFieldId) && (hittedRowId - 1 <= moveRowId && hittedRowId + 1 >= moveRowId);
                };

                horizontalRange = (value) -> {
                    int hittedRowId = lastHittedMoves.get(0).getRowId();
                    int hittedFieldId = lastHittedMoves.get(0).getFieldId();
                    int moveFieldId = value % 10;
                    int moveRowId = value / 10;

                    return (moveRowId == hittedRowId) && (hittedFieldId - 1 <= moveFieldId && hittedFieldId + 1 >= moveFieldId);
                };
            } else {
                if(lastHittedMoves.get(0).getFieldId() == lastHittedMoves.get(1).getFieldId()) {
                    int minHittedRowId = lastHittedMoves.stream()
                            .mapToInt(MoveDTO::getRowId)
                            .min()
                            .orElse(0);

                    int maxHittedRowId = lastHittedMoves.stream()
                            .mapToInt(MoveDTO::getRowId)
                            .max()
                            .orElse(0);


                    int hittedFieldId = lastHittedMoves.get(0).getFieldId();

                    horizontalRange = (value) -> false;

                    vertivalRange = (value) -> {
                        int moveFieldId = value % 10;
                        int moveRowId = value / 10;
                        return (moveFieldId == hittedFieldId) && (moveRowId >= minHittedRowId - 1 && moveRowId <= maxHittedRowId + 1);
                    };

                } else {
                    int minHittedFieldId = lastHittedMoves.stream()
                            .mapToInt(MoveDTO::getFieldId)
                            .min()
                            .orElse(0);

                    int maxHittedFieldId = lastHittedMoves.stream()
                            .mapToInt(MoveDTO::getFieldId)
                            .max()
                            .orElse(0);


                    int hittedRowId = lastHittedMoves.get(0).getRowId();

                    vertivalRange = (value) -> false;

                    horizontalRange = (value) -> {
                        int moveFieldId = value % 10;
                        int moveRowId = value / 10;
                        return (moveRowId == hittedRowId) && (moveFieldId >= minHittedFieldId - 1 && moveFieldId <= maxHittedFieldId + 1);
                    };
                }
            }

        } else {
            vertivalRange = (value) -> true;
            horizontalRange = (value) -> true;
        }
    }

    private boolean checkHit(MoveDTO move) {
        return me.getBoard()
                .getRows().get(move.getRowId())
                .getFields().get(move.getFieldId())
                .getFieldType() instanceof ShipFieldType;
    }

    private int getAvailableShipsSize() {
        return me.getBoard().getShips().stream()
                .filter(ship -> !ship.isSunken())
                .collect(Collectors.toList())
                .size();
    }
}
