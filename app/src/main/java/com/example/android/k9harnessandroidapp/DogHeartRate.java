package com.example.android.k9harnessandroidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DogHeartRate extends AppCompatActivity {
    private SQLiteHelper db;

    private GraphView historicalGraph;
    private LineGraphSeries<DataPoint> historicalHighSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> historicalAverageSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> historicalLowSeries = new LineGraphSeries<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_heart_rate);

        db = new SQLiteHelper(this);

        //TODO: Make the historical highs/lows/avgs/etc load into the graphview upon creation
        initializeGraph();
    }

    private void initializeGraph() {
        historicalGraph = findViewById(R.id.historicalGraph);

        //TODO: pull the dogID from shared preferences instead of hardcoding it
        //Cursor historicalData = db.getHistoricalHeartRateData(1);
    }
}
