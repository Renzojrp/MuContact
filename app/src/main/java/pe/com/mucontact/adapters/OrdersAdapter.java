package pe.com.mucontact.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.activities.AddPublicationActivity;
import pe.com.mucontact.models.Order;
import pe.com.mucontact.models.Publication;

/**
 * Created by romer on 25/7/2017.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private List<Order> orders;

    public OrdersAdapter() {
    }

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.content_order, parent, false));
    }

    @Override
    public void onBindViewHolder(
            OrdersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.photoOrderANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.photoOrderANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.photoOrderANImageView.setImageUrl("http://imagizer.imageshack.us/v2/320x240q90/922/fShoPj.jpg");
        holder.instrumentTextView.setText(orders.get(position).getInstrument().getInstrument());
        holder.descriptionTextView.setText(orders.get(position).getDescription());
        holder.locationReferenceTextView.setText(orders.get(position).getDeliveryDayFormat());
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public OrdersAdapter setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView photoOrderANImageView;
        TextView instrumentTextView;
        TextView descriptionTextView;
        TextView locationReferenceTextView;
        ConstraintLayout orderConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            photoOrderANImageView = (ANImageView) itemView.findViewById(R.id.photoOrderANImageView);
            instrumentTextView = (TextView) itemView.findViewById(R.id.instrumentTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            locationReferenceTextView = (TextView) itemView.findViewById(R.id.locationReferenceTextView);
            orderConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.orderConstraintLayout);
        }
    }
}