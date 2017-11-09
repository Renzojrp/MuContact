package pe.com.mucontact.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.Instrument;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.MuContactApiService;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText;
    EditText passwordEditText;
    TextView signUpTextView;
    ProgressBar loginProgressBar;
    Intent intent;

    boolean correctEmail = false;
    boolean correctPassword= false;

    String TAG = "MuContact";

    User user;
    String token;
    Musician musician;
    List<Instrument> instruments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        emailEditText = (EditText) findViewById(R.id.emailTextInputEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordInputEditText);
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        signUpTextView = (TextView) findViewById(R.id.signUpTextView);
        loginProgressBar.setVisibility(View.GONE);
    }

    public void loginClick(View v) {
        loginProgressBar.setVisibility(View.VISIBLE);
        intent = new Intent (v.getContext(), HomeActivity.class);
        if(!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            emailEditText.setError(getResources().getString(R.string.invalid_email));
            correctEmail = false;
            loginProgressBar.setVisibility(View.INVISIBLE);
        } else {
            emailEditText.setError(null);
            correctEmail = true;
        }
        if(passwordEditText.getText().toString().length() == 0) {
            passwordEditText.setError(getResources().getString(R.string.invalid_password));
            correctPassword = false;
            loginProgressBar.setVisibility(View.INVISIBLE);
        } else {
            passwordEditText.setError(null);
            correctPassword = true;
        }
        if(correctEmail && correctPassword) {
            login();
        }
    }

    private void login() {
        AndroidNetworking.post(MuContactApiService.SIGNIN_URL)
                .addBodyParameter("email", emailEditText.getText().toString())
                .addBodyParameter("password", passwordEditText.getText().toString())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null) return;
                        try {
                            user = User.build(response.getJSONObject("user"));
                            if(user.getUserType().equals("Musician")) {
                                user.setPassword(passwordEditText.getText().toString());
                                token = response.getString("token");
                                MuContactApp.getInstance().setCurrentToken(token);
                                MuContactApp.getInstance().setCurrentUser(user);
                                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                getMusicianByUser();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.error_message_login, Toast.LENGTH_SHORT).show();
                                loginProgressBar.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loginProgressBar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.incorrect_login, Toast.LENGTH_SHORT).show();
                        loginProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void getMusicianByUser() {
        AndroidNetworking.get(MuContactApiService.MUSICIAN_USER_URL)
                .addPathParameter("user_id", user.getId())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null) return;
                        try {
                            musician = Musician.build(response.getJSONObject("musician"), user);
                            MuContactApp.getInstance().setCurrentMusician(musician);
                            updateInstruments();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), R.string.error_profile_musician, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void updateInstruments() {
        AndroidNetworking
                .get(MuContactApiService.INSTRUMET_MUSICIAN_URL)
                .addPathParameter("musician_id", musician.getId())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null) return;
                        try {
                            instruments = Instrument.build(response.getJSONArray("instruments"), musician);
                            Log.d(TAG, "Found Instruments: " + String.valueOf(instruments.size()));
                            MuContactApp.getInstance().setCurrentInstruments(instruments);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, anError.getMessage());
                    }
                });
    }

    public void goToRegisterActivity(View v) {
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        RegisterActivity.class));
    }
}
