package pl.rotkomichal.battleship.utils;

import java.util.regex.Pattern;

/**
 * Created by michal on 26.12.17.
 */

public class Patterns {

    public static final Pattern EMAIL = Pattern.compile("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");
    public static final Pattern PASSWORD = Pattern.compile("[0-9a-zA-Z]{8,}");
}
