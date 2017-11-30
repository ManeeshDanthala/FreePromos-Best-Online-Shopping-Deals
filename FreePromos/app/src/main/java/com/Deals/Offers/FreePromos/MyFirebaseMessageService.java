package com.Deals.Offers.FreePromos;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by sunny on 6/20/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
//        Log.e("notification---",remoteMessage.getNotification().getBody());
       /* if(remoteMessage.getData().get("admin")!=null){
            if(remoteMessage.getNotification().getTitle()!=null){
                sendnotification(remoteMessage.getData().get("admin"));
            }
            else{
                sendnotification(remoteMessage.getData().get("admin"));
            }
        }
        else{
            if(remoteMessage.getData().get("msgr")!=null){
                sendnotification(remoteMessage.getData().get("msgr"));
            }
        }*/
       if(remoteMessage.getData().get("msgr")!=null){
           sendnotification("Checkout the new deals!!!");
       }
       else{
           sendnotification(remoteMessage.getData().get("description"));
       }


    }
    private void sendnotification(String body){
        Intent intent = new Intent(this, MainActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Log.e("notification arrived","");

        Intent intent2 = new Intent(this, MainActivity.class);
        PendingIntent pIntent2 = PendingIntent.getActivity(this, 0, intent2, 0);
        long[] pattern = {500,500,500,500,500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle("FreePromos")
                .setContentText(body)
                .setSmallIcon(R.drawable.my_logo_sq)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(alarmSound)
                .setVibrate(pattern);

        if(body.equalsIgnoreCase("Checkout the new deals!!!")){
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, n.build());
        }
        else{
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, n.build());
        }

    }
}
