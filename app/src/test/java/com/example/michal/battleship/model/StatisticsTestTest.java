package com.example.michal.battleship.model;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michal on 15.01.18.
 */
public class StatisticsTestTest {
    @Test
    public void equalsTest() throws Exception {
        Statistics s1 = new Statistics("nazwa");
        Statistics s2 = new Statistics("nazwa");

        assertEquals(s1, s2);
    }

    @Test
    public void hashCodeTest() throws Exception {
        Statistics s1 = new Statistics("nazwa");
        Statistics s2 = new Statistics("nazwa");

        assertTrue(s1.hashCode() == s2.hashCode());
    }

    @Test
    public void getPlayerName() throws Exception {
        String playerName = "player";
        Statistics statistics = new Statistics(playerName);
        assertEquals(playerName, statistics.getPlayerName());
    }

    @Test
    public void getHittedShipFields() throws Exception {
        Statistics statistics = new Statistics("name");
        assertEquals(0, statistics.getHittedShipFields());
    }

    @Test
    public void getHittedWaterFields() throws Exception {
        Statistics statistics = new Statistics("name");
        assertEquals(0, statistics.getHittedWaterFields());
    }

    @Test
    public void getPoints() throws Exception {
        Statistics statistics = new Statistics("name");
        assertEquals(0, statistics.getPoints());
    }

    @Test
    public void addPoints() throws Exception {
        Statistics statistics = new Statistics("name");
        PointsCalculator pointsCalculator = new PointsCalculator();
        Random random = new Random(System.currentTimeMillis());
        int points = 0;
        for (int i = 0; i < 15; i++) {
            if(random.nextBoolean()) {
                pointsCalculator.hittedShip();
            } else {
                pointsCalculator.hittedWater();
            }

            statistics.addPoints(pointsCalculator);
            points += pointsCalculator.getPoints();
        }


        assertEquals(points, statistics.getPoints());
    }

    @Test
    public void shipHitted() throws Exception {
        Statistics statistics = new Statistics("name");
        int actualShipHits = statistics.getHittedShipFields();
        statistics.shipHitted();

        assertEquals(actualShipHits + 1, statistics.getHittedShipFields());
    }

    @Test
    public void waterHitted() throws Exception {
        Statistics statistics = new Statistics("name");
        int actualWaterHits = statistics.getHittedWaterFields();
        statistics.waterHitted();

        assertEquals(actualWaterHits + 1, statistics.getHittedWaterFields());
    }

    @Test
    public void getShotPercentage() throws Exception {
        Statistics statistics = new Statistics("name");
        double waterShots = 0;
        double shipShots = 0;


        for(int i = 0; i < 10; i++) {
            waterShots += 1;
            statistics.waterHitted();
        }

        for(int i = 0; i < 17; i++) {
            shipShots += 1;
            statistics.shipHitted();
        }

        assertEquals((int) (shipShots / (waterShots + shipShots) * 100), statistics.getShotPercentage());
    }
}