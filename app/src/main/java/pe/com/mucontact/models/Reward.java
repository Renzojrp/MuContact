package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 8/10/2017.
 */

public class Reward {
    private String id;
    private String name;
    private String picture;
    private String description;
    private Integer value;

    public Reward() {
    }

    public Reward(String id, String name, String picture, String description, Integer value) {
        this.setId(id);
        this.setName(name);
        this.setPicture(picture);
        this.setDescription(description);
        this.setValue(value);
    }

    public String getId() {
        return id;
    }

    public Reward setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Reward setName(String name) {
        this.name = name;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public Reward setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Reward setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public Reward setValue(Integer value) {
        this.value = value;
        return this;
    }

    public static Reward build(JSONObject jsonReward) {
        if(jsonReward == null) return null;
        Reward reward = new Reward();
        try {
            reward.setId(jsonReward.getString("_id"))
                    .setName(jsonReward.getString("name"))
                    .setDescription(jsonReward.getString("description"))
                    .setPicture(jsonReward.getString("picture"))
                    .setValue(jsonReward.getInt("value"));
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
