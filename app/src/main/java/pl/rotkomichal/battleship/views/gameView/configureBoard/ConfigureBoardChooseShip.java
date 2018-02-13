package pl.rotkomichal.battleship.views.gameView.configureBoard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.DragEvent;
import android.widget.LinearLayout;

import pl.rotkomichal.battleship.R;
import pl.rotkomichal.battleship.views.gameView.board.BoardView;
import pl.rotkomichal.battleship.views.gameView.board.ship.Ship;
import pl.rotkomichal.battleship.views.gameView.board.ship.ShipFactory;
import pl.rotkomichal.battleship.views.gameView.board.ship.ShipView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 15.12.17.
 */

@SuppressLint("ViewConstructor")
public class ConfigureBoardChooseShip extends LinearLayout {

    private LinearLayout chooseShipLL;

    private List<Ship> ships = new ArrayList<>();

    private BoardView boardView;

    public ConfigureBoardChooseShip(Context context, BoardView boardView) {
        super(context);
        this.boardView = boardView;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_choose_ship, this);
        chooseShipLL = findViewById(R.id.llChooseShip);
        chooseShipLL.setBackground(getResources().getDrawable(R.drawable.ic_launcher_background, null));
        createShips();
        redraw();
        createDragListener();
        createBoardDragListener();
    }

    private void createBoardDragListener() {
        boardView.setOnDragListener(((view, dragEvent) -> {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_ENDED:
                    if(dragEvent.getResult()) {
                        boardView.acceptDragShip();
                    }
            }
            return true;
        }));
    }



    private void createDragListener() {
        this.setOnDragListener((view, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENDED:
                    if(event.getResult()) {
                        Ship onDragView = (Ship) event.getLocalState();
                        ships.remove(onDragView);
                        redraw();
                        break;
                    }
                case DragEvent.ACTION_DROP:
                    return false;
            }
            return true;
        });
    }

    private void createShips() {
        ships.add(ShipFactory.getShip(1));
        ships.add(ShipFactory.getShip(1));
        ships.add(ShipFactory.getShip(1));
        ships.add(ShipFactory.getShip(1));

        ships.add(ShipFactory.getShip(2));
        ships.add(ShipFactory.getShip(2));
        ships.add(ShipFactory.getShip(2));

        ships.add(ShipFactory.getShip(3));
        ships.add(ShipFactory.getShip(3));

        ships.add(ShipFactory.getShip(4));
    }

    public void redraw() {
        chooseShipLL.removeAllViews();
        ships.stream()
                .map(Ship::getSize)
                .distinct()
                .sorted()
                .forEach(size -> ships.stream()
                        .filter(ship -> ship.getSize() == size)
                        .findFirst()
                        .ifPresent(ship -> {
                            ShipView shipView = new ShipView(getContext(), ship);
                            shipView.redraw();
                            chooseShipLL.addView(shipView);
                        }));
    }

    public void reset() {
        ships.clear();
        createShips();
        redraw();
    }

    public List<Ship> getShips() {
        return ships;
    }

    public boolean empty() {
        return ships.size() == 0;
    }
}
