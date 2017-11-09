package pe.com.mucontact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.models.Craftman;

public class AboutCraftmanActivity extends AppCompatActivity {
    private ANImageView photCraftmanANImageView;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView phoneTextView;
    private RatingBar ratingBar;
    Craftman craftman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_craftman);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        photCraftmanANImageView = (ANImageView) findViewById(R.id.photoCraftmanANImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        craftman = MuContactApp.getInstance().getCurrentCraftman();

        photCraftmanANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        photCraftmanANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        //photCraftmanANImageView.setImageUrl();
        nameTextView.setText(craftman.getUser().getDisplayName());
        descriptionTextView.setText(craftman.getDescription());
        phoneTextView.setText(craftman.getUser().getPhone().toString());
        ratingBar.setRating(craftman.getQualification());
    }

    public void goToAddPublicationActivity(View v) {
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        AddPublicationActivity.class));
    }
}
