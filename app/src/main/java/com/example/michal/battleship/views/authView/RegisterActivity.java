package com.example.michal.battleship.views.authView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.michal.battleship.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText customNameEt;

    private EditText emailEt;

    private EditText passwordEt;

    private EditText repeatPasswordEt;

    private Button createAccountBtn;

    private Button registerByGoogleBtn;

    private Button switchToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize() {
        customNameEt = findViewById(R.id.etCustomName);
        customNameEt.setOnFocusChangeListener((view, isFocus) -> {

        });

        emailEt = findViewById(R.id.etEmail);
        emailEt.setOnFocusChangeListener((view, isFocus) -> {

        });

        passwordEt = findViewById(R.id.etPassword);
        passwordEt.setOnFocusChangeListener((view, isFocus) -> {

        });

        repeatPasswordEt = findViewById(R.id.etRepeatPassword);
        repeatPasswordEt.setOnFocusChangeListener((view, isFocus) -> {

        });

        createAccountBtn = findViewById(R.id.btnCreateAccount);
        createAccountBtn.setOnClickListener(view -> {

        });
        registerByGoogleBtn = findViewById(R.id.btnCreateAccountByGoogle);
        registerByGoogleBtn.setOnClickListener(view -> {

        });
        switchToLoginBtn = findViewById(R.id.btnSwitchToLogin);
        switchToLoginBtn.setOnClickListener(view -> {
            finish();
        });
    }
}
