package com.example.michal.battleship.views.authView;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.michal.battleship.AppConfig;
import com.example.michal.battleship.AppController;
import com.example.michal.battleship.R;
import com.example.michal.battleship.auth.hash.IHashProvider;
import com.example.michal.battleship.auth.hash.SHA512HashProvider;
import com.example.michal.battleship.auth.session.SessionService;
import com.example.michal.battleship.model.User;
import com.example.michal.battleship.utils.Patterns;
import com.example.michal.battleship.views.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;

    private Button googleLoginBtn;

    private Button switchToRegisterBtn;

    private EditText emailEt;

    private EditText passwordEt;

    private SessionService sessionService;

    private ProgressBar progressBar;

    private IHashProvider hashProvider;

    private ConstraintLayout loginActivityCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createLoginActivityView();
        createProgressBar();
        createEmailEt();
        createPasswordEt();
        createLoginBtn();
        createGoogleLoginBtn();
        createSwitchToRegisterBtn();
        createSessionService();
        createHashProvider();
    }

    private void createLoginActivityView() {
        loginActivityCl = findViewById(R.id.cl_login_activity);
    }

    private void createProgressBar() {
        progressBar = findViewById(R.id.progressBar);
        hideProgressBar();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void createEmailEt() {
        emailEt = findViewById(R.id.etEmail);
        emailEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                if(!Patterns.EMAIL.matcher(emailEt.getText().toString()).matches()) {
                    emailEt.setError(getResources().getString(R.string.error_email));
                } else {
                    emailEt.setError(null);
                }
            }
        });
    }

    private void createPasswordEt() {
        passwordEt = findViewById(R.id.etPassword);
        passwordEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                if(!Patterns.PASSWORD.matcher(passwordEt.getText().toString()).matches()) {
                    passwordEt.setError(getResources().getString(R.string.error_password));
                } else {
                    passwordEt.setError(null);
                }
            }
        });
    }

    private void createLoginBtn() {
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            hideKeyboard();
            if(     emailEt.getError() == null &&
                    passwordEt.getError() == null &&
                    !emailEt.getText().toString().isEmpty() &&
                    !passwordEt.getText().toString().isEmpty()) {
                String email = emailEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();
                checkLogin(email, password);
            } else {
                showToast(R.string.emptyCredentials);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(loginActivityCl.getWindowToken(), 0);
        }
    }

    private void checkLogin(String email, String password) {
        String tag_login_request = "login_request";
        String hashPass = hashProvider.getHashed(password);
        showProgressBar();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_DATABASE,
                response -> {
            System.out.println(response);
                    hideProgressBar();
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        switch (jsonResponse.getInt("status")) {
                            case 0:
                                addUserToSession(jsonResponse.getJSONObject("userData"));
                                break;
                            case 1:
                                showToast(R.string.error_login);
                                break;
                            default:
                                showToast(R.string.error_unknown);
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.e(tag_login_request, "Login Error: " + error.getMessage());
            showToast(error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", "login");
                params.put("email", email);
                params.put("hashPass", hashPass);
                return params;
            }
        };
        AppController.getInstance().addRequestToQueue(request, tag_login_request);
    }

    private void addUserToSession(JSONObject userData) throws JSONException {
        User user = new User();
        user.setId(userData.getInt("id"));
        user.setEmail(userData.getString("email"));
        user.setCustomName(userData.getString("customName"));
        user.setLevel(userData.getInt("level"));
        user.setPoints(userData.getInt("points"));
        sessionService.getSessionManager(this).createLoginSession(user);
        openMainActivity();
    }

    private void showToast(int resource_string_id) {
        showToast(getResources().getString(resource_string_id));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
                .show();
    }

    private void createSessionService() {
        sessionService = new SessionService();
        if(sessionService.getSessionManager(this).isLogged()) {
            openMainActivity();
        }
    }

    private void createHashProvider() {
        hashProvider = new SHA512HashProvider();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void createGoogleLoginBtn() {
        googleLoginBtn = findViewById(R.id.btnGoogleLogin);
        googleLoginBtn.setVisibility(View.INVISIBLE);
        googleLoginBtn.setOnClickListener(view -> {

        });
    }

    private void createSwitchToRegisterBtn() {
        switchToRegisterBtn = findViewById(R.id.btnSwitchToRegister);
        switchToRegisterBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        finish();
    }
}