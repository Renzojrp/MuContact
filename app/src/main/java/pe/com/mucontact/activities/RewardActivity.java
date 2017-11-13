package pe.com.mucontact.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.adapters.RewardsAdapter;
import pe.com.mucontact.models.Reward;
import pe.com.mucontact.network.MuContactApiService;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class RewardActivity extends AppCompatActivity {
    List<Reward> rewards;
    private static String TAG = "MuContact";
    RecyclerView rewardsRecyclerView;
    RewardsAdapter rewardsAdapter;
    RecyclerView.LayoutManager rewardsLayoutManager;
    private int spanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rewardsRecyclerView = (RecyclerView) findViewById(R.id.rewardsRecyclerView);
        rewards = new ArrayList<>();
        rewardsAdapter = new RewardsAdapter(rewards);

        spanCount = getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3;
        rewardsLayoutManager = new GridLayoutManager(this , spanCount);
        rewardsRecyclerView.setAdapter(rewardsAdapter);
        rewardsRecyclerView.setLayoutManager(rewardsLayoutManager);
        updateRewards();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        spanCount = newConfig.orientation == ORIENTATION_PORTRAIT ? 2 : 3;
        ((GridLayoutManager)rewardsLayoutManager).setSpanCount(spanCount);
    }

    private void updateRewards() {
        AndroidNetworking
                .get(MuContactApiService.REWARD_URL)
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null) return;
                        try {
                            rewards = Reward.build(response.getJSONArray("rewards"));
                            Log.d(TAG, "Found Rewards: " + String.valueOf(rewards.size()));
                            rewardsAdapter.setRewards(rewards);
                            rewardsAdapter.notifyDataSetChanged();
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

}
