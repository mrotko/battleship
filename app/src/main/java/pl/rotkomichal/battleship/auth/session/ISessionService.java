package pl.rotkomichal.battleship.auth.session;

import android.content.Context;

/**
 * Created by michal on 12.12.17.
 */

public interface ISessionService {
    ISessionManager getSessionManager(Context context);
}
