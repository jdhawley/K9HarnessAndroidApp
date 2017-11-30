package com.example.android.k9harnessandroidapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public SQLiteHelper myDB;
    public int val = 0;

    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            TextView txt = (TextView) findViewById(R.id.title_text);
            txt.setText(""+val);
            ++val;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new SQLiteHelper(this);
        myDB.beginSession(1); //test dog and test handler input
        startReceivingData();
    }

    public void goToSettingsMenu(View view) {
        Intent goToSettingsMenuIntent = new Intent(this, SettingsTypeMenu.class);
        startActivity(goToSettingsMenuIntent);
    }


    public void startReceivingData() {
        DataProcessingRunnable r = new DataProcessingRunnable();
        r.initRunnable(myDB,handler);
        Thread inputDataThread = new Thread(r);
        inputDataThread.start();
    }



}


