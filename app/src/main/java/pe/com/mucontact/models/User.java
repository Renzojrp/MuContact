package pe.com.mucontact.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romer on 12/11/2017.
 */

public class User {
    private String id;
    private String email;
    private String password;
    private String displayName;
    private String userType;
    private String signupDate;
    private String birthDate;
    private String gender;
    private Integer phone;
    private String photo;

    public User() {
    }

    public User(String id, String email, String password, String displayName, String userType, String signupDate, String birthDate, String gender, Integer phone, String photo) {
        this.setId(id);
        this.setEmail(email);
        this.setPassword(password);
        this.setDisplayName(displayName);
        this.setUserType(userType);
        this.setSignupDate(signupDate);
        this.setBirthDate(birthDate);
        this.setGender(gender);
        this.setPhone(phone);
        this.setPhoto(photo);
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
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

    public String getBirthDate() {
        return birthDate;
    }

    public User setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Integer getPhone() {
        return phone;
    }

    public User setPhone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public static User build(JSONObject jsonUser) {
        if(jsonUser == null) return null;
        User user = new User();
        try {
            user.setId(jsonUser.getString("_id"))
                    .setEmail(jsonUser.getString("email"))
                    .setDisplayName(jsonUser.getString("displayName"))
                    .setUserType(jsonUser.getString("userType"))
                    .setSignupDate(jsonUser.getString("signupDate"))
                    .setBirthDate(jsonUser.getString("birthDate"))
                    .setGender(jsonUser.getString("gender"))
                    .setPhone(jsonUser.getInt("phone"))
                    .setPhoto(jsonUser.getString("photo"));
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
