package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsBluetooth extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /*
    TODO:
    Add bluetooth device ID field!

    search for devices???


     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_bluetooth);

        setTitle(R.string.Bluetooth_Settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.blue_settings);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeBluetoothSettings(this);
        Button submitButton = (Button) findViewById(R.id.button_save_changes);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText bluetoothID = (EditText) findViewById(R.id.bluetooth_ID_field);
                saveBluetoothSettings(bluetoothID.getText().toString());
            }
        });
    }
    public void initializeBluetoothSettings(Context context) {
        String ID = getBluetoothSettings(context);
        EditText bluetoothID = (EditText) findViewById(R.id.bluetooth_ID_field);
        bluetoothID.setText(ID);
    }
    private void saveBluetoothSettings(String ID) {
        SharedPreferences prefs = this.getSharedPreferences("BluetoothSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("BluetoothID", ID);
        editor.apply();
    }

    static public String getBluetoothSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("BluetoothSettings", MODE_PRIVATE);
        return prefs.getString("BluetoothID", "123456789012");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.blue_settings);
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_dog) {
            goToSettingsDog();
            finish();
            return true;
        } else if (id == R.id.nav_account) {
            goToSettingsAccount();
            finish();
            return true;

        } else if (id == R.id.nav_bluetooth) {
            //this activity
            //goToSettingsBluetooth();
            //finish();
            return true;

        } else if (id == R.id.nav_notification) {

            goToSettingsNotification();
            finish();
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
            finish();
            //TODO: CONSIDER SEPERATING XML FILES FOR EACH ACTIVITY
        }
        else if (id == R.id.nav_heart_rate){
            finish();
            //TODO: nav to heart rate specific page
        }
        else if (id == R.id.nav_resp_rate){
            //TODO: nav to resp rate specific page
            finish();
        }
        else if (id == R.id.nav_core_temp){
            //TODO: nav to core temp specific page
            finish();
        }
        else if (id == R.id.nav_ab_temp){
            finish();
            //TODO: nav to ab temp specific page
        }
        else if (id == R.id.nav_logOut) {
            finish();
            //TODO: logout function!

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.blue_settings);
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
}
