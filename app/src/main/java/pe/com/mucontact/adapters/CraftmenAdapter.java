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
import pe.com.mucontact.activities.AboutCraftmanActivity;
import pe.com.mucontact.models.Craftman;

/**
 * Created by Franklin on 26/07/2017.
 */

public class CraftmenAdapter extends RecyclerView.Adapter<CraftmenAdapter.ViewHolder>{
    private List<Craftman> craftmen;

    public CraftmenAdapter(List<Craftman> craftmen){this.craftmen = craftmen;}

    public CraftmenAdapter(){}

    @Override
    public CraftmenAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.content_craftman, parent,false));
    }

    @Override
    public void onBindViewHolder(
            CraftmenAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int  position) {
        holder.nameTextView.setText(craftmen.get(position).getUser().getDisplayName());
        holder.descriptionTextView.setText(craftmen.get(position).getDescription());
        holder.photoANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.photoANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        //holder.logoANImageView.setImageUrl(sources.get(position).getUrlToSmallLogo());
        holder.craftmanConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MuContactApp.getInstance().setCurrentCraftman(craftmen.get(position));
                v.getContext()
                        .startActivity(new Intent(v.getContext(),
                                AboutCraftmanActivity.class));
            }
        });
    }

    public int getItemCount() {
        return craftmen.size();
    }

    public List<Craftman> getCraftmen() {
        return craftmen;
    }

    public CraftmenAdapter setCraftmen(List<Craftman> craftman) {
        this.craftmen = craftman;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ANImageView photoANImageView;
        ConstraintLayout craftmanConstraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            photoANImageView = (ANImageView) itemView.findViewById(R.id.photoANImageView);
            craftmanConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.craftmanConstraintLayout);
        }
    }
}
