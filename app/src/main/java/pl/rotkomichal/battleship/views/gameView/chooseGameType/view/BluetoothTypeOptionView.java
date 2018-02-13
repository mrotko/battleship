package pl.rotkomichal.battleship.views.gameView.chooseGameType.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import pl.rotkomichal.battleship.R;

/**
 * Created by michal on 13.12.17.
 */

public class BluetoothTypeOptionView extends LinearLayout {
    public BluetoothTypeOptionView(Context context) {
        super(context);
        initialize();
    }

    public BluetoothTypeOptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.layout_choose_game_type_bluetooth, this);
        initialize();
    }

    public BluetoothTypeOptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.layout_choose_game_type_bluetooth, this);
    }
}
