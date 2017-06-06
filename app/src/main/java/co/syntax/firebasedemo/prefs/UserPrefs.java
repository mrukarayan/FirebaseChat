package co.syntax.firebasedemo.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by rukarayan on 23-Jan-17.
 */

public class UserPrefs {
    public static final String USER_TOKEN = "userToken";
    public static final String USER_TOKEN_KEY = "userTokenKey";
    public static final String USER_LOGIN_KEY = "userLoginKey";
    public static final String USER_DEVICE_ID_KEY = "userDeviceIdKey";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;

    public UserPrefs(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(USER_TOKEN, context.MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }

    public void putIsUserLogin(String key, boolean value){
        editor.putBoolean(key, value);
        commit();
    }

    public boolean isUserLogin(String key){
        return prefs.getBoolean(key, false);
    }

    public void putUserDeviceId(String key, String value){
        editor.putString(key, value);
        commit();
    }

    public String getUserDeviceId(String key){
        return prefs.getString(key,"");
    }

    public void putObject(String key, Object object){
        if(object == null){
            throw new IllegalArgumentException("Object not valid");
        }
        if(key.equalsIgnoreCase("") || key == null){
            throw new IllegalArgumentException("Key not valid");
        }
        editor.putString(key, gson.toJson(object));
        commit();
    }

    public <T> T getObject(String key, Class<T> a){
        String json = prefs.getString(key, null);
        if (json == null) {
            return null;
        } else {
            try {
                return gson.fromJson(json, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");
            }
        }
    }

    private void commit(){
        editor.commit();
    }

    private void clear(){
        editor.clear();
    }
}
