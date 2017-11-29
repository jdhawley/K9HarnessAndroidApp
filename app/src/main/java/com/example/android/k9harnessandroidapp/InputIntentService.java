package com.example.android.k9harnessandroidapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by rickflaget on 11/29/17.
 */

public class InputIntentService extends IntentService{

    private static final String TAG = "com.example.android.k9harnessandroidapp";

    public InputIntentService() {
        super("InputIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //this is what the service does
        Log.i(TAG, "The service has now started");
    }

}
