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
public class WaterFieldTypeTest {

    @Test
    public void testDefaultDrawableResourceId() {
        WaterFieldType waterFieldType = new WaterFieldType();
        assertEquals(R.drawable.water, waterFieldType.getDrawableResourceId());
    }

    @Test
    public void testHitDrawableResourceId() {
        WaterFieldType waterFieldType = new WaterFieldType();

        waterFieldType.setFieldStatus(FieldStatus.HIT);
        assertEquals(R.drawable.water_hit, waterFieldType.getDrawableResourceId());

    }
    @Test
    public void testHiddenDrawableResourceId() {
        WaterFieldType waterFieldType = new WaterFieldType();

        waterFieldType.setFieldStatus(FieldStatus.HIDDEN);
        assertEquals(R.drawable.hidden_field, waterFieldType.getDrawableResourceId());

    }
    @Test
    public void testVisibleDrawableResourceId() {
        WaterFieldType waterFieldType = new WaterFieldType();

        waterFieldType.setFieldStatus(FieldStatus.HIT);
        waterFieldType.setFieldStatus(FieldStatus.VISIBLE);
        assertEquals(R.drawable.water, waterFieldType.getDrawableResourceId());

    }
    @Test
    public void testDrawableResourceId() {
        WaterFieldType waterFieldType = new WaterFieldType();
        assertEquals(R.drawable.water, waterFieldType.getDrawableResourceId());

        waterFieldType.setFieldStatus(FieldStatus.HIT);
        assertEquals(R.drawable.water_hit, waterFieldType.getDrawableResourceId());

    }

    @Test
    public void propertyChangeEventTest() {
        PropertyChangeListener propertyChangeListener1 = mock(PropertyChangeListener.class);
        PropertyChangeListener propertyChangeListener2 = mock(PropertyChangeListener.class);

        WaterFieldType waterFieldType = new WaterFieldType();
        waterFieldType.addPropertyChangeListener(propertyChangeListener1);
        waterFieldType.addPropertyChangeListener(propertyChangeListener2);

        waterFieldType.setFieldStatus(FieldStatus.HIDDEN);

        ArgumentCaptor<PropertyChangeEvent> propertyChangeEvents = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        verify(propertyChangeListener1).propertyChange(propertyChangeEvents.capture());
        verify(propertyChangeListener2).propertyChange(propertyChangeEvents.capture());

        for(PropertyChangeEvent propertyChangeEvent : propertyChangeEvents.getAllValues()) {
            assertEquals(WaterFieldType.class.getSimpleName(), propertyChangeEvent.getPropertyName());
            assertEquals(FieldStatus.VISIBLE, propertyChangeEvent.getOldValue());
            assertEquals(FieldStatus.HIDDEN, propertyChangeEvent.getNewValue());
        }

        waterFieldType.removePropertyChangeListener(propertyChangeListener1);
        waterFieldType.removePropertyChangeListener(propertyChangeListener2);

        waterFieldType.setFieldStatus(FieldStatus.HIDDEN);

        verifyNoMoreInteractions(propertyChangeListener1, propertyChangeListener2);
    }
}