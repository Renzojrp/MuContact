package pe.com.mucontact.network;

import java.util.List;

import pe.com.mucontact.models.Craftman;
import pe.com.mucontact.models.Instrument;
import pe.com.mucontact.models.Musician;
import pe.com.mucontact.models.Publication;
import pe.com.mucontact.models.Reward;
import pe.com.mucontact.models.User;

/**
 * Created by romer on 8/10/2017.
 */

public class MuContactApiService {
    public static String SIGNIN_URL = "https://mucontact.herokuapp.com/api/signin";
    public static String SIGNUP_URL = "https://mucontact.herokuapp.com/api/signup";
    public static String MUSICIAN_USER_URL = "https://mucontact.herokuapp.com/api/musician/user/{user_id}";
    public static String PUBLICATION_USER_URL = "https://mucontact.herokuapp.com/api/publication/musician/{musician_id}";
    public static String PUBLICATION_URL = "https://mucontact.herokuapp.com/api/publication";
    public static String PUBLICATION_EDIT_URL = "https://mucontact.herokuapp.com/api/publication/{publication_id}";
    public static String USERS_EDIT_URL = "https://mucontact.herokuapp.com/api/user/{user_id}";
    public static String REWARD_URL = "https://mucontact.herokuapp.com/api/reward";
    public static String CRAFTMAN_URL = "https://mucontact.herokuapp.com/api/craftman";
    public static String ORDER_URL = "https://mucontact.herokuapp.com/api/order";
    public static String INSTRUMET_URL = "https://mucontact.herokuapp.com/api/instrument";
    public static String INSTRUMET_MUSICIAN_URL = "https://mucontact.herokuapp.com/api/instrument/musician/{musician_id}";
    public static String INSTRUMET_EDIT_URL = "https://mucontact.herokuapp.com/api/instrument/{instrument_id}";

    private User currentUser;
    private String currentToken;
    private Musician currentMusician;
    private Publication currentPublication;
    private Reward currentReward;
    private Craftman currentCraftman;
    private Instrument currentInstrument;
    private List<Instrument> currentInstruments;

    public User getCurrentUser() {
        return currentUser;
    }

    public MuContactApiService setCurrentUser(User currentUser){
        this.currentUser = currentUser;
        return this;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public MuContactApiService setCurrentToken(String currentToken){
        this.currentToken = currentToken;
        return this;
    }

    public Musician getCurrentMusician() {
        return currentMusician;
    }

    public MuContactApiService setCurrentMusician(Musician currentMusician){
        this.currentMusician = currentMusician;
        return this;
    }

    public Publication getCurrentPublication() {
        return currentPublication;
    }

    public MuContactApiService setCurrentPublication(Publication currentPublication){
        this.currentPublication = currentPublication;
        return this;
    }

    public Reward getCurrentReward() {
        return currentReward;
    }

    public MuContactApiService setCurrentReward(Reward currentReward) {
        this.currentReward = currentReward;
        return this;
    }

    public Craftman getCurrentCraftman() {
        return currentCraftman;
    }

    public MuContactApiService setCurrentCraftman(Craftman currentCraftman){
        this.currentCraftman = currentCraftman;
        return this;
    }

    public Instrument getCurrentInstrument() {
        return currentInstrument;
    }

    public MuContactApiService setCurrentInstrument(Instrument currentInstrument){
        this.currentInstrument = currentInstrument;
        return this;
    }

    public List<Instrument> getCurrentInstruments() {
        return currentInstruments;
    }

    public MuContactApiService setCurrentInstruments(List<Instrument> currentInstruments){
        this.currentInstruments = currentInstruments;
        return this;
    }
}
