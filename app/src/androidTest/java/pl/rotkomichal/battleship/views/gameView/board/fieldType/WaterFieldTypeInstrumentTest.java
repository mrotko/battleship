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
public class WaterFieldTypeInstrumentTest {

    @Test
    public void parcelableTest() throws Exception {
        WaterFieldType waterFieldType = new WaterFieldType();
        waterFieldType.setId(10);
        waterFieldType.setFieldStatus(FieldStatus.HIT);

        Parcel parcel = Parcel.obtain();
        waterFieldType.writeToParcel(parcel, waterFieldType.describeContents());
        parcel.setDataPosition(0);

        WaterFieldType waterFieldTypeFromParcel = WaterFieldType.CREATOR.createFromParcel(parcel);
        assertThat(waterFieldTypeFromParcel.getId(), is(waterFieldType.getId()));
        assertThat(waterFieldTypeFromParcel.getFieldStatus(), is(waterFieldType.getFieldStatus()));
    }
}