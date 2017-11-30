package com.Deals.Offers.FreePromos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class About_Page extends AppCompatActivity {

    TextView fp,sp,tp,frp,dp;

    String first_para,second_para,third_para,fourth_para,developers_para;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__page);

        first_para ="Founded in 2015, FreePromos is a first app ever created by a startup(in 2017) formed by students pursuing engineering. The idea of creating the app took birth from the thought of providing people best possible deals & offers from 100+ online brands including top stores like Flipkart, Amazon, Myntra, MakeMyTrip, Bookmyshow etc.At FreePromos, we help you save money through our comprehensive listing of deals, offers & discounts from top brands and websites.";
        second_para = "Uniqueness of the app is to provide few much beneficial deals rather than providing a bunch of deals with less savings.The feature that makes our app distinctive is groupchat where admin replies to your queries instantly and also provides platform where users can discuss about the deals.";
        third_para = "Moto of the app is quality matters, not quantity.";
        fourth_para = "In case you want to contact us,drop us a mail at admin@freepromos.in";

        fp =(TextView)findViewById(R.id.intro_para);
        sp =(TextView)findViewById(R.id.second_para);
        tp =(TextView)findViewById(R.id.third_para);
        frp =(TextView)findViewById(R.id.queries);

        fp.setText(first_para);
        sp.setText(second_para);
        tp.setText(third_para);
        frp.setText(fourth_para);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
