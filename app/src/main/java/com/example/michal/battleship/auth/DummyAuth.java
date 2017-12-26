package com.example.michal.battleship.auth;

import com.example.michal.battleship.model.User;

import javax.security.auth.login.LoginException;

/**
 * Created by michal on 12.12.17.
 */

public class DummyAuth implements IAuth {
    @Override
    public User tryLogin(String email, String password) throws LoginException {
        User user = new User();
        user.setEmail(email);
        user.setCustomName("dummy");
        user.setPoints(0);
        user.setLevel(0);
        user.setId(0);
        return user;
    }
}
