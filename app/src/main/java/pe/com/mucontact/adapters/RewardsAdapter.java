package pe.com.mucontact.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.activities.AboutRewardActivity;
import pe.com.mucontact.models.Reward;

import java.util.List;

/**
 * Created by romer on 25/7/2017.
 */

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder>  {
    private List<Reward> rewards;

    public RewardsAdapter(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public RewardsAdapter() {
    }

    @Override
    public RewardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_reward, parent, false));
    }

    @Override
    public void onBindViewHolder(RewardsAdapter.ViewHolder holder, final int position) {
        holder.titleTextView.setText(rewards.get(position).getTitle());
        holder.pictureANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.pictureANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.pictureANImageView.setImageUrl(rewards.get(position).getUrl());
        holder.rewardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MuContactApp.getInstance().setCurrentReward(rewards.get(position));
                v.getContext().startActivity(new Intent(v.getContext(), AboutRewardActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public RewardsAdapter setRewards(List<Reward> rewards) {
        this.rewards = rewards;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView pictureANImageView;
        TextView titleTextView;
        CardView rewardCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            pictureANImageView = (ANImageView) itemView.findViewById(R.id.pictureANImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            rewardCardView = (CardView) itemView.findViewById(R.id.rewardCardView);
        }
    }
}
