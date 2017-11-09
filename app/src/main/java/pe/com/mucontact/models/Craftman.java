package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 8/10/2017.
 */

public class Craftman {
    private String id;
    private User user;
    private String description;
    private Float qualification;

    public Craftman() {
    }

    public Craftman(String id, User user, String description, Float qualification) {
        this.setId(id);
        this.setUser(user);
        this.setDescription(description);
        this.setQualification(qualification);
    }

    public String getId() {
        return id;
    }

    public Craftman setId(String id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Craftman setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Craftman setDescription(String description) {
        this.description = description;
        return this;
    }

    public Float getQualification() {
        return qualification;
    }

    public Craftman setQualification(Float qualification) {
        this.qualification = qualification;
        return this;
    }

    public static Craftman build(JSONObject jsonCraftman, User user) {
        if(jsonCraftman == null) return null;
        Craftman craftman = new Craftman();
        try {
            if( user == null){
                craftman.setId(jsonCraftman.getString("_id"))
                        .setDescription(jsonCraftman.getString("description"))
                        .setQualification(Float.parseFloat(jsonCraftman.getString("qualification")))
                        .setUser(User.build(jsonCraftman.getJSONObject("user")));
            } else {
                craftman.setId(jsonCraftman.getString("_id"))
                        .setDescription(jsonCraftman.getString("description"))
                        .setQualification(Float.parseFloat(jsonCraftman.getString("qualification")))
                        .setUser(user);
            }
            return craftman;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Craftman> build(JSONArray jsonCraftman, User user) {
        if(jsonCraftman == null) return null;
        int length = jsonCraftman.length();
        List<Craftman> craftmen = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                craftmen.add(Craftman.build(jsonCraftman.getJSONObject(i), user));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return craftmen;
    }
}
