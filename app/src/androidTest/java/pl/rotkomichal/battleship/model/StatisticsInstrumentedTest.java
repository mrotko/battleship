package pl.rotkomichal.battleship.model;

import android.os.Parcel;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by michal on 15.01.18.
 */
public class StatisticsInstrumentedTest {

    @Test
    public void parcelableTest() {
        String name = "name";
        Statistics statistics = new Statistics(name);
        statistics.shipHitted();
        statistics.waterHitted();
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        statistics.addPoints(pointsCalculator);
        pointsCalculator.hittedWater();
        statistics.addPoints(pointsCalculator);
        Parcel parcel = Parcel.obtain();
        statistics.writeToParcel(parcel, statistics.describeContents());
        parcel.setDataPosition(0);

        Statistics statisticsFromParcel = Statistics.CREATOR.createFromParcel(parcel);
        assertThat(statisticsFromParcel.getPlayerName(), is(statistics.getPlayerName()));
        assertThat(statisticsFromParcel.getHittedShipFields(), is(statistics.getHittedShipFields()));
        assertThat(statisticsFromParcel.getHittedWaterFields(), is(statistics.getHittedWaterFields()));
        assertThat(statisticsFromParcel.getPoints(), is(statistics.getPoints()));
        assertThat(statisticsFromParcel.getShotPercentage(), is(statistics.getShotPercentage()));
    }
}