package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 17/9/2017.
 */

public class Musician {
    private String _id;
    private String birthDate;
    private String gender;
    private String phone;
    private String photo;
    private Double points;
    private User user;

    public Musician() {
    }

    public Musician(String _id, String birthDate, String gender, String phone, String photo, Double points, User user) {
        this._id = _id;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.photo = photo;
        this.points = points;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public Musician set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Musician setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Musician setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Musician setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public Musician setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public Double getPoints() {
        return points;
    }

    public Musician setPoints(Double points) {
        this.points = points;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Musician setUser(User user) {
        this.user = user;
        return this;
    }

    public static Musician build(JSONObject jsonMusician, User user) {
        if(jsonMusician == null) return null;
        Musician musician = new Musician();
        try {
            musician.set_id(jsonMusician.getString("_id"))
                    .setBirthDate(jsonMusician.getString("birthDate"))
                    .setGender(jsonMusician.getString("gender"))
                    .setPhone(jsonMusician.getString("phone"))
                    .setPhoto(jsonMusician.getString("photo"))
                    .setUser(user);
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