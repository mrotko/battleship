package com.example.michal.battleship.connection.communication;

import com.example.michal.battleship.model.MoveDTO;

import java.util.concurrent.TimeUnit;

/**
 * Created by michal on 18.12.17.
 */

public class ComputerBeginnerCommunication extends ComputerCommunication {

    @Override
    protected MoveDTO calculateMove() {
        removeFieldsAroundSunkedShip();

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
