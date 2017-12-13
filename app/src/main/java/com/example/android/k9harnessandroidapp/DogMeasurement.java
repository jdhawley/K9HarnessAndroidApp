package com.example.android.k9harnessandroidapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DogMeasurement extends AppCompatActivity {
    private SQLiteHelper db;
    private int MAX_DATA_POINTS = 30;

    private GraphView historicalGraph;
    private LineGraphSeries<DataPoint> historicalHighSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> historicalAverageSeries = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> historicalLowSeries = new LineGraphSeries<>();

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

//        setTitle();

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
}
