package pe.com.mucontact.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import pe.com.mucontact.R;
import pe.com.mucontact.network.MuContactApiService;

public class RegisterActivity extends AppCompatActivity {    EditText displayNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    TextView signInTextView;
    Button registerButton;
    ProgressBar loginProgressBar;
    private static String TAG = "MuContact";

    boolean correctEmail = false;
    boolean correctPassword= false;
    boolean correctDisplayName = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        displayNameEditText = (EditText) findViewById(R.id.nameTextInputEditText);
        emailEditText = (EditText) findViewById(R.id.emailTextInputEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordInputEditText);
        signInTextView = (TextView) findViewById(R.id.signInTextView);
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        loginProgressBar.setVisibility(View.GONE);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    public void registerClick(View v) {
        loginProgressBar.setVisibility(View.VISIBLE);
        if(!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            emailEditText.setError(getResources().getString(R.string.invalid_email));
            correctEmail = false;
            loginProgressBar.setVisibility(View.INVISIBLE);
        } else {
            emailEditText.setError(null);
            correctEmail = true;
        }

        if(displayNameEditText.getText().toString().length() == 0){
            displayNameEditText.setError(getResources().getString(R.string.invalid_displayName));
            correctDisplayName = false;
            loginProgressBar.setVisibility(View.INVISIBLE);
        } else {
            displayNameEditText.setError(null);
            correctDisplayName = true;
        }

        if(passwordEditText.getText().toString().length() == 0) {
            passwordEditText.setError(getResources().getString(R.string.invalid_password));
            correctPassword = false;
            loginProgressBar.setVisibility(View.INVISIBLE);
        } else {
            passwordEditText.setError(null);
            correctPassword = true;
        }
        if(correctEmail && correctPassword && correctDisplayName) {
            registerUser();
        }
    }

    private void registerUser() {
        AndroidNetworking.post(MuContactApiService.SIGNUP_URL)
                .addBodyParameter("email", emailEditText.getText().toString())
                .addBodyParameter("displayName", displayNameEditText.getText().toString())
                .addBodyParameter("password",passwordEditText.getText().toString())
                .addBodyParameter("userType", "Musician")
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), R.string.user_saved, Toast.LENGTH_SHORT).show();
                        loginProgressBar.setVisibility(View.INVISIBLE);
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_user_saved, Toast.LENGTH_SHORT).show();
                        loginProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void goToLoginActivity(View v) {
        loginProgressBar.setVisibility(View.VISIBLE);
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }
}
