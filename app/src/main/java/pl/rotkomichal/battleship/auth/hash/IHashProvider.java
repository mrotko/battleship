package pl.rotkomichal.battleship.auth.hash;

/**
 * Created by michal on 12.12.17.
 */

public interface IHashProvider {
    String getHashed(String text);
}
