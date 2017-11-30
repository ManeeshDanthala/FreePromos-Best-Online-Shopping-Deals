package com.Deals.Offers.FreePromos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;
import static android.widget.RelativeLayout.TRUE;


/**
 * Created by Priya on 03-06-2017.
 */

public class cadpt extends ArrayAdapter<HashMap<String,String>> {
    private DatabaseReference myReflikes;
    private FirebaseDatabase database;
    private boolean mprocess=false;
    Context t;
    int width1,height1;
    private FirebaseAuth mAuth;
    final ArrayList<String> lik=new ArrayList<>();
    List<String> myArrayList=new ArrayList<String>();

    private StorageReference mStorageRef;


    public cadpt(Activity context, ArrayList<HashMap<String,String>> a,int w,int h){
        super(context,0,a);
        width1=w;
        height1=h;
        t=context;

    }

    @Nullable
    @Override
    public HashMap<String, String> getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View l=convertView;
        View pl=parent;

        mAuth = FirebaseAuth.getInstance();
        if(l==null){
            l = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_lvnew, parent, false);
        }


       final ImageView img26 =(ImageView)l.findViewById(R.id.img25);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(width1, height1);
        layoutParams.addRule(CENTER_IN_PARENT,TRUE);
        img26.setLayoutParams(new RelativeLayout.LayoutParams(layoutParams));

        final RelativeLayout rl26=(RelativeLayout)l.findViewById(R.id.relative);
        LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams(width1, height1);
        layoutParams2.gravity= Gravity.CENTER;
        rl26.setLayoutParams(new LinearLayout.LayoutParams(layoutParams2));

        SharedPreferences prefs=t.getSharedPreferences("yourPrefsKey", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit1=prefs.edit();

        retrivedata(prefs);

        final HashMap<String,String> b=getItem(position);
        LinearLayout ll112 = (LinearLayout) l;
        ImageView providerimg=(ImageView) l.findViewById(R.id.provider_image);
        TextView y=(TextView) l.findViewById(R.id.deal25);
        //TextView w=(TextView) l.findViewById(R.id.expiry12);
        TextView nooo=(TextView) l.findViewById(R.id.nos);
        TextView title=(TextView) l.findViewById(R.id.title25);
        TextView price3=(TextView) l.findViewById(R.id.pricee);
        TextView mrp3=(TextView) l.findViewById(R.id.mrp);
        TextView discount3=(TextView) l.findViewById(R.id.discount);
        TextView dur=(TextView) l.findViewById(R.id.timer);
        ImageView rp=(ImageView) l.findViewById(R.id.rpicon);
        Button viewdeal2=(Button) l.findViewById(R.id.viewdeal);

        mrp3.setPaintFlags(mrp3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

      //  mStorageRef = FirebaseStorage.getInstance().getReference();
       // StorageReference riversRef = mStorageRef.child("images/s.jpg");
        // https://firebasestorage.googleapis.com/v0/b/testing-b7ff7.appspot.com/o/hey?alt=media&token=0d86fa1d-5d56-4d90-8d58-62dace88afc5


        final ImageButton im=(ImageButton) l.findViewById(R.id.like);
        if(myArrayList.contains(b.get("p"))){
            im.setImageResource(R.drawable.likefilledpic);
            im.setTag("R.drawable.likefilldpic");
        }
        else{
            im.setImageResource(R.drawable.likepic);
            im.setTag("R.drawable.likepic");
        }


        database = FirebaseDatabase.getInstance();
        myReflikes = database.getReference("likes");
        myReflikes.keepSynced(true);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton ii = (ImageButton) v;
                String imageName = (String) ii.getTag();
                if(imageName.equals("R.drawable.likepic")){
                    im.setImageResource(R.drawable.likefilledpic);
                    im.setTag("R.drawable.likefilldpic");
                    myReflikes.child(b.get("p")).child(mAuth.getCurrentUser().getUid()).setValue("liked");
                    lik.add(b.get("p"));
                    myArrayList.add(b.get("p"));
                }
                else{
                    im.setTag("R.drawable.likepic");
                    im.setImageResource(R.drawable.likepic);
                    myReflikes.child(b.get("p")).child(mAuth.getCurrentUser().getUid()).removeValue();
                    lik.remove(b.get("p"));
                    myArrayList.remove(b.get("p"));
                }
                storedata(edit1);
            }
        });
        String split=b.get("v");
        String dealtype1="",provider1="",category1="";
        switch(split.charAt(0)+""){
            case "0":
                category1="Electronics";
                break;
            case "1":
                category1="Fashion";
                break;
            case "2":
                category1="Appliances";
                break;
            case "3":
                category1="Recharge";
                break;
            case "4":
                category1="Food";
                break;
            case "5":
                category1="Grocery|Pantry";
                break;
            case "6":
                category1="Movies";
                break;
            case "7":
                category1="Travel";
                break;
            case "8":
                category1="Others";
                break;
        }
        switch(split.charAt(1)+""){
            case "0":
                provider1="Amazon";
                providerimg.setImageResource(R.drawable.amazon_icon);
                break;
            case "1":
                provider1="Flipkart";
                providerimg.setImageResource(R.drawable.flipkart_icon);
                break;
            case "2":
                provider1="Myntra";
                providerimg.setImageResource(R.drawable.myntra_icon);
                break;
            case "3":
                provider1="Paytm";
                providerimg.setImageResource(R.drawable.paytm_logo);
                break;
            case "4":
                provider1="Mobiwik";
                providerimg.setImageResource(R.drawable.mobiwik_icon);
                break;
            case "5":
                provider1="Phonepay";
                providerimg.setImageResource(R.drawable.phonepe_icon);
                break;
            case "6":
                provider1="Freecharge";
                providerimg.setImageResource(R.drawable.freecharge_icon);
                break;
            case "7":
                provider1="Uber";
                providerimg.setImageResource(R.drawable.uber_icon);
                break;
            case "8":
                provider1="Ola";
                providerimg.setImageResource(R.drawable.ola_icon);
                break;
            case "9":
                provider1="Others";
                if(b.get("urlurl")!=null){
                    Glide.with(t)
                            .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+b.get("p")+"pr"+"?alt=media&token="+b.get("urlurl"))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(providerimg);

                }
                else{
                    providerimg.setImageResource(R.drawable.others_icon);
                }
                break;//b.get(q) here q is for provider image url
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

        /////////////////////
        if(b.get("i")!=null) {
            if(b.get("i").equals("")){
                title.setText("title");
            }
            else{
                title.setText(b.get("i"));
            }

        }

        y.setText(dealtype1);
        Log.e("try-",b.get("t"));
        if(b.get("t")!=null) {
            dur.setText(TimeAgo.counting(Long.parseLong(b.get("t"))));
        }
        //w.setText(b.get("e"));
        if(b.get("r")!=null){
            if(b.get("r").equals("")){
                price3.setText("-");
                mrp3.setText("-");
                discount3.setVisibility(View.GONE);
            }
            else{
                Log.e(b.get("r"),"---");
                String pk[]=b.get("r").toString().split(",");
                price3.setText(pk[0]+"");
                mrp3.setText(pk[1]+"");
                discount3.setText(pk[2]+"% Off");
                discount3.setVisibility(View.VISIBLE);
                Log.e(pk[0],"");
                Log.e(pk[1],"");
                Log.e(pk[2],"");
            }

        }
        else{
            price3.setText("-");
            mrp3.setText("-");
            discount3.setVisibility(View.GONE);
        }
        nooo.setText(b.get("likes"));
        ll112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(t, Postdetails.class);
                intent.putExtra("hashmap",b);
                //passing item clicked to next intent use it their to display info u need
                t.startActivity(intent);
            }
        });
        /*
        Picasso.with(t)
                .load(b.get("url"))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("success","--done");
                    }
                    @Override
                    public void onError() {
                        Log.e("failure--","nope");
                        Picasso.with(t)
                                .load(b.get("url"))
                                .error(R.drawable.likefilledpic)
                                .into(img, new Callback() {
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
                .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+b.get("p")+"?alt=media&token="+b.get("urlurl"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img26);
        return l;
    }
    public void retrivedata(SharedPreferences prefs){
        Set<String> set = prefs.getStringSet("yourKey", null);
        if(set!=null){
            myArrayList=new ArrayList<String>(set);
        }
    }
    public void storedata(SharedPreferences.Editor edit1){
        Set<String> set = new HashSet<String>();
        set.addAll(myArrayList);
        edit1.putStringSet("yourKey", set);
        edit1.commit();
    }
   // https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/image.jpg?alt=media&token=a5a6280c-8f49-46d6-b02c-2b7e8af53ca7
}
class TimeAgo {
    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("yr","month","day","hr","min","sec");

    public static String toDuration(long duration) {

        StringBuffer res = new StringBuffer();
        for(int i=0;i< TimeAgo.times.size(); i++) {
            Long current = TimeAgo.times.get(i);
            long temp = duration/current;
            if(temp>0) {
                res.append(temp).append(" ").append( TimeAgo.timesString.get(i) ).append(temp != 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if("".equals(res.toString()))
            return "0 seconds ago";
        else
            return res.toString();
    }
    public static String counting(long a){
        return toDuration(System.currentTimeMillis()-a);
    }
}
