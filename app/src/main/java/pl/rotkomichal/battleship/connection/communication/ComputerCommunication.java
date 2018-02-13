package pl.rotkomichal.battleship.connection.communication;

import pl.rotkomichal.battleship.model.MoveDTO;
import pl.rotkomichal.battleship.model.Player;
import pl.rotkomichal.battleship.model.User;
import pl.rotkomichal.battleship.utils.validator.DragCoordinatesCalculator;
import pl.rotkomichal.battleship.utils.validator.FieldsAroundBoardValidator;
import pl.rotkomichal.battleship.utils.validator.FieldsUnderBoardValidator;
import pl.rotkomichal.battleship.utils.validator.IValidator;
import pl.rotkomichal.battleship.views.gameView.board.Board;
import pl.rotkomichal.battleship.views.gameView.board.BoardField;
import pl.rotkomichal.battleship.views.gameView.board.BoardRow;
import pl.rotkomichal.battleship.views.gameView.board.ship.Direction;
import pl.rotkomichal.battleship.views.gameView.board.ship.Ship;
import pl.rotkomichal.battleship.views.gameView.board.ship.ShipFactory;
import pl.rotkomichal.battleship.views.gameView.configureBoard.DragShipDTO;

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

    protected Random random;

    protected List<Integer> availableFields = new ArrayList<>();

    protected ComputerCommunication() {
        init();
    }

    private void init() {
        initBoard();
        user = new User();
        opponent = new Player();
        opponent.setBoard(board);
        opponent.setUser(user);
        random = new Random(System.currentTimeMillis());
        availableFields = IntStream.range(0, opponent.getBoard().getRowLength() * opponent.getBoard().getColumnLength())
                .boxed()
                .collect(Collectors.toList());
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

    protected void removeFieldsAroundSunkedShip() {
        me.getBoard().getShips().stream()
                .filter(Ship::isSunken)
                .forEach(ship -> {
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
                            availableFields.remove(Integer.valueOf(rowId * 10 + fieldId));
                        }
                    }
                });
    }
}
