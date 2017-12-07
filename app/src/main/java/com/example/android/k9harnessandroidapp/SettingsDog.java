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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsDog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

     private static final int DOG_SETTINGS_NUM = 8;

     public static final String HEART_RATE_HIGH_KEY = "Heart Rate High";
     public static final String HEART_RATE_LOW_KEY = "Heart Rate Low";
     public static final String RESP_RATE_HIGH_KEY = "Resp Rate High";
     public static final String RESP_RATE_LOW_KEY = "Resp Rate Low";
     public static final String CORE_TEMP_HIGH_KEY = "Core Temp High";
     public static final String CORE_TEMP_LOW_KEY = "Core Temp Low";
     public static final String AB_TEMP_HIGH_KEY = "Ab Temp High";
     public static final String AB_TEMP_LOW_KEY = "Ab Temp Low";

     private static final int HEART_RATE_HIGH_LOC = 0;
     private static final int HEART_RATE_LOW_LOC = 1;
     private static final int RESP_RATE_HIGH_LOC = 2;
     private static final int RESP_RATE_LOW_LOC = 3;
     private static final int CORE_TEMP_HIGH_LOC = 4;
     private static final int CORE_TEMP_LOW_LOC = 5;
     private static final int AB_TEMP_HIGH_LOC = 6;
     private static final int AB_TEMP_LOW_LOC = 7;

    /* TODO
    Add real time checking to integer value input to make sure input is valid.

    Implement using text Watcher action.
    
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_dog);
        setTitle(R.string.Dog_Settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_settings);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeDogSettings(this);
        Button submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Placing all of the fields in Dog Settings into an integer array to save.
                int[] dogSettingsArray = new int[DOG_SETTINGS_NUM];

                EditText heartRateHighInput = (EditText) findViewById(R.id.setting_hr_high);
                dogSettingsArray[HEART_RATE_HIGH_LOC] = Integer.parseInt(heartRateHighInput.getText().toString());

                EditText heartRateLowInput = (EditText) findViewById(R.id.setting_hr_low);
                dogSettingsArray[HEART_RATE_LOW_LOC] = Integer.parseInt(heartRateLowInput.getText().toString());

                EditText respRateHighInput = (EditText) findViewById(R.id.setting_rr_high);
                dogSettingsArray[RESP_RATE_HIGH_LOC] = Integer.parseInt(respRateHighInput.getText().toString());

                EditText respRateLowInput = (EditText) findViewById(R.id.setting_rr_low);
                dogSettingsArray[RESP_RATE_LOW_LOC] = Integer.parseInt(respRateLowInput.getText().toString());

                EditText coreTempHighInput = (EditText) findViewById(R.id.setting_ct_high);
                dogSettingsArray[CORE_TEMP_HIGH_LOC] = Integer.parseInt(coreTempHighInput.getText().toString());

                EditText coreTempLowInput = (EditText) findViewById(R.id.setting_ct_low);
                dogSettingsArray[CORE_TEMP_LOW_LOC] = Integer.parseInt(coreTempLowInput.getText().toString());

                EditText abTempHighInput = (EditText) findViewById(R.id.setting_at_high);
                dogSettingsArray[AB_TEMP_HIGH_LOC] = Integer.parseInt(abTempHighInput.getText().toString());

                EditText abTempLowInput = (EditText) findViewById(R.id.setting_at_low);
                dogSettingsArray[AB_TEMP_LOW_LOC] = Integer.parseInt(abTempLowInput.getText().toString());

                saveDogSettings(dogSettingsArray);
            }
        });

    }

    public void initializeDogSettings(Context context) {
        int[] InitValArray = getDogSettings(context);
        EditText heartRateHighInput = (EditText) findViewById(R.id.setting_hr_high);
        heartRateHighInput.setText("" + InitValArray[HEART_RATE_HIGH_LOC]);

        EditText heartRateLowInput = (EditText) findViewById(R.id.setting_hr_low);
        heartRateLowInput.setText("" + InitValArray[HEART_RATE_LOW_LOC]);

        EditText respRateHighInput = (EditText) findViewById(R.id.setting_rr_high);
        respRateHighInput.setText("" + InitValArray[RESP_RATE_HIGH_LOC]);

        EditText respRateLowInput = (EditText) findViewById(R.id.setting_rr_low);
        respRateLowInput.setText("" + InitValArray[RESP_RATE_LOW_LOC]);

        EditText coreTempHighInput = (EditText) findViewById(R.id.setting_ct_high);
        coreTempHighInput.setText("" + InitValArray[CORE_TEMP_HIGH_LOC]);

        EditText coreTempLowInput = (EditText) findViewById(R.id.setting_ct_low);
        coreTempLowInput.setText("" + InitValArray[CORE_TEMP_LOW_LOC]);

        EditText abTempHighInput = (EditText) findViewById(R.id.setting_at_high);
        abTempHighInput.setText("" + InitValArray[AB_TEMP_HIGH_LOC]);

        EditText abTempLowInput = (EditText) findViewById(R.id.setting_at_low);
        abTempLowInput.setText("" + InitValArray[AB_TEMP_LOW_LOC]);

    }

    private void saveDogSettings(int[] settingsArray) {
        SharedPreferences prefs = this.getSharedPreferences("DogSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HEART_RATE_HIGH_KEY, settingsArray[HEART_RATE_HIGH_LOC]);
        editor.putInt(HEART_RATE_LOW_KEY, settingsArray[HEART_RATE_LOW_LOC]);
        editor.putInt(RESP_RATE_HIGH_KEY, settingsArray[RESP_RATE_HIGH_LOC]);
        editor.putInt(RESP_RATE_LOW_KEY, settingsArray[RESP_RATE_LOW_LOC]);
        editor.putInt(CORE_TEMP_HIGH_KEY, settingsArray[CORE_TEMP_HIGH_LOC]);
        editor.putInt(CORE_TEMP_LOW_KEY, settingsArray[CORE_TEMP_LOW_LOC]);
        editor.putInt(AB_TEMP_HIGH_KEY, settingsArray[AB_TEMP_HIGH_LOC]);
        editor.putInt(AB_TEMP_LOW_KEY, settingsArray[AB_TEMP_LOW_LOC]);
        editor.apply();
    }

    static public int[] getDogSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("DogSettings", MODE_PRIVATE);
        //TODO: Change default value
        int[] array = new int[DOG_SETTINGS_NUM];

        array[HEART_RATE_HIGH_LOC] = prefs.getInt(HEART_RATE_HIGH_KEY, 0);
        array[HEART_RATE_LOW_LOC]  = prefs.getInt(HEART_RATE_LOW_KEY, 0);
        array[RESP_RATE_HIGH_LOC]  = prefs.getInt(RESP_RATE_HIGH_KEY, 0);
        array[RESP_RATE_LOW_LOC]   = prefs.getInt(RESP_RATE_LOW_KEY, 0);
        array[CORE_TEMP_HIGH_LOC]  = prefs.getInt(CORE_TEMP_HIGH_KEY, 0);
        array[CORE_TEMP_LOW_LOC]   = prefs.getInt(CORE_TEMP_LOW_KEY, 0);
        array[AB_TEMP_HIGH_LOC]    = prefs.getInt(AB_TEMP_HIGH_KEY, 0);
        array[AB_TEMP_LOW_LOC]     = prefs.getInt(AB_TEMP_LOW_KEY, 0);

        return array;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_settings);
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
            ///goToSettingsDog();
            //THIS IS THIS OPTION
            return true;
            // Handle the camera action
        } else if (id == R.id.nav_account) {
            goToSettingsAccount();
            finish();
            return true;

        } else if (id == R.id.nav_bluetooth) {
            goToSettingsBluetooth();
            finish();
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
            goToDogOverview();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_settings);
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
