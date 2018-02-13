package pl.rotkomichal.battleship.utils.validator;

import pl.rotkomichal.battleship.views.gameView.board.Board;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.WaterFieldType;
import pl.rotkomichal.battleship.views.gameView.board.ship.Direction;
import pl.rotkomichal.battleship.views.gameView.configureBoard.DragShipDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by michal on 19.12.17.
 */

public class FieldsAroundBoardValidator implements IValidator {

    private final DragShipDTO dragShipDTO;

    private final Board board;

    private final DragCoordinatesCalculator coordinates;

    private List<Integer> rowIdRange;

    private List<Integer> fieldIdRange;

    public FieldsAroundBoardValidator(Board board, DragShipDTO dragShipDTO) {
        this.board = board;
        this.dragShipDTO = dragShipDTO;
        coordinates = new DragCoordinatesCalculator(dragShipDTO);
    }

    @Override
    public boolean isValid() {
        if(dragShipDTO.getShip().getDirection() == Direction.VERTICAL) {
            buildVerticalData();
        } else {
            buildHorizontalData();
        }

        for(Integer rowId : rowIdRange) {
            for(Integer fieldId : fieldIdRange) {
                if (!(board.getRows().get(rowId).getFields().get(fieldId).getFieldType() instanceof WaterFieldType)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void buildVerticalData() {
        rowIdRange = IntStream.range(coordinates.getShipHeadRowId() - 1, coordinates.getShipHeadRowId() + dragShipDTO.getShip().getSize() + 1)
                .filter(i -> i >= 0)
                .filter(i -> i < board.getColumnLength())
                .boxed()
                .collect(Collectors.toList());
        fieldIdRange = IntStream.rangeClosed(coordinates.getShipHeadFieldId() - 1, coordinates.getShipHeadFieldId() + 1)
                .filter(i -> i >= 0)
                .filter(i -> i < board.getRowLength())
                .boxed()
                .collect(Collectors.toList());
    }

    private void buildHorizontalData() {
        rowIdRange = IntStream.rangeClosed(coordinates.getShipHeadRowId() - 1, coordinates.getShipHeadRowId() + 1)
                .filter(i -> i >= 0)
                .filter(i -> i < board.getColumnLength())
                .boxed()
                .collect(Collectors.toList());
        fieldIdRange = IntStream.range(coordinates.getShipHeadFieldId() - 1, coordinates.getShipHeadFieldId() + dragShipDTO.getShip().getSize() + 1)
                .filter(i -> i >= 0)
                .filter(i -> i < board.getRowLength())
                .boxed()
                .collect(Collectors.toList());
    }
}
