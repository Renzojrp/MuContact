package pe.com.mucontact.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.mucontact.R;
import pe.com.mucontact.adapters.CraftmenAdapter;
import pe.com.mucontact.models.Craftman;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.MuContactApiService;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private RecyclerView homeRecyclerView;
    private CraftmenAdapter craftmanAdapter;
    private RecyclerView.LayoutManager craftmanLayoutManager;
    private List<Craftman> craftmen;
    private static String TAG = "MuContact";
    private User user;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.craftmanRecyclerView);
        craftmen = new ArrayList<>();
        craftmanAdapter = (new CraftmenAdapter()).setCraftmen(craftmen);
        craftmanLayoutManager = new LinearLayoutManager(view.getContext());
        homeRecyclerView.setAdapter(craftmanAdapter);
        homeRecyclerView.setLayoutManager(craftmanLayoutManager);
        updateCraftmen();
        return view;
    }

    private void updateCraftmen() {
        AndroidNetworking
                .get(MuContactApiService.CRAFTMAN_URL)
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