package com.example.michal.battleship.views.gameView.board;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.example.michal.battleship.views.gameView.board.fieldType.IFieldType;
import com.example.michal.battleship.views.gameView.board.fieldType.ShipFieldType;
import com.example.michal.battleship.views.gameView.board.fieldType.WaterFieldType;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by michal on 15.01.18.
 */

@RunWith(AndroidJUnit4.class)
public class BoardRowInstrumentalTest {

    @Test
    public void parcelableTest() throws Exception {
        List<IFieldType> fieldTypes = new ArrayList<>();
        fieldTypes.add(new WaterFieldType());
        fieldTypes.add(new ShipFieldType());

        BoardRow boardRow = new BoardRow(1);

        int id = 0;
        for (IFieldType fieldType :
                fieldTypes) {
            for(int i = 0; i < 5; i++) {
                BoardField boardField = new BoardField(id++);
                boardField.setRowId(i);
                boardField.setActive(true);
                boardField.setFieldType(fieldType);
                boardRow.getFields().add(new BoardField());
            }
        }

        Parcel parcel = Parcel.obtain();
        boardRow.writeToParcel(parcel, boardRow.describeContents());
        parcel.setDataPosition(0);

        BoardRow boardRowFromParcel = BoardRow.CREATOR.createFromParcel(parcel);
        assertThat(boardRowFromParcel.getId(), is(boardRow.getId()));
        assertThat(boardRowFromParcel.getFields(), is(boardRow.getFields()));
    }
}