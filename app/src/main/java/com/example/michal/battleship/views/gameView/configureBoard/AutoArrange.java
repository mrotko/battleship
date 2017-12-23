package com.example.michal.battleship.views.gameView.configureBoard;

import com.example.michal.battleship.views.gameView.board.BoardView;
import com.example.michal.battleship.views.gameView.board.ship.Direction;
import com.example.michal.battleship.views.gameView.board.ship.Ship;
import com.example.michal.battleship.views.gameView.board.ship.ShipView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by michal on 17.12.17.
 */

public class AutoArrange implements IAutoArrange {

    private BoardView boardView;

    private Random rand;

    private List<Integer> availableFields = new ArrayList<>();

    private ConfigureBoardChooseShip configureBoardChooseShip;

    private int antiHangingCounter;

    public AutoArrange() {
        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public void setBoard(BoardView boardView) {
        this.boardView = boardView;
        configure();
    }

    @Override
    public void setConfigureBoardChooseShip(ConfigureBoardChooseShip configureBoardChooseShip) {
        this.configureBoardChooseShip = configureBoardChooseShip;
    }

    private void configure() {
        availableFields = IntStream
                .range(0, boardView.getColumnLength() * boardView.getRowLength())
                .boxed()
                .collect(Collectors.toList());
        antiHangingCounter = 0;
    }

    @Override
    public void start() {
        if(!configureBoardChooseShip.empty()) {
            Direction direction = drawDirection();
            Ship ship = drawShip();
            ship.setDirection(direction);
            int position = drawPosition();

            int rowId = position / 10;
            int fieldId = position % 10;

            DragShipDTO dragShipDTO = new DragShipDTO();
            dragShipDTO.setShip(ship);
            dragShipDTO.setBoardRow(boardView.getBoard().getRows().get(rowId));
            dragShipDTO.setBoardField(boardView.getBoard().getRows().get(rowId).getFields().get(fieldId));

            boardView.notifyAboutDropCandidate(dragShipDTO);
            if(boardView.checkDropShipValidity()) {
                boardView.acceptDragShip();
                configureBoardChooseShip.getShips().remove(ship);
                availableFields.remove(position);
                antiHangingCounter = 0;
                start();
            } else if(antiHangingCounter > 1000) {
                configureBoardChooseShip.reset();
                boardView.reset();
                configure();
                start();
            }
            else {
                antiHangingCounter++;
                start();
            }
        }
    }

    private Direction drawDirection() {
        if(rand.nextBoolean()) {
            return Direction.VERTICAL;
        } else {
            return Direction.HORIZONTAL;
        }
    }

    private Ship drawShip() {
        return configureBoardChooseShip.getShips()
                .get(rand.nextInt(configureBoardChooseShip.getShips().size()));
    }

    private int drawPosition() {
        return rand.nextInt(availableFields.size());
    }
}
