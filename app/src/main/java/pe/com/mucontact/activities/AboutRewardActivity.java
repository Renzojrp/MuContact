package pe.com.mucontact.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.Reward;

public class AboutRewardActivity extends AppCompatActivity {
    private ANImageView pictureANImageView;
    private TextView titleTextView;
    private TextView valueTextView;
    private TextView descriptionTextView;
    Reward reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_reward);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pictureANImageView = (ANImageView) findViewById(R.id.pictureANImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        valueTextView = (TextView) findViewById(R.id.valueTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        reward = MuContactApp.getInstance().getCurrentReward();

        titleTextView.setText(reward.getTitle());
        valueTextView.setText(reward.getValue().toString());
        descriptionTextView.setText(reward.getDescription());

        pictureANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        pictureANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        pictureANImageView.setImageUrl(reward.getUrl());
    }

}
