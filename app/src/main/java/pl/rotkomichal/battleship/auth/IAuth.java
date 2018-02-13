package pl.rotkomichal.battleship.auth;

import pl.rotkomichal.battleship.model.User;

/**
 * Created by michal on 12.12.17.
 */

public interface IAuth {
    User tryLogin(String email, String password);
}
