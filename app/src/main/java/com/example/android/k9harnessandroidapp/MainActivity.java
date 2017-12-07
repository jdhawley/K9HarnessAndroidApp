package com.example.android.k9harnessandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
            TextView txt = (TextView) findViewById(R.id.title_text);
            txt.setText(""+val);
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

        myDB = new SQLiteHelper(this);
        startReceivingData();
        //Notifications notify = new Notifications(this);
        //notify.createNotification("??");
        //goToSettingsMenu();
        goToDogOverViewNoButton();
        finish();
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
        Log.e(TAG,"madeittoslectedpg");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_dog) {
            Log.e(TAG,"madeITtoDOG");
            goToSettingsDog();
            return true;
            // Handle the camera action
        } else if (id == R.id.nav_account) {
            goToSettingsAccount();
            return true;

        } else if (id == R.id.nav_bluetooth) {
            goToSettingsBluetooth();
            return true;

        } else if (id == R.id.nav_notification) {
            goToSettingsNotification();
            return true;
        }


            return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dog_overview){
        //goToDogOverview();
        }
        else if (id == R.id.nav_heart_rate){
        //TODO: nav to heart rate specific page
        }
        else if (id == R.id.nav_resp_rate){
         //TODO: nav to resp rate specific page
        }
        else if (id == R.id.nav_core_temp){
         //TODO: nav to core temp specific page
        }
        else if (id == R.id.nav_ab_temp){
         //TODO: nav to ab temp specific page
        }
        else if (id == R.id.nav_logOut) {
            //TODO: logout function!

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToSettingsDog() {
        Intent goToSettingsDogIntent = new Intent(this, SettingsDog.class);
        startActivity(goToSettingsDogIntent);
    }

    public void goToSettingsAccount() {
        Intent goToSettingsAccountIntent = new Intent(this, SettingsAccount.class);
        startActivity(goToSettingsAccountIntent);
    }

    public void goToSettingsBluetooth() {
        Intent goToSettingsBluetoothIntent = new Intent(this, SettingsBluetooth.class);
        startActivity(goToSettingsBluetoothIntent);
    }

    public void goToSettingsNotification() {
        Intent goToSettingsNotificationIntent = new Intent(this, SettingsNotifications.class);
        startActivity(goToSettingsNotificationIntent);
    }

    public void goToDogOverview() {
        Intent DogIntent = new Intent(this, DogOverview.class);
        startActivity(DogIntent);
    }



    public void goToDogOverViewNoButton() {
        Intent goToDogOverViewNoButtonIntent = new Intent(this, DogOverview.class);
        startActivity(goToDogOverViewNoButtonIntent);
    }

    public void goToLoginPage() {
        Intent goToLoginPageIntent = new Intent(this,LoginActivity.class);
        startActivity(goToLoginPageIntent);
    }


    public void startReceivingData() {
        DataProcessingRunnable r = new DataProcessingRunnable(this);
        r.initRunnable(myDB,handler);
        Thread inputDataThread = new Thread(r);
        inputDataThread.start();

        //Intent intent = new Intent(this, InputIntentService.class);
//        intent.putExtra(myDB, handler);
//        startService(intent);
    }


}


