package pl.rotkomichal.battleship.auth.session;

import pl.rotkomichal.battleship.model.User;

/**
 * Created by michal on 12.12.17.
 */

public interface ISessionManager {
    void createLoginSession(User user);

    void checkLogin();

    User getUser();

    boolean isLogged();

    void logoutUser();
}
