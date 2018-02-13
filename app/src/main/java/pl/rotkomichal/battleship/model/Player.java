package pl.rotkomichal.battleship.model;

import android.os.Parcel;

import pl.rotkomichal.battleship.views.gameView.board.Board;

/**
 * Created by michal on 15.12.17.
 */

public class Player extends SimpleObject {

    private Board board;

    private User user;

    private Statistics statistics;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public Player() {
    }


    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.board, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.statistics, flags);
        dest.writeInt(this.id);
    }

    protected Player(Parcel in) {
        this.board = in.readParcelable(Board.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.statistics = in.readParcelable(Statistics.class.getClassLoader());
        this.id = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (board != null ? !board.equals(player.board) : player.board != null) return false;
        if (user != null ? !user.equals(player.user) : player.user != null) return false;
        return statistics != null ? statistics.equals(player.statistics) : player.statistics == null;
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (statistics != null ? statistics.hashCode() : 0);
        return result;
    }
}
