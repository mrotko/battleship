package pl.rotkomichal.battleship.views.gameView.chooseGameType.view;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import pl.rotkomichal.battleship.R;
import pl.rotkomichal.battleship.views.gameView.chooseGameType.GameTypeOption;


public class ComputerTypeOptionView extends LinearLayout {

    private Button chooseBtn;

    private RadioGroup chooseComputerStrengthRg;

    private GameTypeOption currentGameTypeOption;

    public ComputerTypeOptionView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.layout_choose_game_type_computer, this);
        initChooseComputerStrengthRb();
        initChooseButton();

    }

    private void initChooseComputerStrengthRb() {
        chooseComputerStrengthRg = findViewById(R.id.rgChooseComputerStrength);
        chooseComputerStrengthRg.setOnCheckedChangeListener((group, btnId) -> {
            chooseBtn.setVisibility(VISIBLE);
            switch (btnId) {
                case R.id.btnComputerBeginner:
                    currentGameTypeOption = GameTypeOption.COMPUTER_BEGINNER;
                    break;
                case R.id.btnComputerNormal:
                    currentGameTypeOption = GameTypeOption.COMPUTER_NORMAL;
                    break;
            }
        });
    }

    public GameTypeOption getGameTypeOption() {
        return currentGameTypeOption;
    }

    private void initChooseButton() {
        chooseBtn = findViewById(R.id.btnChooseGameType);
    }

    public void setButtonOnClickAction(OnClickListener listener) {
        chooseBtn.setOnClickListener(listener);
        chooseBtn.setVisibility(INVISIBLE);
    }
}
