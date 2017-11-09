package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 8/10/2017.
 */

public class Musician {
    private String id;
    private User user;
    private Integer points;

    public Musician() {
    }

    public Musician(String id, User user, Integer points) {
        this.setId(id);
        this.setUser(user);
        this.setPoints(points);
    }

    public String getId() {
        return id;
    }

    public Musician setId(String id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Musician setUser(User user) {
        this.user = user;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public Musician setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public static Musician build(JSONObject jsonMusician, User user) {
        if(jsonMusician == null) return null;
        Musician musician = new Musician();
        try {
            musician.setId(jsonMusician.getString("_id"))
                    .setUser(user)
                    .setPoints(jsonMusician.getInt("points"));
            return musician;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Musician> build(JSONArray jsonMusician, User user) {
        if(jsonMusician == null) return null;
        int length = jsonMusician.length();
        List<Musician> musicians = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                musicians.add(Musician.build(jsonMusician.getJSONObject(i), user));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return musicians;
    }
}
