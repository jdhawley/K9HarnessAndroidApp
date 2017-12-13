package com.example.android.k9harnessandroidapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DogMeasurement extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SQLiteHelper db;
    private int MAX_DATA_POINTS = 30;

    private GraphView historicalGraph;
    private LineGraphSeries<DataPoint> historicalHighSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> historicalAverageSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> historicalLowSeries = new LineGraphSeries<>();

    private Button lowNumber;
    private Button avgNumber;
    private Button highNumber;

    private PageType pageType = null;

    private enum PageType {
        HeartRate,
        RespiratoryRate,
        CoreTemperature,
        AbdominalTemperature
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_measurement);
        SharedPreferences prefs = this.getSharedPreferences("DogSettings", MODE_PRIVATE);

        String type = getIntent().getStringExtra("PAGE_TYPE");
        if (type.equals("HeartRate")) {
            pageType = PageType.HeartRate;
        } else if (type.equals("RespiratoryRate")) {
            pageType = PageType.RespiratoryRate;
        } else if (type.equals("CoreTemperature")) {
            pageType = PageType.CoreTemperature;
        } else if (type.equals("AbdominalTemperature")) {
            pageType = PageType.AbdominalTemperature;
        }

        db = new SQLiteHelper(this);
        historicalGraph = findViewById(R.id.historicalGraph);
        lowNumber = findViewById(R.id.lowValueNum);
        avgNumber = findViewById(R.id.avgValueNum);
        highNumber = findViewById(R.id.highValueNum);

        lowNumber.setTextSize(24);
        avgNumber.setTextSize(24);
        highNumber.setTextSize(24);

        lowNumber.setTextColor(Color.rgb(255, 165, 0));
        avgNumber.setTextColor(Color.rgb(102, 255, 0));
        highNumber.setTextColor(Color.RED);

        setTitle();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_measurement);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        setActivityDefaults();


        new Thread(new Runnable() {
            @Override
            public void run() {
                setActivityDefaults();
            }
        }).start();
    }

    private void setUpGraph(Cursor results, String valColName) {
        if (results.getCount() == 0) {
            Log.d("MeasurementActivity", "No data was loaded.");
            return;  //No data has been saved to the database yet.
        }

        int sessionIDCol = results.getColumnIndex("SessionID");
        int valCol = results.getColumnIndex(valColName);

        results.moveToFirst();
        int sessionID = results.getInt(sessionIDCol);
        int val = results.getInt(valCol);
        int high = val;
        int low = val;
        int sum = val;
        int count = 1;

        while (results.moveToNext()) {
            if (results.getInt(sessionIDCol) > sessionID) {
                historicalHighSeries.appendData(new DataPoint(sessionID, high), false, MAX_DATA_POINTS);
                historicalAverageSeries.appendData(new DataPoint(sessionID, (double) sum / count), false, MAX_DATA_POINTS);
                historicalLowSeries.appendData(new DataPoint(sessionID, low), false, MAX_DATA_POINTS);

                sessionID = results.getInt(sessionIDCol);
                val = results.getInt(valCol);
                high = val;
                low = val;
                sum = val;
                count = 1;
                continue;
            }

            val = results.getInt(valCol);
            if (val > high) {
                high = results.getInt(valCol);
            }
            if (val < low) {
                low = results.getInt(valCol);
            }
            sum += val;
            count++;
        }

        lowNumber.setText(Integer.toString(low));
        avgNumber.setText(Integer.toString(sum / count));
        highNumber.setText(Integer.toString(high));

        historicalHighSeries.setColor(Color.RED);
        historicalAverageSeries.setColor(Color.rgb(102, 255, 0));
        historicalLowSeries.setColor(Color.rgb(255, 165, 0));

        historicalGraph.addSeries(historicalHighSeries);
        historicalGraph.addSeries(historicalAverageSeries);
        historicalGraph.addSeries(historicalLowSeries);
    }

    private void setUpHeartRate() {
        Log.d("MeasurementActivity", "Attempting to load HeartRate data.");
        Cursor results = db.getHistoricalHeartRateData(1);
        setUpGraph(results, "HeartRate");
    }

    private void setUpRespiratoryRate() {
        Log.d("MeasurementActivity", "Attempting to load RespiratoryRate data.");
        Cursor results = db.getHistoricalRespiratoryRateData(1);
        setUpGraph(results, "RespiratoryRate");
    }

    private void setUpCoreTemperature() {
        Log.d("MeasurementActivity", "Attempting to load CoreTemperature data.");
        Cursor results = db.getHistoricalCoreTemperatureData(1);
        setUpGraph(results, "CoreTemperature");
    }

    private void setUpAbdominalTemperature() {
        Log.d("MeasurementActivity", "Attempting to load AbdominalTemperature data.");
        Cursor results = db.getHistoricalAbdominalTemperatureData(1);
        setUpGraph(results, "AbdominalTemperature");
    }

    private void setActivityDefaults() {
        if (pageType == PageType.HeartRate) {
            setUpHeartRate();
        } else if (pageType == PageType.RespiratoryRate) {
            setUpRespiratoryRate();
        } else if (pageType == PageType.CoreTemperature) {
            setUpCoreTemperature();
        } else if (pageType == PageType.AbdominalTemperature) {
            setUpAbdominalTemperature();
        } else {
            Log.e("MeasurementActivity", "PageType not set properly.");
        }
    }

    private void setTitle() {
        if (pageType == PageType.HeartRate) {
            setTitle(R.string.Dog_HeartRate);
        } else if (pageType == PageType.RespiratoryRate) {
            setTitle(R.string.Dog_RespiratoryRate);
        } else if (pageType == PageType.CoreTemperature) {
            setTitle(R.string.Dog_CoreTemperature);
        } else if (pageType == PageType.AbdominalTemperature) {
            setTitle(R.string.Dog_AbdominalTemperature);
        } else {
            Log.e("MeasurementActivity", "PageType not set properly.");
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_measurement);
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
            // Handle the camera action
        } else if (id == R.id.nav_account) {
            nav.goToSettingsAccount(this);
            finish();
            //THIS IS THIS ACTIVITY
            return true;

        } else if (id == R.id.nav_bluetooth) {
            nav.goToSettingsBluetooth(this);
            finish();
            return true;

        } else if (id == R.id.nav_notification) {
            nav.goToSettingsNotification(this);
            finish();
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
        else if (id == R.id.nav_heart_rate && pageType != PageType.HeartRate){
            nav.goToHeartRateActivity(this);
            finish();
        }
        else if (id == R.id.nav_resp_rate && pageType != PageType.RespiratoryRate){
            nav.goToRespiratoryRateActivity(this);
            finish();
        }
        else if (id == R.id.nav_core_temp && pageType != PageType.CoreTemperature){
            nav.goToCoreTemperatureActivity(this);
            finish();
        }
        else if (id == R.id.nav_ab_temp && pageType != PageType.AbdominalTemperature){
            nav.goToAbdominalTemperatureActivity(this);
            finish();
        }
        else if (id == R.id.nav_logOut) {
            LogOut x = new LogOut();
            x.end(this);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dog_measurement);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
