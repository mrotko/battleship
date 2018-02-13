package pl.rotkomichal.battleship.utils.validator;

import pl.rotkomichal.battleship.views.gameView.board.Board;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.WaterFieldType;
import pl.rotkomichal.battleship.views.gameView.configureBoard.DragShipDTO;

/**
 * Created by michal on 19.12.17.
 */

public class FieldsUnderBoardValidator implements IValidator {

    private Board board;

    private DragShipDTO dragShipDTO;

    private DragCoordinatesCalculator coordinates;

    public FieldsUnderBoardValidator(Board board, DragShipDTO dragShipDTO) {
        this.board = board;
        this.dragShipDTO = dragShipDTO;
        coordinates = new DragCoordinatesCalculator(dragShipDTO);
    }
    @Override
    public boolean isValid() {
        int currentFieldId = coordinates.getShipHeadFieldId();
        int currentRowId = coordinates.getShipHeadRowId();

        for(int i = 0; i < dragShipDTO.getShip().getSize(); i++) {
            if(currentFieldId < 0 || currentFieldId >= board.getRowLength()) {
                return false;
            }
            if(currentRowId < 0 || currentRowId >= board.getColumnLength()) {
                return false;
            }

            if (!(board.getRows().get(currentRowId).getFields().get(currentFieldId).getFieldType() instanceof WaterFieldType)) {
                return false;
            }

            currentFieldId += coordinates.getHorizontalIncrease();
            currentRowId += coordinates.getVerticalIncrease();
        }

        return true;
    }
}
