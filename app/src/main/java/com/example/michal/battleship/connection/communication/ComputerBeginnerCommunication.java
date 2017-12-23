package com.example.michal.battleship.connection.communication;

import com.example.michal.battleship.model.MoveDTO;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by michal on 18.12.17.
 */

public class ComputerBeginnerCommunication extends ComputerCommunication {

    Random random;

    private List<Integer> availableFields = new ArrayList<>();
    public ComputerBeginnerCommunication() {
        super();
        init();
    }

    private void init() {
        random = new Random(System.currentTimeMillis());
        availableFields = IntStream.range(0, opponent.getBoard().getRowLength() * opponent.getBoard().getColumnLength())
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    protected MoveDTO calculateMove() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer move = drawMove();
        availableFields.remove(move);

        int rowId = move / 10;
        int fieldId = move % 10;

        return new MoveDTO(rowId, fieldId);
    }

    private int drawMove() {
        return availableFields.get(random.nextInt(availableFields.size()));
    }
}
