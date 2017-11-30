package com.Deals.Offers.FreePromos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Priya on 31-05-2017.
 */

//To set flags in preference

public class Prefs {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //mode
    int PRIVATE_MODE = 0;

    // file name
    private static final String PREF_NAME = "spaceo-demo";
    private static final String IS_FIRST_TIME = "IsFirstTime";

    public Prefs(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }
    public void setLoginStatus(boolean isLogged){
        editor.putBoolean("Login_Status",isLogged);
        editor.commit();
    }
    public void setspb(boolean spb){
        editor.putBoolean("spb",spb);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME, true);
    }
    public boolean isLoggedIn() {return pref.getBoolean("Login_Status",false); }
    public boolean isSpb() {return pref.getBoolean("spb",false);}

}