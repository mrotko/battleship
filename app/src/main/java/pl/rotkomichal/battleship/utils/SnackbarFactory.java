package pl.rotkomichal.battleship.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import pl.rotkomichal.battleship.R;

/**
 * Created by michal on 17.12.17.
 */

public class SnackbarFactory {

    private static int duration = 10000;

    public static Snackbar getWarning(View view, CharSequence text) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        snackbar.setAction(view.getResources().getString(R.string.hide), view1 -> snackbar.dismiss());
        return snackbar;
    }

    public static Snackbar getInfo(View view, CharSequence text) {
        return Snackbar.make(view, text, duration);
    }

    public static Snackbar getError(View view, CharSequence text) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        snackbar.setAction(view.getResources().getString(R.string.hide), view1 -> snackbar.dismiss());
        return snackbar;
    }

    public static Snackbar getSuccess(View view, CharSequence text) {
        return Snackbar.make(view, text, duration);
    }
}
