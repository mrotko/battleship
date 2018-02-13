package pl.rotkomichal.battleship.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michal on 15.01.18.
 */
public class PointsCalculatorTest {

    @Test
    public void hittedWater() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedWater();
        assertEquals(-10, pointsCalculator.getPoints());
    }

    @Test
    public void hittedShip() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        assertEquals(50, pointsCalculator.getPoints());
    }

    @Test
    public void combo1() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        assertEquals(100, pointsCalculator.getPoints());
    }

    @Test
    public void combo2() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        assertEquals(200, pointsCalculator.getPoints());
    }

    @Test
    public void combo3() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        assertEquals(400, pointsCalculator.getPoints());
    }

    @Test
    public void hittedShipAndSunked() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        assertEquals(300, pointsCalculator.getPoints());
    }

    @Test
    public void combo1AndSunked() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        assertEquals(350, pointsCalculator.getPoints());
    }

    @Test
    public void combo2AndSunked() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        assertEquals(450, pointsCalculator.getPoints());
    }

    @Test
    public void combo3AndSunked() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        assertEquals(650, pointsCalculator.getPoints());
    }

    @Test
    public void sunkedShip() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        assertEquals(300, pointsCalculator.getPoints());
    }


    @Test
    public void momentum1() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedWater();
        pointsCalculator.hittedWater();
        pointsCalculator.hittedShip();
        assertEquals(50, pointsCalculator.getPoints());
    }

    @Test
    public void momentum2() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedWater();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedWater();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        assertEquals(300, pointsCalculator.getPoints());
    }

    @Test
    public void momentum3() {
        PointsCalculator pointsCalculator = new PointsCalculator();
        pointsCalculator.hittedWater();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(1);
        pointsCalculator.hittedWater();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.hittedShip();
        pointsCalculator.setSunkedShipCount(2);
        assertEquals(650, pointsCalculator.getPoints());
    }
}