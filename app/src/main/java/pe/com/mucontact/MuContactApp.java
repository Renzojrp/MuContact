package pe.com.mucontact;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import pe.com.mucontact.models.Craftman;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.Publication;
import pe.com.mucontact.models.Reward;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.MuContactApiService;

/**
 * Created by romer on 17/9/2017.
 */

public class MuContactApp extends Application{
    private static MuContactApp instance;
    private MuContactApiService muContactApiService;

    public MuContactApp() {
        super();
        instance = this;
    }

    public static MuContactApp getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        muContactApiService = new MuContactApiService();
    }

    public MuContactApp setCurrentUser(User user){
        muContactApiService.setCurrentUser(user);
        return this;
    }

    public User getCurrentUser() {
        return muContactApiService.getCurrentUser();
    }

    public MuContactApp setCurrentToken(String token){
        muContactApiService.setCurrentToken(token);
        return this;
    }

    public String getCurrentToken() {
        return muContactApiService.getCurrentToken();
    }

    public MuContactApp setCurrentMusician(Musician musician){
        muContactApiService.setCurrentMusician(musician);
        return this;
    }

    public Musician getCurrentMusician() {
        return muContactApiService.getCurrentMusician();
    }

    public MuContactApp setCurrentPublication(Publication publication){
        muContactApiService.setCurrentPublication(publication);
        return this;
    }

    public Publication getCurrentPublication() {
        return muContactApiService.getCurrentPublication();
    }

    public MuContactApp setCurrentReward(Reward reward) {
        muContactApiService.setCurrentReward(reward);
        return this;
    }

    public Reward getCurrentReward() {
        return muContactApiService.getCurrentReward();
    }

    public MuContactApp setCurrentCraftman(Craftman craftman){
        muContactApiService.setCurrentCraftman(craftman);
        return this;
    }

    public Craftman getCurrentCraftman() {
        return muContactApiService.getCurrentCraftman();
    }
}
