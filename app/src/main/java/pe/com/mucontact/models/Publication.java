package pe.com.mucontact.models;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 26/6/2017.
 */

public class Publication extends SugarRecord{
    private String _id;
    private String instrument;
    private String description;
    private String locationReference;
    private String createdAt;
    private String craftmen;
    private User user;

    public Publication() {
    }

    public Publication(String _id, String instrument, String description, String locationReference, String createdAt, String craftmen, User user) {
        this._id = _id;
        this.instrument = instrument;
        this.description = description;
        this.locationReference = locationReference;
        this.createdAt = createdAt;
        this.craftmen = craftmen;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public Publication set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getInstrument() {
        return instrument;
    }

    public Publication setInstrument(String instrument) {
        this.instrument = instrument;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Publication setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocationReference() {
        return locationReference;
    }

    public Publication setLocationReference(String locationReference) {
        this.locationReference = locationReference;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Publication setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getCraftmen() {
        return craftmen;
    }

    public Publication setCraftmen(String craftmen) {
        this.craftmen = craftmen;
        return this;
    }

    public String getContext() {
        return "On " + getCreatedAt().substring(0,10) +
                (getLocationReference().isEmpty() ? "" :
                        " at " + getLocationReference());
    }

    public static Publication build(JSONObject jsonPublication, User user) {
        if(jsonPublication == null) return null;
        Publication publication = new Publication();
        try {
            publication.set_id(jsonPublication.getString("_id"))
                    .setInstrument(jsonPublication.getString("instrument"))
                    .setDescription(jsonPublication.getString("description"))
                    .setCreatedAt(jsonPublication.getString("date"))
                    .setLocationReference(jsonPublication.getString("locationAt"))
                    .setUser(user);
            return publication;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Publication> build(JSONArray jsonPublications, User user) {
        if(jsonPublications == null) return null;
        int length = jsonPublications.length();
        List<Publication> publications = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                publications.add(Publication.build(jsonPublications.getJSONObject(i), user));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return publications;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
