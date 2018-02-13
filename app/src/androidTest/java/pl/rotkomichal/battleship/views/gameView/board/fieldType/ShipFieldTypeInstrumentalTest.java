package pl.rotkomichal.battleship.views.gameView.board.fieldType;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by michal on 15.01.18.
 */

@RunWith(AndroidJUnit4.class)
public class ShipFieldTypeInstrumentalTest {

    @Test
    public void parcelableTest() throws Exception {
        ShipFieldType shipFieldType = new ShipFieldType();
        shipFieldType.setId(10);
        shipFieldType.setFieldStatus(FieldStatus.HIT);

        Parcel parcel = Parcel.obtain();
        shipFieldType.writeToParcel(parcel, shipFieldType.describeContents());
        parcel.setDataPosition(0);

        ShipFieldType waterFieldTypeFromParcel = ShipFieldType.CREATOR.createFromParcel(parcel);
        assertThat(waterFieldTypeFromParcel.getId(), is(shipFieldType.getId()));
        assertThat(waterFieldTypeFromParcel.getFieldStatus(), is(shipFieldType.getFieldStatus()));
    }

}