package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by rickflaget on 12/13/17.
 */

public class LogOut {

    public void end(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("AccountSettings", ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id_token", "null");
        editor.apply();

        SharedPreferences prefz = ctx.getSharedPreferences("runnable_thread", ctx.MODE_PRIVATE);
        SharedPreferences.Editor editorz = prefz.edit();
        editorz.putBoolean("connected", false);
        editorz.apply();
        //SYNC
        //ENSURE THAT THREAD THAT CONNECTS TO BT IS CLOSED.
        Intent i = new Intent(ctx, LoginActivity.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ctx.startActivity(i);

    }
}
