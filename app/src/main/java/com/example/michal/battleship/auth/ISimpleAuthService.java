package com.example.michal.battleship.auth;

import com.example.michal.battleship.model.User;

import javax.security.auth.login.LoginException;

/**
 * Created by michal on 12.12.17.
 */

public interface ISimpleAuthService {
    User tryLogin(String email, String password) throws LoginException;
}
