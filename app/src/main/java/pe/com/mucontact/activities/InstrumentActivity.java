package pe.com.mucontact.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import pe.com.mucontact.adapters.InstrumentsAdapter;
import pe.com.mucontact.models.Instrument;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.network.MuContactApiService;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class InstrumentActivity extends AppCompatActivity {
    List<Instrument> instruments;
    private static String TAG = "MuContact";
    RecyclerView instrumentsRecyclerView;
    InstrumentsAdapter instrumentsAdapter;
    RecyclerView.LayoutManager instrumentsLayoutManager;
    private int spanCount = 2;
    Musician musician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        instrumentsRecyclerView = (RecyclerView) findViewById(R.id.instrumentsRecyclerView);
        instruments = new ArrayList<>();
        instrumentsAdapter = new InstrumentsAdapter(instruments);

        spanCount = getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3;
        instrumentsLayoutManager = new GridLayoutManager(this , spanCount);
        instrumentsRecyclerView.setAdapter(instrumentsAdapter);
        instrumentsRecyclerView.setLayoutManager(instrumentsLayoutManager);

        musician = MuContactApp.getInstance().getCurrentMusician();

        updateInstruments();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        spanCount = newConfig.orientation == ORIENTATION_PORTRAIT ? 2 : 3;
        ((GridLayoutManager)instrumentsLayoutManager).setSpanCount(spanCount);
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
                            instrumentsAdapter.setInstruments(instruments);
                            instrumentsAdapter.notifyDataSetChanged();
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

    public void goToAddInstrument(View v) {
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        AddInstrumentActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateInstruments();
        MuContactApp.getInstance().setCurrentInstrument(null);
    }
}
