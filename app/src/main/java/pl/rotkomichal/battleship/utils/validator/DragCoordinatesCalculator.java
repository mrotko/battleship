package pl.rotkomichal.battleship.utils.validator;

import pl.rotkomichal.battleship.views.gameView.board.ship.Direction;
import pl.rotkomichal.battleship.views.gameView.configureBoard.DragShipDTO;

/**
 * Created by michal on 19.12.17.
 */

public class DragCoordinatesCalculator {

    private int shipHeadFieldId;

    private int shipHeadRowId;

    private int verticalIncrease;

    private int horizontalIncrease;

    public DragCoordinatesCalculator(DragShipDTO dragShipDTO) {
        init(dragShipDTO);
    }

    private void init(DragShipDTO dragShipDTO) {
        if(dragShipDTO.getShip().getDirection() == Direction.VERTICAL) {
            shipHeadFieldId = dragShipDTO.getBoardField().getId();
            shipHeadRowId = dragShipDTO.getBoardRow().getId() - (dragShipDTO.getShip().getSize() / 2);

            verticalIncrease = 1;
            horizontalIncrease = 0;
        } else {
            shipHeadFieldId = dragShipDTO.getBoardField().getId() - (dragShipDTO.getShip().getSize() / 2);
            shipHeadRowId = dragShipDTO.getBoardRow().getId();
            verticalIncrease = 0;
            horizontalIncrease = 1;
        }
    }

    public int getShipHeadFieldId() {
        return shipHeadFieldId;
    }

    public int getShipHeadRowId() {
        return shipHeadRowId;
    }

    public int getVerticalIncrease() {
        return verticalIncrease;
    }

    public int getHorizontalIncrease() {
        return horizontalIncrease;
    }
}
