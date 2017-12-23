package com.example.michal.battleship.auth.session;

import com.example.michal.battleship.model.User;

/**
 * Created by michal on 12.12.17.
 */

public interface ISessionManager {
    void createLoginSession(User user);

    void checkLogin();

    void getUser();

    boolean isLogged();

    void logoutUser();
}
