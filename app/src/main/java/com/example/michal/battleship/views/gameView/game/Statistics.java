package com.example.michal.battleship.views.gameView.game;

import com.example.michal.battleship.model.SimpleObject;

/**
 * Created by michal on 14.01.18.
 */

class Statistics extends SimpleObject {

    private String playerName;

    private int hittedShipFields;

    private int hittedWaterFields;

    private int points;

    public Statistics(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHittedShipFields() {
        return hittedShipFields;
    }

    public int getHittedWaterFields() {
        return hittedWaterFields;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(IPointsCalculator pointsCalculator) {
        points += pointsCalculator.getPoints();
    }

    public void shipHitted() {
        hittedShipFields += 1;
    }

    public void waterHitted() {
        hittedWaterFields += 1;
    }

    public int getShotPercentage() {
        return (int) ((double) hittedShipFields / (double) hittedWaterFields * 100.0);
    }

}
