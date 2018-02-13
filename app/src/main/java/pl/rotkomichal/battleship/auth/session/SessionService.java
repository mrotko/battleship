package pl.rotkomichal.battleship.auth.session;

import android.content.Context;

/**
 * Created by michal on 12.12.17.
 */

public class SessionService implements ISessionService {
    @Override
    public ISessionManager getSessionManager(Context context) {
        return new SessionManagerSharedPreferences(context);
    }
}
