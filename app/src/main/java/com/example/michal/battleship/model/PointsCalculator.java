package com.example.michal.battleship.model;

/**
 * Created by michal on 14.01.18.
 */

public class PointsCalculator implements IPointsCalculator {

    private int combo = 0;

    private int sunkedShipCount = 0;

    private int pointsAttemptMiss = -10;

    private int pointsBase = 50;

    private int pointsSunkedShip = 250;

    private boolean shipIsSunked = false;

    public void setSunkedShipCount(int sunkedShipCount) {
        shipIsSunked = this.sunkedShipCount < sunkedShipCount;
        this.sunkedShipCount = sunkedShipCount;
    }

    public void hittedShip() {
        if(combo == 0) {
            combo = 1;
        } else {
            combo *= 2;
        }
    }

    public void hittedWater() {
        combo = 0;
        shipIsSunked = false;
    }

    @Override
    public int getPoints() {
        int points = shipIsSunked ? pointsSunkedShip : 0;
        points += combo == 0 ? pointsAttemptMiss : combo * pointsBase;
        return points;
    }
}
