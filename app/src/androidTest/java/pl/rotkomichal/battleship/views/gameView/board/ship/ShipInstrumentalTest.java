package pl.rotkomichal.battleship.views.gameView.board.ship;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import pl.rotkomichal.battleship.views.gameView.board.BoardField;
import pl.rotkomichal.battleship.views.gameView.board.fieldType.ShipFieldType;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by michal on 15.01.18.
 */

@RunWith(AndroidJUnit4.class)
public class ShipInstrumentalTest {

    @Test
    public void parcelableTest() throws Exception {
        Ship ship = new Ship();
        ship.setDirection(Direction.HORIZONTAL);
        for(int i = 0; i < 3; i++) {
            BoardField boardField = new BoardField(i);
            boardField.setActive(true);
            boardField.setRowId(i);
            boardField.setFieldType(new ShipFieldType());
            ship.getFields().add(boardField);
        }


        Parcel parcel = Parcel.obtain();
        ship.writeToParcel(parcel, ship.describeContents());
        parcel.setDataPosition(0);

        Ship shipFromParcel = Ship.CREATOR.createFromParcel(parcel);
        assertThat(shipFromParcel.getId(), is(ship.getId()));
        assertThat(shipFromParcel.getFields(), is(ship.getFields()));
        assertThat(shipFromParcel.getDirection(), is(ship.getDirection()));
    }
}