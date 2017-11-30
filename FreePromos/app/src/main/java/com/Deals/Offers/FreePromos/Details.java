package com.Deals.Offers.FreePromos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Details extends AppCompatActivity {

    List<String> myArrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SharedPreferences prefs = getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        String uid= prefs.getString("id", "null");
        String us=prefs.getString("username","null");

        RelativeLayout fl = (RelativeLayout)findViewById(R.id.frame);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.laybelow);
        int wid = getResources().getDisplayMetrics().widthPixels;
        int  hei=getResources().getDisplayMetrics().heightPixels/4;
        int  hei1=(getResources().getDisplayMetrics().heightPixels/2);

        fl.setLayoutParams(new LinearLayout.LayoutParams(wid, hei1));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(wid, hei);
// Align bottom-right, and add bottom-margin
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(30,0,30,30);
        rl.setLayoutParams(params);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg="";
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Log.e("screensize","-"+toastMsg);
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                Log.e("screensize","-"+toastMsg);
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                Log.e("screensize","-"+toastMsg);
                break;
            default:
                toastMsg="nill";
                Log.e("screensize","-"+toastMsg);
        }
        if(toastMsg.equals("Large screen")){
            Log.e("screensize","-"+toastMsg);
            LinearLayout li =(LinearLayout)findViewById(R.id.linear22);
            LinearLayout.LayoutParams p =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(20,0,20,90);

        }
        if(toastMsg.equals("Normal screen")){
            Log.e("screensize","-"+toastMsg);
//            LinearLayout li =(LinearLayout)findViewById(R.id.linear22);
//            LinearLayout.LayoutParams p =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//            p.setMargins(20,0,20,70);

        }


        SharedPreferences prefs1=getSharedPreferences("yourPrefsKey", Context.MODE_PRIVATE);

        de.hdodenhof.circleimageview.CircleImageView imagepro=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imagepro22);
        TextView us2=(TextView) findViewById(R.id.username22);
        TextView nolikes=(TextView) findViewById(R.id.nolikes22);
        TextView uid1=(TextView) findViewById(R.id.uniqueid22);
        TextView email=(TextView) findViewById(R.id.email22);
        TextView phone=(TextView) findViewById(R.id.phone22);
        TextView edit13=(TextView) findViewById(R.id.editing);


        if(!prefs.getString("profileurl","null").equals("null")){
            Glide.with(Details.this)
                    .load(prefs.getString("profileurl","null"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagepro);
            Log.e("in pro pic-","if");
            Log.e("--",prefs.getString("profileurl","null"));
        }
        else{
            Log.e("no pro pic -","else");
            Log.e("else--",prefs.getString("profileurl","null"));
        }
        if(!prefs.getString("username","null").equals("null")){
            us2.setText(prefs.getString("username","null"));
        }
        retrivedata(prefs1);
        nolikes.setText(myArrayList.size()+" posts liked");

        if(!prefs.getString("id","null").equals("null")){
            uid1.setText(prefs.getString("id","null"));
        }

        if(!prefs.getString("email","null").equals("null")){
            email.setText(prefs.getString("email","null"));
        }

        if(!prefs.getString("phone","null").equals("null")){
            phone.setText(prefs.getString("phone","null"));
        }

        edit13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Details.this,EditProfile.class));
            }
        });


       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
    public void retrivedata(SharedPreferences prefs){
        Set<String> set = prefs.getStringSet("yourKey", null);
        if(set!=null){
            myArrayList=new ArrayList<String>(set);
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(
                Details.this,
                MainActivity.class
        );

        startActivity(intent);
    }
}
