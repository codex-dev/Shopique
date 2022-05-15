package demo.assignment.my_cart.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import demo.assignment.my_cart.ui.screens.CommonActivity;

public class ConnectionStatusService extends Service {

    Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }    private final Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1000 - SystemClock.elapsedRealtime() % 1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(CommonActivity.broadcastStringForAction);
            broadcastIntent.putExtra("online_status", "" + isOnline(ConnectionStatusService.this));
            sendBroadcast(broadcastIntent);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnectedOrConnecting();
    }


}
