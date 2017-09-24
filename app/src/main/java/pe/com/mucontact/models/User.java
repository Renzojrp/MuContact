package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 17/9/2017.
 */

public class User {
    private String _id;
    private String email;
    private String password;
    private String displayName;
    private String userType;
    private String signupDate;

    public User() {
    }

    public User(String _id, String email, String password, String displayName, String userType, String signupDate) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.userType = userType;
        this.signupDate = signupDate;
    }

    public String get_id() {
        return _id;
    }

    public User set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public User setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public User setSignupDate(String signupDate) {
        this.signupDate = signupDate;
        return this;
    }

    public static User build(JSONObject jsonUser) {
        if(jsonUser == null) return null;
        User user = new User();
        try {
            user.set_id(jsonUser.getString("_id"))
                    .setEmail(jsonUser.getString("email"))
                    .setDisplayName(jsonUser.getString("displayName"))
                    .setUserType(jsonUser.getString("userType"))
                    .setSignupDate(jsonUser.getString("signupDate"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> build(JSONArray jsonUsers) {
        if(jsonUsers == null) return null;
        int length = jsonUsers.length();
        List<User> users = new ArrayList<>();
        for(int i = 0; i < length; i++)
            try {
                users.add(User.build(jsonUsers.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return users;
    }
}
