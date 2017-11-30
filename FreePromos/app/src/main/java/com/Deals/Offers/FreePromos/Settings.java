package com.Deals.Offers.FreePromos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(
                Settings.this,
                MainActivity.class
        );

        startActivity(intent);
    }
}
