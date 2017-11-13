package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 8/10/2017.
 */

public class Order {
    private String id;
    private Instrument instrument;
    private String description;
    private String date;
    private String locationAt;
    private String status;
    private String deliveryDay;

    public Order() {
    }

    public Order(String id, Instrument instrument, String description, String date, String locationAt, String status, String deliveryDay) {
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

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Order setInstrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Order setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Order setDate(String date) {
        this.date = date;
        return this;
    }

    public String getLocationAt() {
        return locationAt;
    }

    public Order setLocationAt(String locationAt) {
        this.locationAt = locationAt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public Order setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
        return this;
    }

    public String getDeliveryDayFormat() {
        return "For " + getDeliveryDay() +
                (getLocationAt().isEmpty() ? "" :
                        " at " + getLocationAt());
    }

    public static Order build(JSONObject jsonOrder, Instrument instrument, Musician musician) {
        if(jsonOrder == null) return null;
        Order order = new Order();
        try {
            if( instrument == null){
                order.setId(jsonOrder.getString("_id"))
                        .setInstrument(Instrument.build(jsonOrder.getJSONObject("instrument"), musician))
                        .setDescription(jsonOrder.getString("description"))
                        .setDate(jsonOrder.getString("date"))
                        .setLocationAt(jsonOrder.getString("locationAt"))
                        .setStatus(jsonOrder.getString("status"))
                        .setDeliveryDay(jsonOrder.getString("deliveryDay"));
            } else {
                order.setId(jsonOrder.getString("_id"))
                        .setInstrument(instrument)
                        .setDescription(jsonOrder.getString("description"))
                        .setDate(jsonOrder.getString("date"))
                        .setLocationAt(jsonOrder.getString("locationAt"))
                        .setStatus(jsonOrder.getString("status"))
                        .setDeliveryDay(jsonOrder.getString("deliveryDay"));
            }
            return order;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Order> build(JSONArray jsonOrders, Instrument instrument, Musician musician) {
        if(jsonOrders == null) return null;
        int length = jsonOrders.length();
        List<Order> orders = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                orders.add(Order.build(jsonOrders.getJSONObject(i), instrument, musician));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return orders;
    }
}

