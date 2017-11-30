package com.Deals.Offers.FreePromos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Priya on 05-06-2017.
 */
public class chatadapter extends ArrayAdapter<HashMap<String,String>> {

    // ArrayList<HashMap<String,String>> co=new ArrayList<>();
    private String name;
    private FirebaseAuth mAuth;
    Context t;
    chatadapter(Activity context, ArrayList<HashMap<String,String>> a, String name){
        super(context,0,a);
        //co=b;
        t=context;
        this.name=name;
    }
//
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View l=convertView;
       final HashMap<String,String> msgmsg=getItem(position);
        mAuth = FirebaseAuth.getInstance();
        int flag=0;
        final FirebaseUser user = mAuth.getCurrentUser();
        if(msgmsg.get("message")!=null){
            String po="";
            try{
                po=user.getUid().substring(0,7);
            }
            catch (Exception e){

            }

            if((msgmsg.get("id")).equals(po)){
                l = LayoutInflater.from(getContext()).inflate(R.layout.activity_rightpanel, parent, false);
                flag=0;
            }
            else{
                l = LayoutInflater.from(getContext()).inflate(R.layout.activity_leftpanel, parent, false);
                flag=1;
            }
            TextView x=(TextView) l.findViewById(R.id.username);
            TextView y=(TextView) l.findViewById(R.id.msg);
            TextView z=(TextView) l.findViewById(R.id.timing);

            int wid12 = t.getResources().getDisplayMetrics().widthPixels;
            int hei=t.getResources().getDisplayMetrics().heightPixels;
            //kvb.setLayoutParams(new FrameLayout.LayoutParams(width, hei));



            y.setMaxWidth((wid12*3)/5);
            x.setText(msgmsg.get("username"));
            y.setText(msgmsg.get("message"));
            z.setText(msgmsg.get("time"));

            String adminname =t.getResources().getString(R.string.admin);

            if(msgmsg.get("username").equalsIgnoreCase((adminname))){
                x.setTextColor(Color.parseColor("#7B1FA2"));
            }
            else if(msgmsg.get("username").charAt(0)<='f' || msgmsg.get("username").charAt(0)<='F'){
                x.setTextColor(Color.parseColor("#007102"));
            }
            else if(msgmsg.get("username").charAt(0)>'f' || msgmsg.get("username").charAt(0)>'F' ||
                    msgmsg.get("username").charAt(0)<='m' || msgmsg.get("username").charAt(0)<='M'){
                x.setTextColor(Color.parseColor("#3A0071"));
            }
            else if(msgmsg.get("username").charAt(0)>'m' || msgmsg.get("username").charAt(0)>'M' ||
                    msgmsg.get("username").charAt(0)<='s' || msgmsg.get("username").charAt(0)<='S'){
                x.setTextColor(Color.parseColor("#CBA900"));
            }
            else{
                x.setTextColor(Color.parseColor("#009076"));
            }





            if(msgmsg.get("username").equals((adminname))) {
                Linkify.addLinks(y, Linkify.WEB_URLS);
                y.setLinksClickable(true);
                if(flag==0){
                    y.setLinkTextColor(Color.parseColor("#FFFFFF"));
                }
                else{
                    y.setLinkTextColor(Color.parseColor("#5456DE"));
                }
            }
        }
        else{
            if(msgmsg.get("id").equals(user.getUid().substring(0,7))){
                l = LayoutInflater.from(getContext()).inflate(R.layout.rightimagepanel, parent, false);
            }
            else{
                l = LayoutInflater.from(getContext()).inflate(R.layout.leftimagepanel, parent, false);
            }
            TextView x=(TextView) l.findViewById(R.id.username);
            final ImageView y=(ImageView) l.findViewById(R.id.imgmsg);
            TextView z=(TextView) l.findViewById(R.id.timing);


            /*Picasso.with(t)
                    .load(msgmsg.get("image"))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(y, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("success","--done");
                        }
                        @Override
                        public void onError() {
                            Log.e("failure--","nope");
                            Picasso.with(t)
                                    .load(msgmsg.get("image"))
                                    .into(y, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });
*/
            Glide.with(t)
                    .load(msgmsg.get("image"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(y);

            String adminname =t.getResources().getString(R.string.admin);

            if(msgmsg.get("username").equalsIgnoreCase((adminname))){
                x.setTextColor(Color.parseColor("#7B1FA2"));
            }
            else if(msgmsg.get("username").charAt(0)<='f' || msgmsg.get("username").charAt(0)<='F'){
                x.setTextColor(Color.parseColor("#007102"));
            }
            else if(msgmsg.get("username").charAt(0)>'f' || msgmsg.get("username").charAt(0)>'F' ||
                    msgmsg.get("username").charAt(0)<='m' || msgmsg.get("username").charAt(0)<='M'){
                x.setTextColor(Color.parseColor("#3A0071"));
            }
            else if(msgmsg.get("username").charAt(0)>'m' || msgmsg.get("username").charAt(0)>'M' ||
                    msgmsg.get("username").charAt(0)<='s' || msgmsg.get("username").charAt(0)<='S'){
                x.setTextColor(Color.parseColor("#CBA900"));
            }
            else{
                x.setTextColor(Color.parseColor("#009076"));
            }

            x.setText(msgmsg.get("username"));
           // y.setImage(msgmsg.get("message"));
            z.setText(msgmsg.get("time"));
        }



        return l;

    }
}
