package com.example.android.k9harnessandroidapp;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by George on 11/23/2017.
 */

public class UserPreferences {
    SharedPreferences pref;

    public UserPreferences(Activity activity) {
        pref = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getDog() {
        return pref.getString("dog", "No Dog Found");
    }

    void setDog(String dog) {
        pref.edit().putString("dog", dog).commit();
    }

    void storeToken(String JWTToken) {
        pref.edit().putString("JWTToken", JWTToken).commit();
    }
}
