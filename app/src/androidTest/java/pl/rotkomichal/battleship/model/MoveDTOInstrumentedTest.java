package pl.rotkomichal.battleship.model;

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
public class MoveDTOInstrumentedTest {

    @Test
    public void parcelableTest() throws Exception {
        MoveDTO moveDTO = new MoveDTO(10, 20);
        moveDTO.setId(50);

        Parcel parcel = Parcel.obtain();
        moveDTO.writeToParcel(parcel, moveDTO.describeContents());
        parcel.setDataPosition(0);

        MoveDTO moveDTOFromParcel = MoveDTO.CREATOR.createFromParcel(parcel);
        assertThat(moveDTOFromParcel.getRowId(), is(moveDTO.getRowId()));
        assertThat(moveDTOFromParcel.getFieldId(), is(moveDTO.getFieldId()));
        assertThat(moveDTOFromParcel.getId(), is(moveDTO.getId()));
    }
}