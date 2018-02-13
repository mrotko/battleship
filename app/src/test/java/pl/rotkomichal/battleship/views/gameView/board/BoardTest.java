package pl.rotkomichal.battleship.views.gameView.board;

import pl.rotkomichal.battleship.views.gameView.board.fieldType.FieldStatus;
import pl.rotkomichal.battleship.views.gameView.board.ship.Direction;
import pl.rotkomichal.battleship.views.gameView.board.ship.Ship;
import pl.rotkomichal.battleship.views.gameView.board.ship.ShipFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by michal on 15.01.18.
 */
public class BoardTest {
    private List<Ship> ships = new ArrayList<>();

    private Random random = new Random(System.currentTimeMillis());

    private Board board;

    @Before
    public void init() {
        Ship ship1 = ShipFactory.getShip(4);
        ship1.setDirection(Direction.HORIZONTAL);
        ship1.getFields().get(0).setRowId(7);
        ship1.getFields().get(0).setId(0);
        ship1.getFields().get(1).setRowId(7);
        ship1.getFields().get(1).setId(0);
        ship1.getFields().get(2).setRowId(7);
        ship1.getFields().get(2).setId(0);
        ship1.getFields().get(3).setRowId(7);
        ship1.getFields().get(3).setId(0);

        Ship ship2 = ShipFactory.getShip(3);
        ship2.setDirection(Direction.VERTICAL);
        ship2.getFields().get(0).setRowId(0);
        ship2.getFields().get(0).setId(3);
        ship2.getFields().get(1).setRowId(1);
        ship2.getFields().get(1).setId(3);
        ship2.getFields().get(2).setRowId(2);
        ship2.getFields().get(2).setId(3);

        Ship ship3 = ShipFactory.getShip(3);
        ship3.setDirection(Direction.VERTICAL);
        ship3.getFields().get(0).setRowId(2);
        ship3.getFields().get(0).setId(6);
        ship3.getFields().get(1).setRowId(3);
        ship3.getFields().get(1).setId(6);
        ship3.getFields().get(2).setRowId(4);
        ship3.getFields().get(2).setId(6);

        Ship ship4 = ShipFactory.getShip(2);
        ship4.setDirection(Direction.HORIZONTAL);
        ship4.getFields().get(0).setRowId(5);
        ship4.getFields().get(0).setId(0);
        ship4.getFields().get(1).setRowId(5);
        ship4.getFields().get(1).setId(1);

        Ship ship5 = ShipFactory.getShip(2);
        ship5.setDirection(Direction.HORIZONTAL);
        ship5.getFields().get(0).setRowId(3);
        ship5.getFields().get(0).setId(0);
        ship5.getFields().get(1).setRowId(3);
        ship5.getFields().get(1).setId(1);

        Ship ship6 = ShipFactory.getShip(2);
        ship6.setDirection(Direction.VERTICAL);
        ship6.getFields().get(0).setRowId(0);
        ship6.getFields().get(0).setId(0);
        ship6.getFields().get(1).setRowId(1);
        ship6.getFields().get(1).setId(0);

        Ship ship7 = ShipFactory.getShip(1);
        ship7.setDirection(Direction.VERTICAL);
        ship7.getFields().get(0).setRowId(7);
        ship7.getFields().get(0).setId(5);

        Ship ship8 = ShipFactory.getShip(1);
        ship8.setDirection(Direction.VERTICAL);
        ship8.getFields().get(0).setRowId(4);
        ship8.getFields().get(0).setId(3);

        Ship ship9 = ShipFactory.getShip(1);
        ship9.setDirection(Direction.VERTICAL);
        ship9.getFields().get(0).setRowId(0);
        ship9.getFields().get(0).setId(5);

        Ship ship10 = ShipFactory.getShip(1);
        ship10.setDirection(Direction.VERTICAL);
        ship10.getFields().get(0).setRowId(0);
        ship10.getFields().get(0).setId(7);

        ships.addAll(Arrays.asList(ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9, ship10));

        board = new Board(ships);
    }


    @Test
    public void recreateBoardTable() {
        List<BoardRow> boardRows = new ArrayList<>(board.getRows());
        board.createTable();
        assertEquals(boardRows, board.getRows());
    }

    @Test
    public void setActiveToAll() {
        board.setActiveToAll(true);

        for(BoardRow boardRow : board.getRows()) {
            for(BoardField boardField : boardRow.getFields()) {
                assertTrue(boardField.isActive());
            }
        }

        board.setActiveToAll(false);

        for(BoardRow boardRow : board.getRows()) {
            for(BoardField boardField : boardRow.getFields()) {
                assertFalse(boardField.isActive());
            }
        }
    }

    @Test
    public void setFieldStatusToAll() {
        board.setFieldStatusToAll(FieldStatus.HIT);

        for(BoardRow boardRow : board.getRows()) {
            for(BoardField boardField : boardRow.getFields()) {
                assertEquals(FieldStatus.HIT, boardField.getFieldType().getFieldStatus());
            }
        }
    }

    @Test
    public void addFieldStatusListener() {
        PropertyChangeListener propertyChangeListener = mock(PropertyChangeListener.class);
        ArgumentCaptor<PropertyChangeEvent> propertyChangeEvents = ArgumentCaptor.forClass(PropertyChangeEvent.class);

        board.addFieldStatusListener(propertyChangeListener);

        BoardRow randomBoardRow = board.getRows().get(random.nextInt(board.getRows().size()));
        BoardField randomField = randomBoardRow.getFields().get(random.nextInt(randomBoardRow.getFields().size()));
        FieldStatus oldValue = randomField.getFieldType().getFieldStatus();
        randomField.hit();

        verify(propertyChangeListener).propertyChange(propertyChangeEvents.capture());

        for(PropertyChangeEvent propertyChangeEvent : propertyChangeEvents.getAllValues()) {
            assertEquals(oldValue, propertyChangeEvent.getOldValue());
            assertEquals(FieldStatus.HIT, propertyChangeEvent.getNewValue());
        }


    }

    @Test
    public void endGame() {
        assertFalse(board.isEndGame());
        board.getShips().forEach(ship -> ship.getFields().forEach(BoardField::hit));

        assertTrue(board.isEndGame());
    }
}