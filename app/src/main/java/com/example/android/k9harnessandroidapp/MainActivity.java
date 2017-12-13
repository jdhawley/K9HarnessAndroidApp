package com.example.android.k9harnessandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public SQLiteHelper myDB;
    public int val = 0;
    private boolean loggedIn = false;

    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            ++val;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Navigation nav = new Navigation();
        myDB = new SQLiteHelper(this);
        buttonFuncs();
       if(!DataProcessingRunnable.isRunning){
           startReceivingData();
       }
        //Notifications notify = new Notifications(this);
        //notify.createNotification("??");
        //goToSettingsMenu();
       nav.goToDogOverview(this);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonFuncs();
    }

    public void buttonFuncs() {
        SharedPreferences prefs = this.getSharedPreferences("runnable_thread", MODE_PRIVATE);
        boolean isRun = prefs.getBoolean("connected",true);
        if (isRun) {
            Button btn = (Button) findViewById(R.id.but_connect);
            btn.setEnabled(false);
            Button btn1 = (Button) findViewById(R.id.but_disconnect);
            btn1.setEnabled(true);
        }
        else {
            Button btn = (Button) findViewById(R.id.but_connect);
            btn.setEnabled(true);
            Button btn1 = (Button) findViewById(R.id.but_disconnect);
            btn1.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void connectToBluetooth(View view) {
        if(!DataProcessingRunnable.isRunning) {
            SharedPreferences prefs = this.getSharedPreferences("runnable_thread", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("connected", true);
            editor.apply();
            startReceivingData();
            Navigation nav = new Navigation();
            nav.goToDogOverview(this);
            //finish();
            buttonFuncs();
        }
    }

    public void disconnectFromBluetooth(View view) {
        SharedPreferences prefs = this.getSharedPreferences("runnable_thread", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("connected", false);
        editor.apply();
        buttonFuncs();
        //display connect button or something
        //must make sure to properly end thread
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Navigation nav = new Navigation();
        //Log.e(TAG,"madeittoslectedpg");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_dog) {
            //Log.e(TAG,"madeITtoDOG");
            nav.goToSettingsDog(this);
            return true;
        } else if (id == R.id.nav_account) {
            nav.goToSettingsAccount(this);
            return true;

        } else if (id == R.id.nav_bluetooth) {
            nav.goToSettingsBluetooth(this);
            return true;

        } else if (id == R.id.nav_notification) {
            nav.goToSettingsNotification(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Navigation nav = new Navigation();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dog_overview) {
            //goToDogOverview();
            //DO NOTHING HERE CAUSE ITS THIS ACTIVITY
            //TODO: CONSIDER SEPERATING XML FILES FOR EACH ACTIVITY
        } else if (id == R.id.nav_heart_rate) {
            nav.goToHeartRateActivity(this);
        } else if (id == R.id.nav_resp_rate) {
            nav.goToRespiratoryRateActivity(this);
        } else if (id == R.id.nav_core_temp) {
            nav.goToCoreTemperatureActivity(this);
        } else if (id == R.id.nav_ab_temp) {
            nav.goToAbdominalTemperatureActivity(this);
        } else if (id == R.id.nav_logOut) {
            //TODO: logout function!

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void startReceivingData() {
        //THIS IS WHERE BLUETOOTH CONNECTION WOULD GO
        //NOT IN PAGE BECAUSE HARDWARE SUPPORT NOT AVAIL
        //CODE FOR THIS CAN BE PULLED NEAR EXACTLY FROM PREVIOUS ECE GROUP CODE.
        //ENSURE DISABLING OF AUTO-GENERATE FUNCTION
        DataProcessingRunnable r = new DataProcessingRunnable(this);
        r.initRunnable(myDB,handler);
        Thread inputDataThread = new Thread(r);
        inputDataThread.start();

        //Intent intent = new Intent(this, InputIntentService.class);
//        intent.putExtra(myDB, handler);
//        startService(intent);
    }


}


