package com.example.android.k9harnessandroidapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SQLiteHelper myDB;
    public int val = 0;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            TextView txt = (TextView) findViewById(R.id.title_text);
            txt.setText(""+val);
            ++val;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new SQLiteHelper(this);
        myDB.beginSession(1); //test dog and test handler input
        startReceivingData();
    }

    public void goToSettingsMenu(View view) {
        Intent goToSettingsMenuIntent = new Intent(this, SettingsTypeMenu.class);
        startActivity(goToSettingsMenuIntent);
    }

    /* Convert ADC value to degrees Fahrenheit */
    public double convertTemp(double rawValue) {
        double temp = 1023.0 / rawValue - 1.0;
        temp = 10000.0 / temp;
        double steinhart = temp / 10000.0;
        steinhart = Math.log(steinhart);
        steinhart = steinhart / 3950.0;
        steinhart = steinhart + 1.0 / (25.0 + 273.15);
        steinhart = 1.0 / steinhart;
        steinhart = steinhart * (9.0 / 5.0) - 459.67;
        return steinhart;
    }

    /* Calculate core tempurature from the abdominal, ambient, and chest temperatures */
    public int calculateCoreTemp(double abdominal, double ambient, double chest, double avgAmbient) {
        double C = 1;
        double Ka = (0.01203) * avgAmbient + -0.77285;
        double Kb = (0.05661) * avgAmbient + -3.60028;
        double Kc = (-0.07096) * avgAmbient + 5.64545;
        double coreTemp = (Ka * ambient) + (Kb * chest) + (Kc * abdominal) + C;
        return (int) coreTemp;
    }

    public void startReceivingData() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int hr;
                int rr;
                int at;
                int ct;
                double abdominalTemp;
                double ambientTemp;
                double chestTemp;

                DataMockup testData = new DataMockup();
                while (true) {
                    //wait 10 seconds
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //New data input
                    String[] parsedMessage = testData.getDataPoint().split(":");
                    parsedMessage[0] = parsedMessage[0].trim();
                    parsedMessage[1] = parsedMessage[1].trim();
                    parsedMessage[2] = parsedMessage[2].trim();
                    parsedMessage[3] = parsedMessage[3].trim();
                    parsedMessage[4] = parsedMessage[4].replaceAll("#","");
                    parsedMessage[4] = parsedMessage[4].trim();
                    /*
                    boolean hrNumeric = isNumeric(parsedMessage[0]);
                    boolean rrNumeric = isNumeric(parsedMessage[1]);
                    boolean abtNumeric = isNumeric(parsedMessage[2]);
                    boolean ambNumeric = isNumeric(parsedMessage[3]);
                    boolean chestNumeric = isNumeric(parsedMessage[4]);
                    */

                    //copied from previous versions
                    hr = Integer.parseInt(parsedMessage[0]);
                    rr = Integer.parseInt(parsedMessage[1]);
                    abdominalTemp = (double) Integer.parseInt(parsedMessage[2]);
                    ambientTemp = (double) Integer.parseInt(parsedMessage[3]);
                    chestTemp = (double) Integer.parseInt(parsedMessage[4]);

                    abdominalTemp = convertTemp(abdominalTemp);
                    ambientTemp = convertTemp(ambientTemp);
                    chestTemp = convertTemp(chestTemp);

                    //get average ambient temp so far
                    //int sizeAmbient = listAmbient.size();
                    //TODO: add SQLite call to get avg ambient

                    double avgAmbient = ambientTemp;
                    at = (int) abdominalTemp;
                    ct = calculateCoreTemp(abdominalTemp, ambientTemp, chestTemp, avgAmbient);
                    myDB.addDataTick(hr,rr,ct,(int)ambientTemp,at);
                    handler.sendEmptyMessage(0);
                    }
                }
            };
        Thread inputDataThread = new Thread(r);
        inputDataThread.start();
    }



}


