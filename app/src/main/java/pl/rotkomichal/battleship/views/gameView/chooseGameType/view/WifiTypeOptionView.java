package pl.rotkomichal.battleship.views.gameView.chooseGameType.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import pl.rotkomichal.battleship.R;

/**
 * Created by michal on 13.12.17.
 */

public class WifiTypeOptionView extends LinearLayout {

    private Button searchOpponentBtn;

    private ProgressBar searchOpponentPb;

    private EditText opponentNameEt;

    private Button chooseBtn;

    public WifiTypeOptionView(Context context) {
        super(context);
        initialize();
    }

    public WifiTypeOptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public WifiTypeOptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.layout_choose_game_type_wifi, this);
        initSearchOpponentBtn();
        initSearchOpponentPb();
        initOpponentNameEt();
        initChooseBtn();

    }

    private void initSearchOpponentBtn() {
        searchOpponentBtn = findViewById(R.id.btnSearchOpponent);
    }

    private void initSearchOpponentPb() {
        searchOpponentPb = findViewById(R.id.pbSearchOpponent);
    }

    private void initOpponentNameEt() {
        opponentNameEt = findViewById(R.id.etOpponentName);
    }

    private void initChooseBtn() {
        chooseBtn = findViewById(R.id.btnChooseGameType);
    }
}
