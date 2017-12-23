package com.example.michal.battleship.views.authView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.michal.battleship.R;
import com.example.michal.battleship.auth.DummyAuthService;
import com.example.michal.battleship.auth.ISimpleAuthService;
import com.example.michal.battleship.auth.session.ISessionManager;
import com.example.michal.battleship.auth.session.SessionService;
import com.example.michal.battleship.model.User;
import com.example.michal.battleship.views.MainActivity;

import java.security.AuthProvider;

import javax.security.auth.login.LoginException;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;

    private Button googleLoginBtn;

    private Button switchToRegisterBtn;

    private EditText emailEt;

    private EditText passwordEt;

    private EditText messagesEt;

    private ISimpleAuthService simpleAuthService;

    private SessionService sessionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        simpleAuthService = new DummyAuthService();
        sessionService = new SessionService();
        initialize();
    }

    private void initialize() {
        initializeEmailEt();
        initializePasswordEt();
        initializeLoginBtn();
        initializeGoogleLoginBtn();
        initializeSwitchToRegisterBtn();
        initializeMessagesEt();
    }

    private void initializeEmailEt() {
        emailEt = findViewById(R.id.etEmail);
        emailEt.setOnFocusChangeListener((view, isFocus) -> {

        });
    }

    private void initializePasswordEt() {
        passwordEt = findViewById(R.id.etPassword);
        passwordEt.setOnFocusChangeListener((view, isFocus) -> {

        });
    }

    private void initializeLoginBtn() {
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            try {
                User user = simpleAuthService.tryLogin(emailEt.getText().toString(), passwordEt.getText().toString());
                ISessionManager sessionManager = sessionService.getSessionManager(getApplicationContext());
                sessionManager.createLoginSession(user);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                finish();
            } catch (LoginException e) {
                messagesEt.setText(R.string.loginError);
            }
        });
    }

    private void initializeGoogleLoginBtn() {
        googleLoginBtn = findViewById(R.id.btnGoogleLogin);
        googleLoginBtn.setOnClickListener(view -> {

        });
    }

    private void initializeSwitchToRegisterBtn() {
        switchToRegisterBtn = findViewById(R.id.btnSwitchToRegister);
        switchToRegisterBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initializeMessagesEt() {
        messagesEt = findViewById(R.id.etMessages);
        messagesEt.setVisibility(View.INVISIBLE);
    }
}