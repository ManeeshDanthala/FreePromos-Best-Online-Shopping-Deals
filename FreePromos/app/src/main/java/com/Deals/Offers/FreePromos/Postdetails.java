package com.Deals.Offers.FreePromos;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class Postdetails extends AppCompatActivity {


    HashMap<String,String> ht;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        ht=(HashMap<String,String>) getIntent().getSerializableExtra("hashmap");
        String split=ht.get("v");
        String dealtype1="",provider1="",category1="";

        //ImageView providerimg =(ImageView)findViewById(R.id.provider_image10);
        //TextView expiry11 =(TextView)findViewById(R.id.expiry10);
        TextView desc11 =(TextView)findViewById(R.id.desc10);
        TextView date11 =(TextView)findViewById(R.id.date10);
        TextView  link11=(TextView)findViewById(R.id.link10);
        TextView  deal11 =(TextView)findViewById(R.id.deal10);
        TextView title11 = (TextView)findViewById(R.id.title10);
        TextView   price11=(TextView)findViewById(R.id.price10);
        TextView exp11=(TextView) findViewById(R.id.expiry17);
        TextView   discount11=(TextView)findViewById(R.id.discount92);
        TextView   mrp11=(TextView)findViewById(R.id.Mrp92);
        TextView   coupon11=(TextView)findViewById(R.id.coupon92);
        ImageView proimg=(ImageView)findViewById(R.id.img10);
        TextView   store11=(TextView)findViewById(R.id.storename10);
        Button activatedeal12=(Button) findViewById(R.id.activatedeal3);
        Button sharebutton12=(Button) findViewById(R.id.sharebutton);
        TextView tc11 = (TextView)findViewById(R.id.tc10);
        TextView tcid11 = (TextView)findViewById(R.id.tcid);

        int wid3 = getResources().getDisplayMetrics().widthPixels;
        int hei3=(getResources().getDisplayMetrics().heightPixels/4);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(wid3, hei3);
        layoutParams.gravity= Gravity.CENTER;
        proimg.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));

        //expiry11.setText(ht.get("e"));
        if(ht.get("d")!=null){
            if(ht.get("d").equals("")){
                desc11.setText("No description available at this moment");
            }
            else{

                desc11.setText(ht.get("d"));
            }

        }
        else{
            TextView did2=(TextView) findViewById(R.id.did);
            did2.setVisibility(View.GONE);
            desc11.setVisibility(View.GONE);
        }
        if(ht.get("l")!=null){
            if(!ht.get("l").equals("")){
                link11.setText(ht.get("l"));
                link11.setPaintFlags(link11.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                Log.e("url--",Uri.parse(ht.get("l"))+"");
                link11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String abc=ht.get("l");
                        if (!abc.startsWith("http://") && !abc.startsWith("https://")){
                            abc = "http://" + abc;
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(abc));
                        startActivity(i);

                    }
                });
            }

        }

        if(ht.get("r")!=null){
            if(ht.get("r").equals("")){
                price11.setText("--");
                mrp11.setText("--");
                discount11.setText("--");
            }
            else{
                Log.e(ht.get("r"),"---");
                String pk[]=ht.get("r").toString().split(",");
                price11.setText(pk[0]+"");
                mrp11.setText(pk[1]+"");
                discount11.setText(pk[2]+"%");
                mrp11.setPaintFlags(mrp11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
        else{
            price11.setText("--");
            mrp11.setText("--");
            discount11.setText("--");
        }
        if(ht.get("c")!=null){
            Log.e(ht.get("c"),"");
            coupon11.setText(ht.get("c"));
        }
        if(ht.get("i")!=null){
            title11.setText(ht.get("i"));
        }

        if(ht.get("t")!=null){
            Date date = new Date(Long.parseLong(ht.get("t")));
            date11.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date));
        }
        if(ht.get("e")!=null){
            exp11.setText(ht.get("e"));
        }
        if (ht.get("a") != null) {
            tc11.setText(ht.get("a"));
            tc11.setPaintFlags(tc11.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            tcid11.setText("T&c");
        }
        else{
            LinearLayout tc13 = (LinearLayout)findViewById(R.id.tclayout);
            tc13.setVisibility(View.GONE);
        }
        tc11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ab=ht.get("a");
                if (!ab.startsWith("http://") && !ab.startsWith("https://")){
                    ab = "http://" + ab;
                }
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(ab));
                startActivity(i);
                startActivity(i);
            }
        });



        //check this whether it is correct...then  run
        Glide.with(getBaseContext())
                .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+ht.get("p")+"?alt=media&token="+ht.get("urlurl"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(proimg);

//        switch(split.charAt(0)+""){
//            case "0":
//                category1="Electronics";
//                break;
//            case "1":
//                category1="Fashion";
//                break;
//            case "2":
//                category1="Appliances";
//                break;
//            case "3":
//                category1="Wallets";
//                break;
//            case "4":
//                category1="Grocery|Pantry";
//                break;
//            case "5":
//                category1="Travel";
//                break;
//            case "6":
//                category1="Others";
//                break;
//        }
        switch(split.charAt(1)+""){
            case "0":
                provider1="Amazon";
                store11.setText(provider1);
                //providerimg.setImageResource(R.drawable.amazon_icon);
                break;
            case "1":
                provider1="Flipkart";
                store11.setText(provider1);
                //providerimg.setImageResource(R.drawable.flipkart_icon);
                break;
            case "2":
                provider1="Myntra";
                store11.setText(provider1);
               // providerimg.setImageResource(R.drawable.myntra_icon);
                break;
            case "3":
                provider1="Paytm";
                store11.setText(provider1);
                //providerimg.setImageResource(R.drawable.paytm_logo);
                break;
            case "4":
                provider1="Mobiwik";
                store11.setText(provider1);
                //providerimg.setImageResource(R.drawable.mobiwik_icon);
                break;
            case "5":
                provider1="Phonepay";
                store11.setText(provider1);
              //  providerimg.setImageResource(R.drawable.phonepe_icon);
                break;
            case "6":
                provider1="Freecharge";
                store11.setText(provider1);
                //providerimg.setImageResource(R.drawable.freecharge_icon);
                break;
            case "7":
                provider1="Uber";
                store11.setText(provider1);
             //   providerimg.setImageResource(R.drawable.uber_icon);
                break;
            case "8":
                provider1="Ola";
                store11.setText(provider1);
           //     providerimg.setImageResource(R.drawable.ola_icon);
                break;
            default:
                provider1="Others";
                if(ht.get("j")!=null){
                    //here j is for provider name
                    store11.setText(ht.get("j"));
                }
                else{
                    store11.setText(provider1);
                }

         //       providerimg.setImageResource(R.drawable.others_icon);
                break;
        }
        switch(split.charAt(2)+""){
            case "0":
                dealtype1="Loot Deal";
                break;
            case "1":
                dealtype1="Steal Deal";
                break;
            case "2":
                dealtype1="Best Deal";
                break;
            case "3":
                dealtype1="Price drop";
                break;
            case "4":
                dealtype1="Flash sale";
                break;
            case "5":
                dealtype1="Others";
                break;
        }
        if(dealtype1!=""){
            deal11.setText(dealtype1);
        }
        activatedeal12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ht.get("l")!=null){
                    String abc=ht.get("l");
                    if (!abc.startsWith("http://") && !abc.startsWith("https://")){
                        abc = "http://" + abc;
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(abc));
                    startActivity(i);
                }
                else{
                    Toast.makeText(Postdetails.this,"No link available",Toast.LENGTH_SHORT).show();
                }

            }
        });
        sharebutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="";
                if(ht.get("i")!=null){
                    s=s+ht.get("i");
                    if(ht.get("r")!=null){
                        String pk[]=ht.get("r").toString().split(",");
                        s=s+"\n"+"\n"+"Deal Price Rs."+pk[0]+" (MRP Rs."+pk[1]+")";
                    }
                    if(ht.get("l")!=null){
                        String abcd=ht.get("l");
                        if (!abcd.startsWith("http://") && !abcd.startsWith("https://")){
                            abcd = "http://" + abcd;
                        }
                        s=s+"\n"+"\n"+"Visit "+abcd;
                    }
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, s);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                else {
                    Toast.makeText(Postdetails.this,"Cant share this deal",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
