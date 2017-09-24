package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Franklin on 26/07/2017.
 */

public class Craftman{
    private String _id;
    private String level;
    private String phone;
    private String description;
    private User user;


    public Craftman() {
    }

    public Craftman(String _id, String level, String phone, String description, User user) {
        this._id = _id;
        this.phone = level;
        this.level = phone;
        this.description = description;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public Craftman set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Craftman setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public Craftman setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Craftman setDescription(String description) {
        this.description = description;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Craftman setUser(User user) {
        this.user = user;
        return this;
    }

    public static Craftman build(JSONObject jsonCraftman, User user) {
        if(jsonCraftman == null) return null;
        Craftman craftman = new Craftman();
        try {
            if( user == null){
                craftman.set_id(jsonCraftman.getString("_id"))
                        .setLevel(jsonCraftman.getString("level"))
                        .setPhone(jsonCraftman.getString("phone"))
                        .setDescription(jsonCraftman.getString("description"))
                        .setUser(user.build(jsonCraftman.getJSONObject("user")));
            } else {
                craftman.set_id(jsonCraftman.getString("_id"))
                        .setLevel(jsonCraftman.getString("level"))
                        .setPhone(jsonCraftman.getString("phone"))
                        .setDescription(jsonCraftman.getString("description"))
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
