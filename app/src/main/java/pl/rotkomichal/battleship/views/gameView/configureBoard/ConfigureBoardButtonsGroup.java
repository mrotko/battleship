package pl.rotkomichal.battleship.views.gameView.configureBoard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import pl.rotkomichal.battleship.R;

/**
 * Created by michal on 17.12.17.
 */

public class ConfigureBoardButtonsGroup extends LinearLayout {

    private Button autoArrangeBtn;

    private Button resetBoardBtn;

    private Button startGameBtn;

    public ConfigureBoardButtonsGroup(Context context) {
        super(context);
        init();
    }

    public ConfigureBoardButtonsGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConfigureBoardButtonsGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_configure_board_buttons, this);
        createAutoArrangeBtn();
        createResetBoardBtn();
        createStartGameBtn();
    }

    private void createAutoArrangeBtn() {
        autoArrangeBtn = findViewById(R.id.btnAutoArrange);
    }

    private void createResetBoardBtn() {
        resetBoardBtn = findViewById(R.id.btnResetBoard);
    }

    private void createStartGameBtn() {
        startGameBtn = findViewById(R.id.btnStartGame);
    }

    public Button getAutoArrangeBtn() {
        return autoArrangeBtn;
    }

    public Button getResetBoardBtn() {
        return resetBoardBtn;
    }

    public Button getStartGameBtn() {
        return startGameBtn;
    }
}
