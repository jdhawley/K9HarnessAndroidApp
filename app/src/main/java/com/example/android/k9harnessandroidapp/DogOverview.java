package com.example.android.k9harnessandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class DogOverview extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "DogOverview";

    private SQLiteHelper db;

    private int seconds = 0;
    private int secondsIncrementer = 1;

    private LineGraphSeries<DataPoint> hrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> rrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> ctSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> abTSeries = new LineGraphSeries<>();

    private int MAX_DATA_POINTS = 30;
    private GraphView hrGraph;
    private GraphView rrGraph;
    private GraphView ctGraph;
    private GraphView abtGraph;

    // TODO: Change series color based on value in respect to high/low values
    private int hrHigh;
    private int hrLow;
    private int rrHigh;
    private int rrLow;
    private int ctHigh;
    private int ctLow;
    private int abtHigh;
    private int abtLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_overview);
        setTitle(R.string.Dog_Overview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        initializeHighLowVals();
        initializeGraphs();

        db = new SQLiteHelper(this);
    }

    //TODO: NOT SURE IF THIS DOES ANYTHING YET, BUT WILL BE NEEDED IN FUTURE WHEN LAYOUT IS DIFFERENT
    @Override
    protected void onResume() {
        super.onResume();
        initializeHighLowVals();
    }

    private void initializeHighLowVals() {
        // TODO: SET UP DEFAULT VALUES OTHER THAN 0
        SharedPreferences prefs = this.getSharedPreferences("DogSettings", MODE_PRIVATE);
        hrHigh = prefs.getInt(SettingsDog.HEART_RATE_HIGH_KEY,0);
        hrLow = prefs.getInt(SettingsDog.HEART_RATE_LOW_KEY,0);
        rrHigh = prefs.getInt(SettingsDog.RESP_RATE_HIGH_KEY,0);
        rrLow = prefs.getInt(SettingsDog.RESP_RATE_LOW_KEY,0);
        ctHigh = prefs.getInt(SettingsDog.CORE_TEMP_HIGH_KEY,0);
        ctLow = prefs.getInt(SettingsDog.CORE_TEMP_LOW_KEY,0);
        abtHigh = prefs.getInt(SettingsDog.AB_TEMP_HIGH_KEY,-11);
        abtLow = prefs.getInt(SettingsDog.AB_TEMP_LOW_KEY,0);
    }

    private void initializeGraphs(){
        hrGraph = (GraphView) findViewById(R.id.hrGraph);
        rrGraph = (GraphView) findViewById(R.id.rrGraph);
        ctGraph = (GraphView) findViewById(R.id.ctGraph);
        abtGraph = (GraphView) findViewById(R.id.abtGraph);


        hrGraph.getViewport().setXAxisBoundsManual(true);
        rrGraph.getViewport().setXAxisBoundsManual(true);
        ctGraph.getViewport().setXAxisBoundsManual(true);
        abtGraph.getViewport().setXAxisBoundsManual(true);

        updateGraphAxes();
    }

    private void updateGraphAxes(){
        if(seconds < 6){
            hrGraph.getViewport().setMaxX(6);
            rrGraph.getViewport().setMaxX(6);
            ctGraph.getViewport().setMaxX(6);
            abtGraph.getViewport().setMaxX(6);
        }
        else {
            hrGraph.getViewport().setMaxX(seconds);
            rrGraph.getViewport().setMaxX(seconds);
            ctGraph.getViewport().setMaxX(seconds);
            abtGraph.getViewport().setMaxX(seconds);
        }

        if(seconds - MAX_DATA_POINTS >= 0) {
            hrGraph.getViewport().setMinX((seconds - MAX_DATA_POINTS) + 1);
            rrGraph.getViewport().setMinX((seconds - MAX_DATA_POINTS) + 1);
            ctGraph.getViewport().setMinX((seconds - MAX_DATA_POINTS) + 1);
            abtGraph.getViewport().setMinX((seconds - MAX_DATA_POINTS) + 1);
        }
        else{
            hrGraph.getViewport().setMinX(0);
            rrGraph.getViewport().setMinX(0);
            ctGraph.getViewport().setMinX(0);
            abtGraph.getViewport().setMinX(0);
        }
    }

    private int[] processDataString(String dataString){
        // Clean the "#" off the end of the string and split the values into an array
        String[] stringData = dataString.replace("#", "").split(":");

        int[] intData = new int[5];
        for(int i = 0; i < 5; i++){
            intData[i] = Integer.parseInt(stringData[i]);
        }

        return intData;
    }

    public void addDataPoint(int hr, int rr,int ct,int amt,int abt){
        // TODO: Implement functionality to read actual data instead of random generation.
        //String dp = generateDataPoint();
        //int[] data = processDataString(dp);

        //hr = data[0];
        //rr = data[1];
        //ct = data[2];
        //amt = data[3];
        //abt = data[4];

        //if(!db.duringSession()) {
            //TODO: Find the id for the dog being displayed instead of having this hardcoded.
          //  db.beginSession(1);
        //}
        //db.addDataTick(hr, rr, ct, amt, abt);

        hrSeries.appendData(new DataPoint(seconds, hr), true, MAX_DATA_POINTS);
        rrSeries.appendData(new DataPoint(seconds, rr), true, MAX_DATA_POINTS);
        ctSeries.appendData(new DataPoint(seconds, ct), true, MAX_DATA_POINTS);
        abTSeries.appendData(new DataPoint(seconds, abt), true, MAX_DATA_POINTS);

        if (hr > hrHigh) {
            hrSeries.setColor(Color.RED);
        }
        else if (hr < hrLow) {
            hrSeries.setColor(Color.rgb(255,165,0)); //orange
        }
        else {
            hrSeries.setColor(Color.rgb(0,100,0)); //dark green
        }


        if (rr > rrHigh) {
            rrSeries.setColor(Color.RED);
        }
        else if (rr < rrLow) {
            rrSeries.setColor(Color.rgb(255,165,0));
        }
        else {
            rrSeries.setColor(Color.rgb(0,100,0));
        }


        if (ct > ctHigh) {
            ctSeries.setColor(Color.RED);
        }
        else if (ct < ctLow) {
            ctSeries.setColor(Color.rgb(255,165,0));
        }
        else {
            ctSeries.setColor(Color.rgb(0,100,0));
        }


        if (abt > abtHigh) {
            abTSeries.setColor(Color.RED);
        }
        else if (abt < abtLow) {
            abTSeries.setColor(Color.rgb(255,165,0));
        }
        else {
            abTSeries.setColor(Color.rgb(0,100,0));
        }



        hrGraph.addSeries(hrSeries);
        rrGraph.addSeries(rrSeries);
        ctGraph.addSeries(ctSeries);
        abtGraph.addSeries(abTSeries);

        updateGraphAxes();

        seconds += secondsIncrementer;
    }

    private String generateDataPoint(){
        Random r = new Random();
        int hr = r.nextInt(41) + 60;
        int rr = r.nextInt(26) + 10;
        int ct = r.nextInt(3) + 101;
        int amt = r.nextInt(3) + 101;
        int abt = r.nextInt(3) + 101;
        return hr + ":" + rr + ":" + ct + ":" + amt + ":" + abt + "#";
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_layout);
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
            //DO NOTHING HERE CAUSE ITS THIS ACTIVITY
            //TODO: CONSIDER SEPERATING XML FILES FOR EACH ACTIVITY
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_layout);
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


}
