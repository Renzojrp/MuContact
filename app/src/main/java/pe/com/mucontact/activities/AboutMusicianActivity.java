package pe.com.mucontact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.User;

public class AboutMusicianActivity extends AppCompatActivity {
    private ANImageView photoANImageView;
    private TextView displayNameTextView;
    private TextView emailTextView;
    private TextView userTypeTextView;
    private TextView phoneTextView;
    private TextView genderTextView;
    private TextView birthDateTextView;
    User user;
    Musician musician;
    String TAG = "MuContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_musician);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userInformation();
    }public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile_sources, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                return true;
            case R.id.action_edit_profile:
                startActivity(new Intent(getApplicationContext(), EditMusicianProfileActivity.class));
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void userInformation() {
        user = MuContactApp.getInstance().getCurrentUser();
        musician = MuContactApp.getInstance().getCurrentMusician();

        photoANImageView = (ANImageView) findViewById(R.id.photoANImageView);
        displayNameTextView = (TextView) findViewById(R.id.displayNameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        userTypeTextView = (TextView) findViewById(R.id.userTypeTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        genderTextView = (TextView) findViewById(R.id.genderTextView);
        birthDateTextView = (TextView) findViewById(R.id.birthDateTextView);

        photoANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        photoANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        //photoANImageView.setImageUrl(sources.get(position).getUrlToSmallLogo());
        displayNameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        userTypeTextView.setText(user.getUserType());
        phoneTextView.setText((user.getPhone().toString()));
        genderTextView.setText(user.getGender());
        birthDateTextView.setText(user.getBirthDate());
    }

    public void goToInstrumentActivity(View v) {
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        InstrumentActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        userInformation();
    }
}
