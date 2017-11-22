package com.example.android.k9harnessandroidapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class DogOverview extends AppCompatActivity {
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

        initializeHighLowVals();

        initializeGraphs();
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
        abtHigh = prefs.getInt(SettingsDog.AB_TEMP_HIGH_KEY,0);
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

    public void addDataPoint(View view){
        // TODO: Implement functionality to read actual data instead of random generation.
        String dp = generateDataPoint();
        int[] data = processDataString(dp);

        int hr = data[0];
        int rr = data[1];
        int ct = data[2];
        //int amt = data[3];  // this value is ignored because it is not used at this time
        int abt = data[4];

        hrSeries.appendData(new DataPoint(seconds, hr), true, MAX_DATA_POINTS);
        rrSeries.appendData(new DataPoint(seconds, rr), true, MAX_DATA_POINTS);
        ctSeries.appendData(new DataPoint(seconds, ct), true, MAX_DATA_POINTS);
        abTSeries.appendData(new DataPoint(seconds, abt), true, MAX_DATA_POINTS);

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
}
