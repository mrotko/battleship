package pl.rotkomichal.battleship.views.authView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import pl.rotkomichal.battleship.AppConfig;
import pl.rotkomichal.battleship.AppController;
import pl.rotkomichal.battleship.R;
import pl.rotkomichal.battleship.auth.hash.IHashProvider;
import pl.rotkomichal.battleship.auth.hash.SHA512HashProvider;
import pl.rotkomichal.battleship.model.User;
import pl.rotkomichal.battleship.utils.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.sv_register_activity)
    ScrollView registerActivitySv;

    @BindView(R.id.etCustomName)
    EditText customNameEt;

    @BindView(R.id.etEmail)
    EditText emailEt;

    @BindView(R.id.etPassword)
    EditText passwordEt;

    @BindView(R.id.etRepeatPassword)
    EditText repeatPasswordEt;

    @BindView(R.id.btnCreateAccount)
    Button createAccountBtn;

    @BindView(R.id.btnCreateAccountByGoogle)
    Button registerByGoogleBtn;

    @BindView(R.id.btnSwitchToLogin)
    Button switchToLoginBtn;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private IHashProvider hashProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        configureProgressBar();
        configureCustomNameEt();
        configureEmailEt();
        configurePasswordEt();
        configureRepeatPasswordEt();
        configureAccountBtn();
        configureRegisterByGoogleBtn();
        configureSwitchToLoginBtn();
        configureHashProvider();
    }

    private void configureHashProvider() {
        hashProvider = new SHA512HashProvider();
    }

    private void configureProgressBar() {
        hideProgressBar();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void configureCustomNameEt() {
        customNameEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                validateCustomNameEt();
            }
        });
    }

    private boolean validateCustomNameEt() {
        if(customNameEt.getText().toString().isEmpty()) {
            customNameEt.setError(getResources().getString(R.string.error_empty));
            return false;
        } else {
            customNameEt.setError(null);
            return true;
        }
    }

    private void configureEmailEt() {
        emailEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                validateEmailEt();
            }
        });
    }

    private boolean validateEmailEt() {
        if(!Patterns.EMAIL.matcher(emailEt.getText().toString()).matches()) {
            emailEt.setError(getResources().getString(R.string.error_email));
            return false;
        } else {
            emailEt.setError(null);
            return true;
        }
    }

    private void configurePasswordEt() {
        passwordEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                validatePasswordEt();
            }
        });
    }

    private boolean validatePasswordEt() {
        if(!Patterns.PASSWORD.matcher(passwordEt.getText().toString()).matches()) {
            passwordEt.setError(getResources().getString(R.string.error_password));
            return false;
        } else {
            passwordEt.setError(null);
            return true;
        }
    }

    private void configureRepeatPasswordEt() {
        repeatPasswordEt.setOnFocusChangeListener((view, isFocus) -> {
            if(!isFocus) {
                validateRepeatPasswordEt();
            }
        });
    }

    private boolean validateRepeatPasswordEt() {
        if(!repeatPasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
            repeatPasswordEt.setError(getResources().getString(R.string.error_repeat_password));
            return false;
        } else {
            repeatPasswordEt.setError(null);
            return true;
        }
    }

    private void configureAccountBtn() {
        createAccountBtn.setOnClickListener(view -> {
            hideKeyboard();
            if(     validateCustomNameEt() &&
                    validateEmailEt() &&
                    validatePasswordEt() &&
                    validateRepeatPasswordEt()) {
                User user = new User();
                user.setEmail(emailEt.getText().toString());
                user.setCustomName(customNameEt.getText().toString());
                user.setGoogleId("");
                user.setHashPass(hashProvider.getHashed(passwordEt.getText().toString()));
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
            inputMethodManager.hideSoftInputFromWindow(registerActivitySv.getWindowToken(), 0);
        }
    }

    private void checkRegister(User user) {
        String tag_register_request = "register_request";
        showProgressBar();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                AppConfig.URL_DATABASE,
                createStringResponseListener(),
                createErrorListener(tag_register_request)) {
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

    @NonNull
    private Response.ErrorListener createErrorListener(String tag_register_request) {
        return error -> {
                    Log.e(tag_register_request, "Register Error: " + error.getMessage());
                    showToast(error.getMessage());
                };
    }

    @NonNull
    private Response.Listener<String> createStringResponseListener() {
        return response -> {
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
        };
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

    private void configureRegisterByGoogleBtn() {
        registerByGoogleBtn.setOnClickListener(view -> {

        });

    }

    private void configureSwitchToLoginBtn() {
        switchToLoginBtn.setOnClickListener(view -> finish());
    }
}
