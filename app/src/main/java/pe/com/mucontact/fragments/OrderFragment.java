package pe.com.mucontact.fragments;


import android.content.Intent;
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
import pe.com.mucontact.activities.CraftmanActivity;
import pe.com.mucontact.adapters.OrdersAdapter;
import pe.com.mucontact.adapters.PublicationsAdapter;
import pe.com.mucontact.models.Instrument;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.Order;
import pe.com.mucontact.models.Publication;
import pe.com.mucontact.network.MuContactApiService;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private RecyclerView ordersRecyclerView;
    private OrdersAdapter ordersAdapter;
    private RecyclerView.LayoutManager ordersLayoutManager;
    private List<Order> orders;
    private static String TAG = "MuContact";
    private Musician musician;
    private Instrument instrument;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ordersRecyclerView = (RecyclerView) view.findViewById(R.id.orderRecyclerView);
        orders = new ArrayList<>();
        ordersAdapter = (new OrdersAdapter()).setOrders(orders);
        ordersLayoutManager = new LinearLayoutManager(view.getContext());
        ordersRecyclerView.setAdapter(ordersAdapter);
        ordersRecyclerView.setLayoutManager(ordersLayoutManager);
        musician = MuContactApp.getInstance().getCurrentMusician();
        updateOrders();
        return view;
    }

    private void updateOrders() {
        AndroidNetworking
                .get(MuContactApiService.ORDER_USER_URL)
                .addPathParameter("musician_id", musician.getId())
                .addHeaders("Authorization", MuContactApp.getInstance().getCurrentToken())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response == null) return;
                        try {
                            orders = Order.build(response.getJSONArray("orders"), instrument, musician);
                            Log.d(TAG, "Found Publications: " + String.valueOf(orders.size()));
                            ordersAdapter.setOrders(orders);
                            ordersAdapter.notifyDataSetChanged();
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
        updateOrders();
    }

    public void goToCraftmanActivity(View v) {
        v.getContext()
                .startActivity(new Intent(v.getContext(),
                        CraftmanActivity.class));
    }

}
