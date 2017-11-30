package com.Deals.Offers.FreePromos;

import android.app.Activity;
import android.content.Context;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Priya on 03-06-2017.
 */

public class cadptdt extends ArrayAdapter<HashMap<String,String>> {
    private DatabaseReference myReflikes;
    private FirebaseDatabase database;
    private boolean mprocess=false;
    Context t;
    int width1,height1;
    private FirebaseAuth mAuth;
    final ArrayList<String> lik=new ArrayList<>();
    List<String> myArrayList=new ArrayList<String>();

    private StorageReference mStorageRef;


    public cadptdt(Activity context, ArrayList<HashMap<String,String>> a,int w,int h){
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
        mAuth = FirebaseAuth.getInstance();
        if(l==null){
            l = LayoutInflater.from(getContext()).inflate(
                    R.layout.postdisplay, parent, false);
        }

        final ImageView img26 =(ImageView)l.findViewById(R.id.img56);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width1, height1);
        layoutParams.gravity= Gravity.CENTER;
        img26.setLayoutParams(new RelativeLayout.LayoutParams(layoutParams));

        final RelativeLayout rl26=(RelativeLayout)l.findViewById(R.id.relative56);
        rl26.setLayoutParams(new LinearLayout.LayoutParams(width1, height1));

        SharedPreferences prefs=t.getSharedPreferences("yourPrefsKey", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit1=prefs.edit();

        retrivedata(prefs);

        final HashMap<String,String> b=getItem(position);
        ImageView providerimg=(ImageView) l.findViewById(R.id.provider_image56);
        TextView y=(TextView) l.findViewById(R.id.deal56);
        TextView nooo=(TextView) l.findViewById(R.id.nos56);
        TextView title=(TextView) l.findViewById(R.id.title56);
        TextView price3=(TextView) l.findViewById(R.id.pricee56);
        TextView mrp3=(TextView) l.findViewById(R.id.mrp56);
        TextView discount3=(TextView) l.findViewById(R.id.discount56);
        TextView dur=(TextView) l.findViewById(R.id.timer56);
        ImageView rp=(ImageView) l.findViewById(R.id.rpicon56);
        TextView gone=(TextView) l.findViewById(R.id.gonegone);
        TextView purl=(TextView) l.findViewById(R.id.p_url);//provider url
        TextView goneurl=(TextView) l.findViewById(R.id.gonegoneurl);

        mrp3.setPaintFlags(mrp3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //  mStorageRef = FirebaseStorage.getInstance().getReference();
        // StorageReference riversRef = mStorageRef.child("images/s.jpg");
        // https://firebasestorage.googleapis.com/v0/b/testing-b7ff7.appspot.com/o/hey?alt=media&token=0d86fa1d-5d56-4d90-8d58-62dace88afc5


        final ImageButton im=(ImageButton) l.findViewById(R.id.like56);
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
                Log.e("urlurl of pic ",b.get("urlurl"));
                if(b.get("urlurl")!=null){
                    Glide.with(t)
                            .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+b.get("p")+"pr"+"?alt=media&token="+b.get("urlurl"))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(providerimg);
                    purl.setText(b.get("urlurl"));

                }
                else{
                    providerimg.setImageResource(R.drawable.others_icon);
                    purl.setText(b.get(""));
                }
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


        title.setText(b.get("i"));
        y.setText(dealtype1);
        Log.e("try-",b.get("t"));
        gone.setText(b.get("p"));
        goneurl.setText(b.get("urlurl"));
        dur.setText(TimeAgo.counting(Long.parseLong(b.get("t"))));
        //w.setText(b.get("e"));
        if(b.get("r")!=null){
            if(b.get("r").equals("")){
                price3.setText("-");
                mrp3.setText("-");
                discount3.setText("-");
            }
            else{
                Log.e(b.get("r"),"---");
                String pk[]=b.get("r").toString().split(",");
                price3.setText(pk[0]+"");
                mrp3.setText(pk[1]+"");
                discount3.setText(pk[2]+"% Off");
                Log.e(pk[0],"");
                Log.e(pk[1],"");
                Log.e(pk[2],"");
            }

        }
        else{
            price3.setText("-");
            mrp3.setText("-");
            discount3.setText("-");
        }
        nooo.setText(b.get("likes"));

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

