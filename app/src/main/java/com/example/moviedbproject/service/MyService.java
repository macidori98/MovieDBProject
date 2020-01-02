package com.example.moviedbproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();
    private IBinder iBinder = new MyBinder();
    private Chronometer mChronometer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(TAG, "in onRebind");
        super.onRebind(intent);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChronometer.stop();
    }

    public String getTimestamp() {
        long elapsedMillis = SystemClock.elapsedRealtime()
                - mChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);
        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
