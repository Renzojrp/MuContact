package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 4/8/2017.
 */

public class Contract {
    private String _id;
    private Publication publication;
    private Craftman craftman;
    private User user;
    private String state;
    private String date;

    public Contract() {
    }

    public Contract(String _id, Publication publication, Craftman craftman, User user, String state, String date) {
        this._id = _id;
        this.publication = publication;
        this.craftman = craftman;
        this.user = user;
        this.state = state;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public Contract set_id(String _id) {
        this._id = _id;
        return this;
    }

    public Publication getPublication() {
        return publication;
    }

    public Contract setPublication(Publication publication) {
        this.publication = publication;
        return this;
    }

    public Craftman getCraftman() {
        return craftman;
    }

    public Contract setCraftman(Craftman craftman) {
        this.craftman = craftman;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Contract setUser(User user) {
        this.user = user;
        return this;
    }

    public String getState() {
        return state;
    }

    public Contract setState(String state) {
        this.state = state;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Contract setDate(String date) {
        this.date = date;
        return this;
    }

    public static Contract build(JSONObject jsonContract, User user, Publication publication, Craftman craftman) {
        if(jsonContract == null) return null;
        Contract contract = new Contract();
        try {
            contract.setUser(user.build(jsonContract.getJSONObject("user")));
            contract.setCraftman(craftman.build(jsonContract.getJSONObject("craftman") , contract.getUser()));
            contract.setPublication(publication.build(jsonContract.getJSONObject("publication") , user.build(jsonContract.getJSONObject("user"))));
            return contract;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Contract> build(JSONArray jsonContract, User user, Publication publication, Craftman craftman) {
        if(jsonContract == null) return null;
        int length = jsonContract.length();
        List<Contract> contracts = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                contracts.add(Contract.build(jsonContract.getJSONObject(i), user, publication, craftman));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return contracts;
    }
}
