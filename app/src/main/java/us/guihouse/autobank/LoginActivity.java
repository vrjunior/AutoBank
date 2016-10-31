package us.guihouse.autobank;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import us.guihouse.autobank.bean.Login;
import us.guihouse.autobank.bean.Session;
import us.guihouse.autobank.fetchers.BasicFetcher;
import us.guihouse.autobank.http.LoginRequest;

import static us.guihouse.autobank.http.Constants.SHARED_PREFS_FILE;
import static us.guihouse.autobank.http.Constants.SHARED_PREFS_TOKEN;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Login loginAttempt;
    private SharedPreferences sharedPrefe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        sharedPrefe = getSharedPreferences(SHARED_PREFS_FILE, MODE_PRIVATE);
        loginAttempt = new Login();
        if(isTokenExists()) {
            this.redirectToCardActivity();
        }
    }


    private boolean isTokenExists() {
        if(sharedPrefe.getString(SHARED_PREFS_TOKEN, "-1").equals("-1")){
            return false;
        }
        return true;
    }

    private void redirectToCardActivity() {
        Intent openCardActivity = new Intent(LoginActivity.this, CardActivity.class);
        startActivity(openCardActivity);
    }

    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        loginAttempt.setEmail(mEmailView.getText().toString());
        loginAttempt.setPassword(mPasswordView.getText().toString());

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(loginAttempt.getPassword()) && !isPasswordValid(loginAttempt.getPassword())) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(loginAttempt.getEmail())) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(loginAttempt.getEmail())) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            this.performLogin();
        }
    }

    private void performLogin() {
        LoginRequest loginRequest = new LoginRequest(this.loginAttempt);

        BasicFetcher<LoginRequest, Session> loginFetcher = new BasicFetcher<>(new BasicFetcher.FetchCallback<Session>() {
            @Override
            public void onSuccess(Session data) {
                //updating sharedPreference
                SharedPreferences.Editor editor = sharedPrefe.edit();
                editor.putString(SHARED_PREFS_TOKEN, data.getToken());
                editor.commit();

                redirectToCardActivity();
            }

            @Override
            public void onNoConnection() {
                showProgress(false);
                Toast.makeText(LoginActivity.this, "Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                showProgress(false);
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNoAuthentication() {
                showProgress(false);
                mEmailView.setError(getString(R.string.email_or_password_invalid));
            }
        });
        loginFetcher.execute(loginRequest);

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

