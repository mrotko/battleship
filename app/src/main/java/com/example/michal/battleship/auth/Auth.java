package com.example.michal.battleship.auth;

import com.example.michal.battleship.model.User;

import javax.security.auth.login.LoginException;

/**
 * Created by michal on 25.12.17.
 */

class Auth implements IAuth {
    @Override
    public User tryLogin(String email, String password) throws LoginException {
        return null;
    }
}
