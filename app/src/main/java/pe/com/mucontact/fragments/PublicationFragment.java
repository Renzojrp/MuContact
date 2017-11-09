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

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.adapters.PublicationsAdapter;
import pe.com.mucontact.models.Instrument;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.Publication;
import pe.com.mucontact.network.MuContactApiService;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicationFragment extends Fragment {
    private RecyclerView publicationsRecyclerView;
    private PublicationsAdapter publicationsAdapter;
    private RecyclerView.LayoutManager publicationsLayoutManager;
    private List<Publication> publications;
    private static String TAG = "MuContact";
    private Musician musician;
    private Instrument instrument;


    public PublicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publication, container, false);
        publicationsRecyclerView = (RecyclerView) view.findViewById(R.id.publicationRecyclerView);
        publications = new ArrayList<>();
        publicationsAdapter = (new PublicationsAdapter()).setPublications(publications);
        publicationsLayoutManager = new LinearLayoutManager(view.getContext());
        publicationsRecyclerView.setAdapter(publicationsAdapter);
        publicationsRecyclerView.setLayoutManager(publicationsLayoutManager);
        musician = MuContactApp.getInstance().getCurrentMusician();
        updatePublications();
        return view;
    }

    private void updatePublications() {
       AndroidNetworking
                .get(MuContactApiService.PUBLICATION_USER_URL)
                .addPathParameter("musician_id", musician.getId())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response == null) return;
                        try {
                            publications = Publication.build(response.getJSONArray("publications"), instrument, musician);
                            Log.d(TAG, "Found Publications: " + String.valueOf(publications.size()));
                            publicationsAdapter.setPublications(publications);
                            publicationsAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        updatePublications();
    }
}