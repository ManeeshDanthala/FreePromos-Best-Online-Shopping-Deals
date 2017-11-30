package com.Deals.Offers.FreePromos;

import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Priya on 31-05-2017.
 */


public class Application extends android.app.Application {
    private Prefs prefs;
    private static Application app;
    private boolean called=false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        prefs = new Prefs(this);
        if(!called){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            called=true;
        }
        startService(new Intent(this, YourService.class));

    }
    public static Application getApp() {
        return app;
    }
    public Prefs getPrefs() {
        return prefs;
    }
    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }
}
