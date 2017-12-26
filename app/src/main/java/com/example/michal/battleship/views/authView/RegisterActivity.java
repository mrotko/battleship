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
import com.android.volley.toolbox.StringRequest;
import com.example.michal.battleship.AppConfig;
import com.example.michal.battleship.AppController;
import com.example.michal.battleship.R;
import com.example.michal.battleship.auth.hash.IHashProvider;
import com.example.michal.battleship.auth.hash.SHA512HashProvider;
import com.example.michal.battleship.model.User;
import com.example.michal.battleship.utils.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

public class RegisterActivity extends AppCompatActivity {

    private EditText customNameEt;

    private EditText emailEt;

    private EditText passwordEt;

    private EditText repeatPasswordEt;

    private Button createAccountBtn;

    private Button registerByGoogleBtn;

    private Button switchToLoginBtn;

    private ProgressBar progressBar;

    private ConstraintLayout registerActivityCl;

    private IHashProvider hashProvder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createRegisterActivityCl();
        createProgressBar();
        createCustomNameEt();
        createEmailEt();
        createPasswordEt();
        createRepeatPasswordEt();
        createAccountBtn();
        createRegisterByGoogleBtn();
        createSwitchToLoginBtn();
        createHashProvider();
    }

    private void createHashProvider() {
        hashProvder = new SHA512HashProvider();
    }

    private void createRegisterActivityCl() {
        registerActivityCl = findViewById(R.id.cl_register_activity);
    }

    private void createProgressBar() {
        progressBar = findViewById(R.id.progressBar);
        hideProgressBar();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void createCustomNameEt() {
        customNameEt = findViewById(R.id.etCustomName);
        customNameEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                if(customNameEt.getText().toString().isEmpty()) {
                    customNameEt.setError(getResources().getString(R.string.error_empty));
                } else {
                    customNameEt.setError(null);
                }
            }
        });
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

    private void createRepeatPasswordEt() {
        repeatPasswordEt = findViewById(R.id.etRepeatPassword);
        repeatPasswordEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                if(!repeatPasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
                    repeatPasswordEt.setError(getResources().getString(R.string.error_repeat_password));
                } else {
                    repeatPasswordEt.setError(null);
                }
            }
        });
    }

    private void createAccountBtn() {
        createAccountBtn = findViewById(R.id.btnCreateAccount);
        createAccountBtn.setOnClickListener(view -> {
            hideKeyboard();
            if(     customNameEt.getError() == null &&
                    emailEt.getError() == null &&
                    passwordEt.getError() == null &&
                    repeatPasswordEt.getError() == null &&
                    !customNameEt.getText().toString().isEmpty() &&
                    !emailEt.getText().toString().isEmpty() &&
                    !passwordEt.getText().toString().isEmpty() &&
                    !repeatPasswordEt.getText().toString().isEmpty()) {
                User user = new User();
                user.setEmail(emailEt.getText().toString());
                user.setCustomName(customNameEt.getText().toString());
                user.setGoogleId("");
                user.setHashPass(hashProvder.getHashed(passwordEt.getText().toString()));
                checkRegister(user);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.emptyCredentials), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(registerActivityCl.getWindowToken(), 0);
        }
    }

    private void checkRegister(User user) {
        String tag_register_request = "register_request";
        showProgressBar();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                AppConfig.URL_DATABASE,
                response -> {
                    System.out.println(response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        switch(jsonResponse.getInt("status")) {
                            case 0:
                                openLoginActivity();
                                break;
                            case 1:
                                showToast(R.string.error_email_exists);
                                break;
                            default:
                                showToast(R.string.error_unknown);
                        }
                        hideProgressBar();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.e(tag_register_request, "Register Error: " + error.getMessage());
            showToast(error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", "register");
                params.put("customName", user.getCustomName());
                params.put("email", user.getEmail());
                params.put("hashPass", user.getHashPass());
                params.put("googleId", user.getGoogleId());
                params.put("level", Integer.toString(user.getLevel()));
                params.put("points", Integer.toString(user.getPoints()));

                return params;
            }
        };
        AppController.getInstance().addRequestToQueue(stringRequest, tag_register_request);
    }

    private void showToast(int resource_string_id) {
        showToast(getResources().getString(resource_string_id));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
                .show();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createRegisterByGoogleBtn() {
        registerByGoogleBtn = findViewById(R.id.btnCreateAccountByGoogle);
        registerByGoogleBtn.setOnClickListener(view -> {

        });

    }

    private void createSwitchToLoginBtn() {
        switchToLoginBtn = findViewById(R.id.btnSwitchToLogin);
        switchToLoginBtn.setOnClickListener(view -> finish());
    }
}
