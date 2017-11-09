package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 8/10/2017.
 */

public class Instrument {
    private String id;
    private Musician musician;
    private String instrument;
    private String brand;
    private String model;
    private String picture;
    private String serialNumber;

    public Instrument() {
    }

    public Instrument(String id, Musician musician, String instrument, String brand, String model, String picture, String serialNumber) {
        this.setId(id);
        this.setMusician(musician);
        this.setInstrument(instrument);
        this.setBrand(brand);
        this.setModel(model);
        this.setPicture(picture);
        this.setSerialNumber(serialNumber);
    }

    public String getId() {
        return id;
    }

    public Instrument setId(String id) {
        this.id = id;
        return this;
    }

    public Musician getMusician() {
        return musician;
    }

    public Instrument setMusician(Musician musician) {
        this.musician = musician;
        return this;
    }

    public String getInstrument() {
        return instrument;
    }

    public Instrument setInstrument(String instrument) {
        this.instrument = instrument;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Instrument setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Instrument setModel(String model) {
        this.model = model;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public Instrument setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Instrument setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public static Instrument build(JSONObject jsonInstrument, Musician musician) {
        if(jsonInstrument == null) return null;
        Instrument instrument = new Instrument();
        try {
            instrument.setId(jsonInstrument.getString("_id"))
                    .setMusician(musician)
                    .setInstrument(jsonInstrument.getString("instrument"))
                    .setBrand(jsonInstrument.getString("brand"))
                    .setModel(jsonInstrument.getString("model"))
                    .setPicture(jsonInstrument.getString("picture"))
                    .setSerialNumber(jsonInstrument.getString("serialNumber"));
            return instrument;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Instrument> build(JSONArray jsonInstrument, Musician musician) {
        if(jsonInstrument == null) return null;
        int length = jsonInstrument.length();
        List<Instrument> instruments = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                instruments.add(Instrument.build(jsonInstrument.getJSONObject(i), musician));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return instruments;
    }
}
