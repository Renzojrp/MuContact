package pe.com.mucontact.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
import pe.com.mucontact.adapters.CraftmenAdapter;
import pe.com.mucontact.models.Craftman;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.MuContactApiService;

public class CraftmanActivity extends AppCompatActivity {
    private RecyclerView craftmanRecyclerView;
    private CraftmenAdapter craftmanAdapter;
    private RecyclerView.LayoutManager craftmanLayoutManager;
    private List<Craftman> craftmen;
    private static String TAG = "MuContact";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craftman);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        craftmanRecyclerView = (RecyclerView) findViewById(R.id.craftmanRecyclerView);
        craftmen = new ArrayList<>();
        craftmanAdapter = new CraftmenAdapter(craftmen);
        craftmanLayoutManager = new LinearLayoutManager(this);
        craftmanRecyclerView.setAdapter(craftmanAdapter);
        craftmanRecyclerView.setLayoutManager(craftmanLayoutManager);

        updateCraftmen();
    }

    private void updateCraftmen() {
        AndroidNetworking
                .get(MuContactApiService.CRAFTMAN_URL)
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response == null) return;
                        try {
                            craftmen = Craftman.build(response.getJSONArray("craftmen"), user);
                            Log.d(TAG, "Found Craftmen: " + String.valueOf(craftmen.size()));
                            craftmanAdapter.setCraftmen(craftmen);
                            craftmanAdapter.notifyDataSetChanged();
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
