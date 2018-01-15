package com.example.michal.battleship.views.gameView.board.fieldType;

import com.example.michal.battleship.R;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by michal on 15.01.18.
 */
public class ShipFieldTypeTest {

    @Test
    public void testDefaultDrawableResourceId() {
        ShipFieldType shipFieldType = new ShipFieldType();
        assertEquals(R.drawable.ship, shipFieldType.getDrawableResourceId());
    }

    @Test
    public void testHitDrawableResourceId() {
        ShipFieldType shipFieldType = new ShipFieldType();

        shipFieldType.setFieldStatus(FieldStatus.HIT);
        assertEquals(R.drawable.ship_hit, shipFieldType.getDrawableResourceId());

    }
    @Test
    public void testHiddenDrawableResourceId() {
        ShipFieldType shipFieldType = new ShipFieldType();

        shipFieldType.setFieldStatus(FieldStatus.HIDDEN);
        assertEquals(R.drawable.hidden_field, shipFieldType.getDrawableResourceId());

    }
    @Test
    public void testVisibleDrawableResourceId() {
        ShipFieldType shipFieldType = new ShipFieldType();

        shipFieldType.setFieldStatus(FieldStatus.HIT);
        shipFieldType.setFieldStatus(FieldStatus.VISIBLE);
        assertEquals(R.drawable.ship, shipFieldType.getDrawableResourceId());

    }
    @Test
    public void testDrawableResourceId() {
        ShipFieldType shipFieldType = new ShipFieldType();
        assertEquals(R.drawable.ship, shipFieldType.getDrawableResourceId());

        shipFieldType.setFieldStatus(FieldStatus.HIT);
        assertEquals(R.drawable.ship_hit, shipFieldType.getDrawableResourceId());

    }

    @Test
    public void propertyChangeEventTest() {
        PropertyChangeListener propertyChangeListener1 = mock(PropertyChangeListener.class);
        PropertyChangeListener propertyChangeListener2 = mock(PropertyChangeListener.class);

        ShipFieldType shipFieldType = new ShipFieldType();
        shipFieldType.addPropertyChangeListener(propertyChangeListener1);
        shipFieldType.addPropertyChangeListener(propertyChangeListener2);

        shipFieldType.setFieldStatus(FieldStatus.HIDDEN);

        ArgumentCaptor<PropertyChangeEvent> propertyChangeEvents = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        verify(propertyChangeListener1).propertyChange(propertyChangeEvents.capture());
        verify(propertyChangeListener2).propertyChange(propertyChangeEvents.capture());

        for(PropertyChangeEvent propertyChangeEvent : propertyChangeEvents.getAllValues()) {
            assertEquals(ShipFieldType.class.getSimpleName(), propertyChangeEvent.getPropertyName());
            assertEquals(FieldStatus.VISIBLE, propertyChangeEvent.getOldValue());
            assertEquals(FieldStatus.HIDDEN, propertyChangeEvent.getNewValue());
        }

        shipFieldType.removePropertyChangeListener(propertyChangeListener1);
        shipFieldType.removePropertyChangeListener(propertyChangeListener2);

        shipFieldType.setFieldStatus(FieldStatus.HIDDEN);

        verifyNoMoreInteractions(propertyChangeListener1, propertyChangeListener2);
    }
}