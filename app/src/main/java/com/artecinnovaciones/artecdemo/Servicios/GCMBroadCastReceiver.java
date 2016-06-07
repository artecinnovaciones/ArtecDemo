package com.artecinnovaciones.artecdemo.Servicios;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Geovany.Chin on 03/06/2016.
 */
public class GCMBroadCastReceiver extends WakefulBroadcastReceiver {

    private String TAG = "Broadcast receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),GCMService.class.getName());

        startWakefulService(context, (intent.setComponent(comp)));

        setResultCode(Activity.RESULT_OK);

        Log.i(TAG, "Notification receive");
    }
}