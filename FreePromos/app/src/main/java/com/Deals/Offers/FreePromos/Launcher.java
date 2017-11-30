package com.Deals.Offers.FreePromos;

import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by sunny on 6/22/2017.
 */

public class Launcher extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new AsyncTask<Service, Void, Service>() {

            @Override
            protected Service doInBackground(Service... params) {
                Service service = params[0];
                PackageManager pm = service.getPackageManager();
                try {
                    Intent target = pm.getLaunchIntentForPackage("your.package.id");
                    if (target != null) {
                        service.startActivity(target);
                        synchronized (this) {
                            wait(3000);
                        }
                    } else {
                        throw new ActivityNotFoundException();
                    }
                } catch (ActivityNotFoundException | InterruptedException ignored) {
                }
                return service;
            }

            @Override
            protected void onPostExecute(Service service) {
                service.stopSelf();
            }

        }.execute(this);

        return START_STICKY;
    }
}
