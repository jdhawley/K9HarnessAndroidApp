package com.example.android.k9harnessandroidapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DogOverview extends AppCompatActivity {
    private SQLiteHelper db;
    private Thread t;
    private boolean isThreadEnding;

    private int currentTick = -1;
    //TODO: Implement the secondsIncrementer somewhere
    private int secondsIncrementer = 1;

    private LineGraphSeries<DataPoint> hrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> rrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> ctSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> abtSeries = new LineGraphSeries<>();

    private int MAX_DATA_POINTS = 30;
    private GraphView hrGraph;
    private GraphView rrGraph;
    private GraphView ctGraph;
    private GraphView abtGraph;

    //TODO: Get the dogID from the page instead of hardcoding it.
    private int dogID = 1;
    private int hrHigh;
    private int hrLow;
    private int rrHigh;
    private int rrLow;
    private int ctHigh;
    private int ctLow;
    private int abtHigh;
    private int abtLow;

    private int hrSessionHigh = -1;
    private int hrSessionLow = -1;
    private int rrSessionHigh = -1;
    private int rrSessionLow = -1;
    private int ctSessionHigh = -1;
    private int ctSessionLow = -1;
    private int abtSessionHigh = -1;
    private int abtSessionLow = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_overview);
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
        hrGraph = findViewById(R.id.hrGraph);
        rrGraph = findViewById(R.id.rrGraph);
        ctGraph = findViewById(R.id.ctGraph);
        abtGraph = findViewById(R.id.abtGraph);

        hrGraph.getViewport().setXAxisBoundsManual(true);
        rrGraph.getViewport().setXAxisBoundsManual(true);
        ctGraph.getViewport().setXAxisBoundsManual(true);
        abtGraph.getViewport().setXAxisBoundsManual(true);

        hrGraph.getViewport().setYAxisBoundsManual(true);
        rrGraph.getViewport().setYAxisBoundsManual(true);
        ctGraph.getViewport().setYAxisBoundsManual(true);
        abtGraph.getViewport().setYAxisBoundsManual(true);

        hrGraph.addSeries(hrSeries);
        rrGraph.addSeries(rrSeries);
        ctGraph.addSeries(ctSeries);
        abtGraph.addSeries(abtSeries);

        hrGraph.getViewport().setScrollable(true);
        rrGraph.getViewport().setScrollable(true);
        ctGraph.getViewport().setScrollable(true);
        abtGraph.getViewport().setScrollable(true);

        updateGraphAxes();
    }

    private void updateGraphAxes(){
        if (currentTick < 6) {
            hrGraph.getViewport().setMaxX(6);
            rrGraph.getViewport().setMaxX(6);
            ctGraph.getViewport().setMaxX(6);
            abtGraph.getViewport().setMaxX(6);
        }
        else {
            hrGraph.getViewport().setMaxX(currentTick);
            rrGraph.getViewport().setMaxX(currentTick);
            ctGraph.getViewport().setMaxX(currentTick);
            abtGraph.getViewport().setMaxX(currentTick);
        }

        if (currentTick - MAX_DATA_POINTS >= 0) {
            hrGraph.getViewport().setMinX((currentTick - MAX_DATA_POINTS) + 1);
            rrGraph.getViewport().setMinX((currentTick - MAX_DATA_POINTS) + 1);
            ctGraph.getViewport().setMinX((currentTick - MAX_DATA_POINTS) + 1);
            abtGraph.getViewport().setMinX((currentTick - MAX_DATA_POINTS) + 1);
        }
        else{
            hrGraph.getViewport().setMinX(0);
            rrGraph.getViewport().setMinX(0);
            ctGraph.getViewport().setMinX(0);
            abtGraph.getViewport().setMinX(0);
        }

        hrGraph.getViewport().setMinY(hrSessionLow);
        hrGraph.getViewport().setMaxY(hrSessionHigh);
        rrGraph.getViewport().setMinY(rrSessionLow);
        rrGraph.getViewport().setMaxY(rrSessionHigh);
        ctGraph.getViewport().setMinY(ctSessionLow);
        ctGraph.getViewport().setMaxY(ctSessionHigh);
        abtGraph.getViewport().setMinY(abtSessionLow);
        abtGraph.getViewport().setMaxY(abtSessionHigh);
    }

    private void updateGraphColors(int hr, int rr, int ct, int abt){
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
            abtSeries.setColor(Color.RED);
        }
        else if (abt < abtLow) {
            abtSeries.setColor(Color.rgb(255, 165, 0));
        }
        else {
            abtSeries.setColor(Color.rgb(0, 100, 0));
        }
    }

    private void loadSession() {
        if (!db.duringSession()) {
            Log.d("Session", "No current session to load.");
            return;
        }

        Log.d("DogOverview", "Attempting to load session data");

        Cursor data = db.getAllSessionData();
        addDataToGraphs(data);
    }

    private void updateSessionHighsAndLows(int hr, int rr, int ct, int abt) {
        if (hrSessionLow == -1) {
            hrSessionLow = hr;
            hrSessionHigh = hr + 1;
        } else {
            if (hr < hrSessionLow) hrSessionLow = hr;
            if (hr > hrSessionHigh) hrSessionHigh = hr;
        }

        if (rrSessionLow == -1) {
            rrSessionLow = rr;
            rrSessionHigh = rr + 1;
        } else {
            if (rr < rrSessionLow) rrSessionLow = rr;
            if (rr > rrSessionHigh) rrSessionHigh = rr;
        }

        if (ctSessionLow == -1) {
            ctSessionLow = ct;
            ctSessionHigh = ct + 1;
        } else {
            if (ct < ctSessionLow) ctSessionLow = ct;
            if (ct > ctSessionHigh) ctSessionHigh = ct;
        }

        if (abtSessionLow == -1) {
            abtSessionLow = abt;
            abtSessionHigh = abt + 1;
        } else {
            if (abt < abtSessionLow) abtSessionLow = abt;
            if (abt > abtSessionHigh) abtSessionHigh = abt;
        }


    }

    private void addDataToGraphs(Cursor data) {
        //TODO: Remove the skipped and added variables
        int skipped = 0, added = 0;
        int hr, rr, ct, abt;

        hrGraph.removeAllSeries();
        rrGraph.removeAllSeries();
        ctGraph.removeAllSeries();
        abtGraph.removeAllSeries();

        while (data.moveToNext()) {
            if (data.getInt(data.getColumnIndex("SessionTick")) < currentTick) {
                skipped++;
                continue; //Data point has already been added.
            }

            hr = data.getInt(data.getColumnIndex("HeartRate"));
            rr = data.getInt(data.getColumnIndex("RespiratoryRate"));
            ct = data.getInt(data.getColumnIndex("CoreTemperature"));
            abt = data.getInt(data.getColumnIndex("AbdominalTemperature"));

            hrSeries.appendData(new DataPoint(currentTick, hr), false, MAX_DATA_POINTS);
            rrSeries.appendData(new DataPoint(currentTick, rr), false, MAX_DATA_POINTS);
            ctSeries.appendData(new DataPoint(currentTick, ct), false, MAX_DATA_POINTS);
            abtSeries.appendData(new DataPoint(currentTick, abt), false, MAX_DATA_POINTS);

            currentTick++;
            updateGraphColors(hr, rr, ct, abt);
            updateSessionHighsAndLows(hr, rr, ct, abt);
            added++;
        }

        // DO NOT REMOVE THIS SLEEP
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hrGraph.addSeries(hrSeries);
        rrGraph.addSeries(rrSeries);
        ctGraph.addSeries(ctSeries);
        abtGraph.addSeries(abtSeries);

        updateGraphAxes();

        Log.d("DogOverview", Integer.toString(skipped) + " skipped and " + Integer.toString(added) + " added.");
    }

    public void beginSession(View view){
        db.beginSession(dogID);
        currentTick = 0;

        isThreadEnding = false;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isThreadEnding) {
                    loadSession();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public void endSession(View view){
        db.endSession();
        currentTick = -1;

        hrGraph.removeAllSeries();
        rrGraph.removeAllSeries();
        ctGraph.removeAllSeries();
        abtGraph.removeAllSeries();

        hrSeries = new LineGraphSeries<>();
        rrSeries = new LineGraphSeries<>();
        ctSeries = new LineGraphSeries<>();
        abtSeries = new LineGraphSeries<>();

        hrGraph.addSeries(hrSeries);
        rrGraph.addSeries(rrSeries);
        ctGraph.addSeries(ctSeries);
        abtGraph.addSeries(abtSeries);

        isThreadEnding = true;
    }
}
