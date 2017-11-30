package com.example.android.k9harnessandroidapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class DogOverview extends AppCompatActivity {
    private SQLiteHelper db;

    private int seconds = 0;
    private int secondsIncrementer = 10;

    private LineGraphSeries<DataPoint> hrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> rrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> ctSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> abTSeries = new LineGraphSeries<>();

    private int MAX_DATA_POINTS = 30;
    private GraphView hrGraph;
    private GraphView rrGraph;
    private GraphView ctGraph;
    private GraphView abtGraph;

    //TODO: Get this information from the page instead of hardcoding it.
    private int dogID = 1;
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
        initializeHighLowVals();
        initializeGraphs();

        db = new SQLiteHelper(this);
        LoadOrStartSession();
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

    public void addTestDataPoint(View view){
        // TODO: Implement functionality to read actual data instead of random generation.
        String dp = generateDataPoint();
        int[] data = processDataString(dp);

        int hr = data[0];
        int rr = data[1];
        int ct = data[2];
        int amt = data[3];
        int abt = data[4];

        if(!db.duringSession()) {
            db.beginSession(dogID);
        }
        db.addDataTick(hr, rr, ct, amt, abt);

        addDataPoint(hr, rr, ct, abt);
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
            abTSeries.setColor(Color.RED);
        }
        else if (abt < abtLow) {
            abTSeries.setColor(Color.rgb(255,165,0));
        }
        else {
            abTSeries.setColor(Color.rgb(0,100,0));
        }
    }

    public void addDataPoint(int hr, int rr, int ct, int abt){
        hrSeries.appendData(new DataPoint(seconds, hr), true, MAX_DATA_POINTS);
        rrSeries.appendData(new DataPoint(seconds, rr), true, MAX_DATA_POINTS);
        ctSeries.appendData(new DataPoint(seconds, ct), true, MAX_DATA_POINTS);
        abTSeries.appendData(new DataPoint(seconds, abt), true, MAX_DATA_POINTS);

        updateGraphColors(hr, rr, ct, abt);

        //TODO: Make sure this doesn't add an additional graph line overtop of the previous one each time
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

    private void LoadOrStartSession(){
        if(!db.duringSession()){
            db.beginSession(dogID);
            return;
        }

        Cursor data = db.getAllSessionData();

        int hrCol = data.getColumnIndex("HeartRate");
        int rrCol = data.getColumnIndex("RespiratoryRate");
        int ctCol = data.getColumnIndex("CoreTemperature");
        int abtCol = data.getColumnIndex("AbdominalTemperature");

        int hr, rr, ct, abt;
        while(data.moveToNext()){
            hr = data.getInt(hrCol);
            rr = data.getInt(rrCol);
            ct = data.getInt(ctCol);
            abt = data.getInt(abtCol);
            addDataPoint(hr, rr, ct, abt);
        }
    }

    public void beginSession(View view){
        db.beginSession(dogID);
    }

    public void endSession(View view){
        db.endSession();
    }
}
