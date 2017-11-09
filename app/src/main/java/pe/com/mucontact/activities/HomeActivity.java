package pe.com.mucontact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.fragments.ContractFragment;
import pe.com.mucontact.fragments.OrderFragment;
import pe.com.mucontact.fragments.PublicationFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((BottomNavigationView) findViewById(R.id.navigation))
                .setOnNavigationItemSelectedListener(
                        new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                return navigateAccordingTo(item.getItemId());
                            }
                        });
        navigateAccordingTo(R.id.navigation_orders);
    }

    private Fragment getFragmentFor(int id) {
        switch (id) {
            case R.id.navigation_orders: return new OrderFragment();
            case R.id.navigation_publications: return new PublicationFragment();
            case R.id.navigation_contracts: return new ContractFragment();
        }
        return null;
    }

    private boolean navigateAccordingTo(int id) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, getFragmentFor(id))
                    .commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sources, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                startActivity(new Intent(getApplicationContext(), AboutUserActivity.class));
                return true;
            case R.id.action_rewards:
                startActivity(new Intent(getApplicationContext(), RewardActivity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void goToAddPublication(View v) {
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        AddPublicationActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MuContactApp.getInstance().setCurrentPublication(null);
        MuContactApp.getInstance().setCurrentReward(null);
        MuContactApp.getInstance().setCurrentCraftman(null);
    }
}
