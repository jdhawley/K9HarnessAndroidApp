package com.example.android.k9harnessandroidapp;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.os.Handler;
import android.util.Log;

import java.util.Date;


/**
 * Created by rickflaget on 11/29/17.
 */

public class DataProcessingRunnable implements Runnable {
    private String TAG = "DataProcessRunnable";
    private Handler handler;
    private SQLiteHelper myDB;
    private Context context;
    public static boolean isRunning = false;

    public DataProcessingRunnable(Context ctx){
        context = ctx;
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

    /* Calculate core temperature from the abdominal, ambient, and chest temperatures */
    public int calculateCoreTemp(double abdominal, double ambient, double chest, double avgAmbient) {
        double C = 1;
        double Ka = (0.01203) * avgAmbient + -0.77285;
        double Kb = (0.05661) * avgAmbient + -3.60028;
        double Kc = (-0.07096) * avgAmbient + 5.64545;
        double coreTemp = (Ka * ambient) + (Kb * chest) + (Kc * abdominal) + C;
        return (int) coreTemp;
    }

    public void initRunnable(SQLiteHelper a, Handler b) {
        myDB = a;
        handler = b;
    }

    @Override
    public void run() {
        isRunning = true;

        int hr;
        int rr;
        int at;
        int ct;
        double abdominalTemp;
        double ambientTemp;
        double chestTemp;

        Notifications notify = new Notifications(context);
        notify.setNotificationsSettings();


        DataMockup testData = new DataMockup();
        while (true) {
            //wait 10 seconds
            try {
                Thread.sleep(10000);
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

            Cursor ambientTemps = myDB.getAllAmbientTemps();
            int numTemps = ambientTemps.getCount() + 1; //for current read in val

            double total = ambientTemp;
            while(ambientTemps.moveToNext()){
                total += ambientTemps.getInt(0);
            }
            //TODO: TEST avgAMBINET VALUES

            double avgAmbient = total/(double)numTemps;
            at = (int) abdominalTemp;
            ct = calculateCoreTemp(abdominalTemp, ambientTemp, chestTemp, avgAmbient);

            //DO NOT TOUCH ECE
            myDB.addDataTick(hr,rr,ct,(int)ambientTemp,at);
            notify.createAllNotifications(hr,rr,ct,at);
            handler.sendEmptyMessage(0);
            Log.d(TAG, "STILL RUNNING!");
        }
    }
}

