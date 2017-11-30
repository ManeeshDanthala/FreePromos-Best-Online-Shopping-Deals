package com.Deals.Offers.FreePromos;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sunny on 6/20/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN="Reg_Token";
    private String recentToken="";

    @Override
    public void onTokenRefresh() {
        recentToken= FirebaseInstanceId.getInstance().getToken();
        try{
            SharedPreferences myPrefs = this.getSharedPreferences("profiledata", MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putString("token",recentToken);
            editor.commit();
        }
        catch (Exception e){
            Log.e("exp in myfrbinstance-",e.getMessage());
        }

        Log.e(REG_TOKEN,recentToken);
    }
    public String getRecentToken(){
        Log.e(REG_TOKEN+"in_get",recentToken);
        return FirebaseInstanceId.getInstance().getToken();
    }
}
