package com.example.michal.battleship.connection.communication;

import com.example.michal.battleship.model.MoveDTO;
import com.example.michal.battleship.model.Player;
import com.example.michal.battleship.model.User;
import com.example.michal.battleship.utils.validator.DragCoordinatesCalculator;
import com.example.michal.battleship.utils.validator.FieldsAroundBoardValidator;
import com.example.michal.battleship.utils.validator.FieldsUnderBoardValidator;
import com.example.michal.battleship.utils.validator.IValidator;
import com.example.michal.battleship.views.gameView.board.Board;
import com.example.michal.battleship.views.gameView.board.BoardField;
import com.example.michal.battleship.views.gameView.board.BoardFieldView;
import com.example.michal.battleship.views.gameView.board.BoardRow;
import com.example.michal.battleship.views.gameView.board.BoardRowView;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldStatus;
import com.example.michal.battleship.views.gameView.board.fieldType.FieldType;
import com.example.michal.battleship.views.gameView.board.ship.Direction;
import com.example.michal.battleship.views.gameView.board.ship.Ship;
import com.example.michal.battleship.views.gameView.board.ship.ShipFactory;
import com.example.michal.battleship.views.gameView.configureBoard.DragShipDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by michal on 18.12.17.
 */

public abstract class ComputerCommunication implements ICommunication {

    protected Board board;

    protected Player opponent;

    protected Player me;

    protected User user;

    protected MoveDTO currentMove;

    protected ComputerCommunication() {
        init();
    }

    private void init() {
        initBoard();
        user = new User();
        opponent = new Player();
        opponent.setBoard(board);
        opponent.setUser(user);
    }

    private void initBoard() {
        board = new Board();
        int antiHangingCounter = 0;
        List<Ship> ships = generateShips();
        List<Integer> availablePositions = IntStream
                .range(0, board.getRowLength() * board.getColumnLength())
                .boxed()
                .collect(Collectors.toList());

        Random random = new Random(System.currentTimeMillis());

        while(!ships.isEmpty()) {
            Integer position = availablePositions.get(random.nextInt(availablePositions.size()));

            int rowId = position / 10;
            int fieldId = position % 10;
            Ship ship = ships.get(0);
            ship.setDirection(random.nextBoolean() ? Direction.VERTICAL : Direction.HORIZONTAL);

            DragShipDTO dragShipDTO = new DragShipDTO();
            dragShipDTO.setShip(ship);
            dragShipDTO.setBoardRow(board.getRows().get(rowId));
            dragShipDTO.setBoardField(board.getRows().get(rowId).getFields().get(fieldId));

            IValidator underShipValidator = new FieldsUnderBoardValidator(board, dragShipDTO);
            IValidator aroundShpValidator = new FieldsAroundBoardValidator(board, dragShipDTO);

            if(underShipValidator.isValid() &&
                    aroundShpValidator.isValid()) {
                DragCoordinatesCalculator coordinates = new DragCoordinatesCalculator(dragShipDTO);
                int currentRowId = coordinates.getShipHeadRowId();
                int currentFieldId = coordinates.getShipHeadFieldId();
                for (BoardField field : dragShipDTO.getShip().getFields()) {
                    field.setId(currentFieldId);
                    field.setRowId(currentRowId);
                    BoardRow currentRow = board.getRows().get(currentRowId);
                    currentRow.getFields().set(currentFieldId, field);

                    currentRowId += coordinates.getVerticalIncrease();
                    currentFieldId += coordinates.getHorizontalIncrease();
                }

                availablePositions.remove(position);
                board.getShips().add(ship);
                ships.remove(ship);
                antiHangingCounter = 0;
            } else if(antiHangingCounter > 1000) {
                board.getShips().clear();
                initBoard();
            } else {
                antiHangingCounter++;
            }
        }
    }

    private List<Ship> generateShips() {
        List<Ship> ships = new ArrayList<>();

        ships.add(ShipFactory.getShip(4));

        ships.add(ShipFactory.getShip(3));
        ships.add(ShipFactory.getShip(3));

        ships.add(ShipFactory.getShip(2));
        ships.add(ShipFactory.getShip(2));
        ships.add(ShipFactory.getShip(2));

        ships.add(ShipFactory.getShip(1));
        ships.add(ShipFactory.getShip(1));
        ships.add(ShipFactory.getShip(1));
        ships.add(ShipFactory.getShip(1));

        return ships;
    }

    @Override
    public void sendMe(Player me) {
        this.me = me;
    }

    @Override
    public Player retrieveOpponent() {
        return opponent;
    }

    @Override
    public void sendMove(MoveDTO moveDTO) {
//        opponent.getBoard()
//                .getRows().get(moveDTO.getRowId())
//                .getFields().get(moveDTO.getFieldId())
//                .getFieldType()
//                .setFieldStatus(FieldStatus.HIT);
    }

    @Override
    public MoveDTO retrieveMove() {
        return calculateMove();
    }

    @Override
    public void sendRevenge(boolean revenge) {
        if(revenge) {
            init();
        }
    }

    @Override
    public boolean retrieveRevenge() {
        return true;
    }

    protected abstract MoveDTO calculateMove();
}
