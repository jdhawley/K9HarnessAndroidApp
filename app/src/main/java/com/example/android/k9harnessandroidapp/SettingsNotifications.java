package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsNotifications extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static boolean hasChanged = true;
    public final static String NOTIFICATION_SETTINGS = "NotificationSettings";
    public final static String SWITCH_HEART_RATE_HIGH_KEY = "switch_heart_rate_high_key";
    public final static String SWITCH_HEART_RATE_LOW_KEY = "switch_heart_rate_low_key";
    public final static String SWITCH_RESP_RATE_HIGH_KEY = "switch_resp_rate_high_key";
    public final static String SWITCH_RESP_RATE_LOW_KEY = "switch_resp_rate_low_key";
    public final static String SWITCH_CORE_TEMP_HIGH_KEY = "switch_core_temp_high_key";
    public final static String SWITCH_CORE_TEMP_LOW_KEY = "switch_core_temp_low_key";
    public final static String SWITCH_AB_TEMP_HIGH_KEY = "switch_ab_temp_high_key";
    public final static String SWITCH_AB_TEMP_LOW_KEY = "switch_ab_temp_low_key";
    //TODO: UI FORMATTING PLS... maybe default button??
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notifications);
        initializeNotificationSettings(this);

        setTitle(R.string.Notification_Settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.notification_settings);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {

                switch(compoundButton.getId()){
                    case R.id.switch_hr_low:
                        saveNotificationSetting(SWITCH_HEART_RATE_LOW_KEY, isEnabled);
                        break;
                    case R.id.switch_hr_high:
                        saveNotificationSetting(SWITCH_HEART_RATE_HIGH_KEY, isEnabled);
                        break;
                    case R.id.switch_rr_low:
                        saveNotificationSetting(SWITCH_RESP_RATE_LOW_KEY, isEnabled);
                        break;
                    case R.id.switch_rr_high:
                        saveNotificationSetting(SWITCH_RESP_RATE_HIGH_KEY, isEnabled);
                        break;
                    case R.id.switch_ct_low:
                        saveNotificationSetting(SWITCH_CORE_TEMP_LOW_KEY, isEnabled);
                        break;
                    case R.id.switch_ct_high:
                        saveNotificationSetting(SWITCH_CORE_TEMP_HIGH_KEY, isEnabled);
                        break;
                    case R.id.switch_at_low:
                        saveNotificationSetting(SWITCH_AB_TEMP_LOW_KEY, isEnabled);
                        break;
                    case R.id.switch_at_high:
                        saveNotificationSetting(SWITCH_AB_TEMP_HIGH_KEY, isEnabled);
                        break;
                    default:
                        break;
                }
            }
        };
        //START LISTENER FOR ALL OF THE SWITCHES
        ((Switch) findViewById(R.id.switch_hr_high)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_hr_low)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_rr_high)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_rr_low)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_ct_high)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_ct_low)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_at_high)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_at_low)).setOnCheckedChangeListener(multiListener);
    }

    public void initializeNotificationSettings(Context context) {
        Switch hr_high = (Switch) findViewById(R.id.switch_hr_high);
        hr_high.setChecked(getNotificationSetting(context, SWITCH_HEART_RATE_HIGH_KEY));

        Switch hr_low = (Switch) findViewById(R.id.switch_hr_low);
        hr_low.setChecked(getNotificationSetting(context, SWITCH_HEART_RATE_LOW_KEY));

        Switch rr_high = (Switch) findViewById(R.id.switch_rr_high);
        rr_high.setChecked(getNotificationSetting(context, SWITCH_RESP_RATE_HIGH_KEY));

        Switch rr_low = (Switch) findViewById(R.id.switch_rr_low);
        rr_low.setChecked(getNotificationSetting(context, SWITCH_RESP_RATE_LOW_KEY));

        Switch ct_high = (Switch) findViewById(R.id.switch_ct_high);
        ct_high.setChecked(getNotificationSetting(context, SWITCH_CORE_TEMP_HIGH_KEY));

        Switch ct_low = (Switch) findViewById(R.id.switch_ct_low);
        ct_low.setChecked(getNotificationSetting(context, SWITCH_CORE_TEMP_LOW_KEY));

        Switch at_high = (Switch) findViewById(R.id.switch_at_high);
        at_high.setChecked(getNotificationSetting(context, SWITCH_AB_TEMP_HIGH_KEY));

        Switch at_low = (Switch) findViewById(R.id.switch_at_low);
        at_low.setChecked(getNotificationSetting(context, SWITCH_AB_TEMP_LOW_KEY));

    }

    private void saveNotificationSetting(String key, boolean value) {
        SharedPreferences prefs = this.getSharedPreferences(NOTIFICATION_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
        //Toast.makeText(SettingsNotifications.this, ""+key+value,Toast.LENGTH_SHORT).show();
    }

    static public boolean getNotificationSetting(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(NOTIFICATION_SETTINGS, MODE_PRIVATE);
        return prefs.getBoolean(key, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.notification_settings);
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
        Navigation nav = new Navigation();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_dog) {
            nav.goToSettingsDog(this);
            finish();
            return true;
        } else if (id == R.id.nav_account) {
            nav.goToSettingsAccount(this);
            finish();
            return true;

        } else if (id == R.id.nav_bluetooth) {
            nav.goToSettingsBluetooth(this);
            finish();
            return true;

        } else if (id == R.id.nav_notification) {
            //this activity
            //goToSettingsNotification();
            //finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Navigation nav = new Navigation();
        int id = item.getItemId();

        if (id == R.id.nav_dog_overview){
            nav.goToDogOverview(this);
            finish();
        }
        else if (id == R.id.nav_heart_rate){
            nav.goToHeartRateActivity(this);
            finish();
        }
        else if (id == R.id.nav_resp_rate){
            nav.goToRespiratoryRateActivity(this);
            finish();
        }
        else if (id == R.id.nav_core_temp){
            nav.goToCoreTemperatureActivity(this);
            finish();
        }
        else if (id == R.id.nav_ab_temp){
            nav.goToAbdominalTemperatureActivity(this);
            finish();
        }
        else if (id == R.id.nav_logOut) {
            LogOut x = new LogOut();
            x.end(this);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.notification_settings);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
