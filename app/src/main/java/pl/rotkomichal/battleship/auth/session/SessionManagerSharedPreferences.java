package pl.rotkomichal.battleship.auth.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import pl.rotkomichal.battleship.model.User;
import pl.rotkomichal.battleship.views.authView.LoginActivity;

/**
 * Created by michal on 12.12.17.
 */

public class SessionManagerSharedPreferences implements ISessionManager{
    private SharedPreferences sharedPreferences;

    private Editor editor;

    private Context context;

    private int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "USER_DATA";

    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_CUSTOM_NAME = "customName";

    public static final String KEY_POINTS = "points";

    public static final String KEY_LEVEL = "level";

    public SessionManagerSharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    @Override
    public void createLoginSession(User user) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_CUSTOM_NAME, user.getCustomName());
        editor.putInt(KEY_POINTS, user.getPoints());
        editor.putInt(KEY_LEVEL, user.getPoints());
        editor.commit();
    }

    @Override
    public void checkLogin() {
        if(!isLogged()) {
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setEmail(sharedPreferences.getString(KEY_EMAIL, null));
        user.setCustomName(sharedPreferences.getString(KEY_CUSTOM_NAME, ""));
        user.setPoints(sharedPreferences.getInt(KEY_POINTS, -1));
        user.setLevel(sharedPreferences.getInt(KEY_LEVEL, -1));
        return user;
    }

    @Override
    public boolean isLogged() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    @Override
    public void logoutUser() {
        editor.clear();
        editor.commit();
        startLoginActivity();
    }
}
