package com.example.michal.battleship.auth.hash;

import android.os.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by michal on 12.12.17.
 */

public class SHA512HashProvider implements IHashProvider {

    private String salt = "asdfgh";

    @Override
    public String getHashed(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte [] bytes = md.digest(text.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : bytes) {
                stringBuilder.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
