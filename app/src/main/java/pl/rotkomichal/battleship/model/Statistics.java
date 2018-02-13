package pl.rotkomichal.battleship.model;

import android.os.Parcel;

/**
 * Created by michal on 14.01.18.
 */

public class Statistics extends SimpleObject {

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
        return (int) ((double) hittedShipFields / (double) (hittedWaterFields + hittedShipFields) * 100.0);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.playerName);
        dest.writeInt(this.hittedShipFields);
        dest.writeInt(this.hittedWaterFields);
        dest.writeInt(this.points);
        dest.writeInt(this.id);
    }

    protected Statistics(Parcel in) {
        this.playerName = in.readString();
        this.hittedShipFields = in.readInt();
        this.hittedWaterFields = in.readInt();
        this.points = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<Statistics> CREATOR = new Creator<Statistics>() {
        @Override
        public Statistics createFromParcel(Parcel source) {
            return new Statistics(source);
        }

        @Override
        public Statistics[] newArray(int size) {
            return new Statistics[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;

        Statistics that = (Statistics) o;

        if (hittedShipFields != that.hittedShipFields) return false;
        if (hittedWaterFields != that.hittedWaterFields) return false;
        if (points != that.points) return false;
        return playerName != null ? playerName.equals(that.playerName) : that.playerName == null;
    }

    @Override
    public int hashCode() {
        int result = playerName != null ? playerName.hashCode() : 0;
        result = 31 * result + hittedShipFields;
        result = 31 * result + hittedWaterFields;
        result = 31 * result + points;
        return result;
    }
}
