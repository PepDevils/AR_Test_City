package pt.pepdevils.virtualbraga.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

/**
 * Created by pfons on 29/10/2017.
 */

public class PreferencesHelper {

    public static void saveObjectInSharedPref(Context c, Object obj, String tag, int total) {
        try {
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            prefsEditor.putInt("total", total);
            prefsEditor.putString(tag, json);
            prefsEditor.apply();
        } catch (Exception e) {
            Log.d("CacheDatePepeError:", "saveObjectInFileCache: " + e.getMessage());
        }
    }

    //talvez tenha que adaptar para cada tipo de objecto
    public static ArrayList<Object> getObjectInSharedPref(Context c, String tag) {
        ArrayList<Object> notic = new ArrayList<>();
        try {
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(c);
            Gson gson = new Gson();
            int total = appSharedPrefs.getInt("total", 0);
            if (total > 0) {
                for (int i = 0; i < total; i++) {
                    String json = appSharedPrefs.getString(tag + i, "");
                    Object obj = gson.fromJson(json, Object.class);
                    notic.add(obj);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return notic;
    }


}
