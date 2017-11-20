package com.example.android.k9harnessandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.Random;

public class DogOverview extends AppCompatActivity {
    private boolean TESTING_MESSAGES = false;
    private int testSeconds = 0;
    private int testSecondsIncrementer = 1;
    private LineGraphSeries<DataPoint> hrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> rrSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> ctSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> abTSeries = new LineGraphSeries<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_overview);
    }

    private String generateDataPoint(){
        Random r = new Random();
        int hr = r.nextInt(41) + 60;
        int rr = r.nextInt(26) + 10;
        int ct = r.nextInt(3) + 101;
        int amT = r.nextInt(3) + 101;
        int abT = r.nextInt(3) + 101;
        return hr + ":" + rr + ":" + ct + ":" + amT + ":" + abT + "#";
    }

    public void addDataPoint(View view){
        int MAX_DATA_POINTS = 30;

        String dp = generateDataPoint();
        // Clean the "#" off the end of the data string.
        dp = dp.replace("#", "");
        String data[] = dp.split(":");

        int hr = Integer.parseInt(data[0]);
        int rr = Integer.parseInt(data[1]);
        int ct = Integer.parseInt(data[2]);
        int amT = Integer.parseInt(data[3]);
        int abT = Integer.parseInt(data[4]);

        if(TESTING_MESSAGES) {
            String message = hr + ":" + rr + ":" + ct + ":" + abT + "#" + testSeconds;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        hrSeries.appendData(new DataPoint(testSeconds, hr), true, MAX_DATA_POINTS);
        rrSeries.appendData(new DataPoint(testSeconds, rr), true, MAX_DATA_POINTS);
        ctSeries.appendData(new DataPoint(testSeconds, ct), true, MAX_DATA_POINTS);
        abTSeries.appendData(new DataPoint(testSeconds, abT), true, MAX_DATA_POINTS);

        GraphView hrGraph = (GraphView) findViewById(R.id.hrGraph);
        GraphView rrGraph = (GraphView) findViewById(R.id.rrGraph);
        GraphView ctGraph = (GraphView) findViewById(R.id.ctGraph);
        GraphView abTGraph = (GraphView) findViewById(R.id.abTGraph);

        hrGraph.addSeries(hrSeries);
        rrGraph.addSeries(rrSeries);
        ctGraph.addSeries(ctSeries);
        abTGraph.addSeries(abTSeries);

        hrGraph.getViewport().setXAxisBoundsManual(true);
        rrGraph.getViewport().setXAxisBoundsManual(true);
        ctGraph.getViewport().setXAxisBoundsManual(true);
        abTGraph.getViewport().setXAxisBoundsManual(true);

        if(testSeconds < 6){
            hrGraph.getViewport().setMaxX(6);
            rrGraph.getViewport().setMaxX(6);
            ctGraph.getViewport().setMaxX(6);
            abTGraph.getViewport().setMaxX(6);
        }
        else {
            hrGraph.getViewport().setMaxX(testSeconds);
            rrGraph.getViewport().setMaxX(testSeconds);
            ctGraph.getViewport().setMaxX(testSeconds);
            abTGraph.getViewport().setMaxX(testSeconds);
        }

        if(testSeconds - MAX_DATA_POINTS >= 0) {
            hrGraph.getViewport().setMinX((testSeconds - MAX_DATA_POINTS) + 1);
            rrGraph.getViewport().setMinX((testSeconds - MAX_DATA_POINTS) + 1);
            ctGraph.getViewport().setMinX((testSeconds - MAX_DATA_POINTS) + 1);
            abTGraph.getViewport().setMinX((testSeconds - MAX_DATA_POINTS) + 1);
        }
        else{
            hrGraph.getViewport().setMinX(0);
            rrGraph.getViewport().setMinX(0);
            ctGraph.getViewport().setMinX(0);
            abTGraph.getViewport().setMinX(0);
        }

        testSeconds += testSecondsIncrementer;
    }
}
