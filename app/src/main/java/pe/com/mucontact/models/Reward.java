package pe.com.mucontact.models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 25/7/2017.
 */

public class Reward extends SugarRecord {
    private String _id;
    private String title;
    private String description;
    private String url;
    private Double value;

    public Reward() {
    }

    public Reward(String _id, String title, String description, String url, Double value) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.value = value;
    }

    public String get_id() {
        return _id;
    }

    public Reward set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Reward setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Reward setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Reward setUrl(String url) {
        this.url = url;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public Reward setValue(Double value) {
        this.value = value;
        return this;
    }

    public static Reward build(JSONObject jsonReward) {
        if(jsonReward == null) return null;
        Reward reward = new Reward();
        try {
            reward.set_id(jsonReward.getString("_id"))
                    .setTitle(jsonReward.getString("name"))
                    .setDescription(jsonReward.getString("description"))
                    .setUrl(jsonReward.getString("image"))
                    .setValue(jsonReward.getDouble("value"));
            return reward;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Reward> build(JSONArray jsonRewards) {
        if(jsonRewards == null) return null;
        int length = jsonRewards.length();
        List<Reward> rewards = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                rewards.add(Reward.build(jsonRewards.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return rewards;
    }

}