package pe.com.mucontact.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.activities.AddInstrumentActivity;
import pe.com.mucontact.models.Instrument;

/**
 * Created by romer on 8/10/2017.
 */

public class InstrumentsAdapter extends RecyclerView.Adapter<InstrumentsAdapter.ViewHolder>  {
    private List<Instrument> instruments;

    public InstrumentsAdapter(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public InstrumentsAdapter() {
    }

    @Override
    public InstrumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InstrumentsAdapter.ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_instrument, parent, false));
    }

    @Override
    public void onBindViewHolder(InstrumentsAdapter.ViewHolder holder, final int position) {
        holder.nameTextView.setText(instruments.get(position).getInstrument());
        holder.pictureANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.pictureANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.pictureANImageView.setImageUrl(instruments.get(position).getPicture());
        holder.instrumentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MuContactApp.getInstance().setCurrentInstrument(instruments.get(position));
                v.getContext().startActivity(new Intent(v.getContext(), AddInstrumentActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return instruments.size();
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public InstrumentsAdapter setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView pictureANImageView;
        TextView nameTextView;
        CardView instrumentCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            pictureANImageView = (ANImageView) itemView.findViewById(R.id.pictureANImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            instrumentCardView = (CardView) itemView.findViewById(R.id.instrumentCardView);
        }
    }
}