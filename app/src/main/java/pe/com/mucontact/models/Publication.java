package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 8/10/2017.
 */

public class Publication {
    private String id;
    private Instrument instrument;
    private String description;
    private String date;
    private String locationAt;
    private String status;
    private String deliveryDay;

    public Publication() {
    }

    public Publication(String id, Instrument instrument, String description, String date, String locationAt, String status, String deliveryDay) {
        this.setId(id);
        this.setInstrument(instrument);
        this.setDescription(description);
        this.setDate(date);
        this.setLocationAt(locationAt);
        this.setStatus(status);
        this.setDeliveryDay(deliveryDay);
    }

    public String getId() {
        return id;
    }

    public Publication setId(String id) {
        this.id = id;
        return this;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Publication setInstrument(Instrument instrument) {
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

    public String getDate() {
        return date;
    }

    public Publication setDate(String date) {
        this.date = date;
        return this;
    }

    public String getLocationAt() {
        return locationAt;
    }

    public Publication setLocationAt(String locationAt) {
        this.locationAt = locationAt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Publication setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public Publication setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
        return this;
    }

    public String getDeliveryDayFormat() {
        return "For " + getDeliveryDay() +
                (getLocationAt().isEmpty() ? "" :
                        " at " + getLocationAt());
    }

    public static Publication build(JSONObject jsonPublication, Instrument instrument, Musician musician) {
        if(jsonPublication == null) return null;
        Publication publication = new Publication();
        try {
            if( instrument == null){
                publication.setId(jsonPublication.getString("_id"))
                        .setInstrument(Instrument.build(jsonPublication.getJSONObject("instrument"), musician))
                        .setDescription(jsonPublication.getString("description"))
                        .setDate(jsonPublication.getString("date"))
                        .setLocationAt(jsonPublication.getString("locationAt"))
                        .setStatus(jsonPublication.getString("status"))
                        .setDeliveryDay(jsonPublication.getString("deliveryDay"));
            } else {
                publication.setId(jsonPublication.getString("_id"))
                        .setInstrument(instrument)
                        .setDescription(jsonPublication.getString("description"))
                        .setDate(jsonPublication.getString("date"))
                        .setLocationAt(jsonPublication.getString("locationAt"))
                        .setStatus(jsonPublication.getString("status"))
                        .setDeliveryDay(jsonPublication.getString("deliveryDay"));
            }
            return publication;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Publication> build(JSONArray jsonPublications, Instrument instrument, Musician musician) {
        if(jsonPublications == null) return null;
        int length = jsonPublications.length();
        List<Publication> publications = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                publications.add(Publication.build(jsonPublications.getJSONObject(i), instrument, musician));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return publications;
    }
}

