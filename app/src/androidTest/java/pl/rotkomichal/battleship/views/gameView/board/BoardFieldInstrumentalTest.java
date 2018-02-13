package pl.rotkomichal.battleship.views.gameView.board;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import pl.rotkomichal.battleship.views.gameView.board.fieldType.IFieldType;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.ShipFieldType;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.WaterFieldType;

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
public class BoardFieldInstrumentalTest {

    @Test
    public void parcelableTest() throws Exception {
        List<IFieldType> fieldTypes = new ArrayList<>();
        fieldTypes.add(new WaterFieldType());
        fieldTypes.add(new ShipFieldType());

        for (IFieldType fieldType :
                fieldTypes) {
            BoardField boardField = new BoardField(10);
            boardField.setFieldType(fieldType);
            boardField.setActive(true);
            boardField.setRowId(15);

            Parcel parcel = Parcel.obtain();
            boardField.writeToParcel(parcel, boardField.describeContents());
            parcel.setDataPosition(0);


            BoardField boardFieldFromParcel = BoardField.CREATOR.createFromParcel(parcel);
            assertThat(boardFieldFromParcel.getId(), is(boardField.getId()));
            assertThat(boardFieldFromParcel.getFieldType(), is(boardField.getFieldType()));
            assertThat(boardFieldFromParcel.getRowId(), is(boardField.getRowId()));
        }
    }
}