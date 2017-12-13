package com.example.android.k9harnessandroidapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by rickflaget on 12/7/17.
 *
 * Class utalized to create notifications from input data.
 * Code largley sourced from this: TODO modify to fit our needs more accuratly
 * https://stackoverflow.com/questions/46990995/on-android-8-oreo-api-26-and-later-notification-does-not-display
 */

public class Notifications {
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    /* notification settings */
    private boolean notifyHRHigh;
    private boolean notifyHRLow;

    private boolean notifyRRHigh;
    private boolean notifyRRLow;

    private boolean notifyATHigh;
    private boolean notifyATLow;

    private boolean notifyCTHigh;
    private boolean notifyCTLow;

    /* high/low values */
    private int heartRateHighVal;
    private int heartRateLowVal;

    private int respRateHighVal;
    private int respRateLowVal;

    private int coreTempHighVal;
    private int coreTempLowVal;

    private int abTempHighVal;
    private int abTempLowVal;




    private NotificationManager notifManager;
    public String channelName = "K9CollarNotifications";
    public String channelID = "K9CollarNotificationsID";
    public String channelDescription = "K9 Harness Alerts";

    public Context context;

    public Notifications(Context ctx) {
        context = ctx;
    }

    /* updates notification settings and dog high/low values for utilization in
     * notification methods.
     *
     * @params: none
     */
    public void setNotificationsSettings (){
        updateDogVals();
        updateNotifVals();
    }

    /*
     * Main processing method for creating notifications. Takes in full input
     * data provided by hardware after data processing and determines if the value is
     * in a notification range and if those notifications are enabled in settings.
     *
     * @params:
     * hr: heart rate
     * rr: respiratory rate
     * ct: core temperature
     * at: abdominal temperature
     *
     * all parameters are supplied after dataProcessingRunnable
     */
    public void createAllNotifications(int hr, int rr, int ct, int at) {
        setNotificationsSettings();
        String notificationMessage = "";
        if (!SettingsNotifications.hasChanged) {

        //TODO:
        }
        else {
            //TODO:
        }

        /* HEART RATE NOTIFICATIONS */
        if (hr > heartRateHighVal && notifyHRHigh){
           notificationMessage = notificationMessage + "HR HIGH: "+hr+"\n";
        }
        else if (hr < heartRateLowVal && notifyHRLow) {
            notificationMessage = notificationMessage + "HR LOW: "+hr+"\n";
        }

        /*RESP RATE NOTIFCATIONS */
        if (rr > respRateHighVal && notifyRRHigh){
            notificationMessage = notificationMessage + "RR HIGH: "+rr+"\n";
        }
        else if (rr < respRateLowVal && notifyRRLow) {
            notificationMessage = notificationMessage + "RR LOW: "+rr+"\n";
        }

        /*CORE TEMP NOTIFICATIONS */
        if (ct > coreTempHighVal && notifyCTHigh){
            notificationMessage = notificationMessage + "CT HIGH: "+ct+"\n";
        }
        else if (ct < coreTempLowVal && notifyCTLow) {
            notificationMessage = notificationMessage + "CT LOW: "+ct+"\n";
        }

        /*AB TEMP NOTIFICATIONS */
        if (at > abTempHighVal && notifyATHigh){
            notificationMessage = notificationMessage + "AbT HIGH: "+at+"\n";
        }
        else if (at < abTempLowVal &&  notifyATLow) {
            notificationMessage = notificationMessage + "AbT LOW: "+at+"\n";
        }
        //TODO: MAKE BETTER NOTIFICATION
        if (!notificationMessage.equals("")){
            createNotification(notificationMessage);
        }
    }

    /*
     * Helper function to retrieve a dogs settings for high and low values for each measurement type
     * Sets these values to class variables already defined.
     * @params: none
     */

    public void updateDogVals() {
        SharedPreferences prefs = context.getSharedPreferences("DogSettings", context.MODE_PRIVATE);

        heartRateHighVal = prefs.getInt(SettingsDog.HEART_RATE_HIGH_KEY, 0);
        heartRateLowVal  = prefs.getInt(SettingsDog.HEART_RATE_LOW_KEY, 0);
        respRateHighVal  = prefs.getInt(SettingsDog.RESP_RATE_HIGH_KEY, 0);
        respRateLowVal   = prefs.getInt(SettingsDog.RESP_RATE_LOW_KEY, 0);
        coreTempHighVal  = prefs.getInt(SettingsDog.CORE_TEMP_HIGH_KEY, 0);
        coreTempLowVal   = prefs.getInt(SettingsDog.CORE_TEMP_LOW_KEY, 0);
        abTempHighVal    = prefs.getInt(SettingsDog.AB_TEMP_HIGH_KEY, 0);
        abTempLowVal    = prefs.getInt(SettingsDog.AB_TEMP_LOW_KEY, 0);

        /*Log.e("Notifications", "HeartRateHighVal"+ heartRateHighVal+" ");
        Log.e("Notifications", "HeartRateLowVal"+ heartRateLowVal+" ");
        Log.e("Notifications", "respRateHighVal"+ respRateHighVal+" ");
        Log.e("Notifications", "respRateLowVal"+ heartRateLowVal+" ");
        Log.e("Notifications", "coreTempHighVal"+ coreTempHighVal+" ");
        Log.e("Notifications", "coreTempLowVal"+ coreTempLowVal+" ");
        Log.e("Notifications", "abTempHighVal"+ abTempHighVal+" ");
        Log.e("Notifications", "abTempLowVal"+ abTempLowVal+" ");*/


    }

    /* Helper function to retreive a users notification settings for when they should be notified.
     * Sets these variables to class variables already defined.
     * @params: none
     */

    public void updateNotifVals() {
        SharedPreferences prefs = context.getSharedPreferences(SettingsNotifications.NOTIFICATION_SETTINGS, SettingsNotifications.MODE_PRIVATE);
        notifyHRHigh = prefs.getBoolean(SettingsNotifications.SWITCH_HEART_RATE_HIGH_KEY, true);
        notifyHRLow = prefs.getBoolean(SettingsNotifications.SWITCH_HEART_RATE_LOW_KEY, true);

        notifyRRHigh = prefs.getBoolean(SettingsNotifications.SWITCH_RESP_RATE_HIGH_KEY, true);
        notifyRRLow = prefs.getBoolean(SettingsNotifications.SWITCH_RESP_RATE_LOW_KEY, true);

        notifyCTHigh = prefs.getBoolean(SettingsNotifications.SWITCH_CORE_TEMP_HIGH_KEY, true);
        notifyCTLow = prefs.getBoolean(SettingsNotifications.SWITCH_CORE_TEMP_LOW_KEY, true);

        notifyATHigh = prefs.getBoolean(SettingsNotifications.SWITCH_AB_TEMP_HIGH_KEY, true);
        notifyATLow = prefs.getBoolean(SettingsNotifications.SWITCH_AB_TEMP_LOW_KEY, true);

        /*Log.e("Notifications", "notifyHRHigh: "+ notifyHRHigh);
        Log.e("Notifications", "notifyHRLow: "+ notifyHRLow);

        Log.e("Notifications", "notifyRRHigh: "+ notifyRRHigh);
        Log.e("Notifications", "notifyRRLow: "+ notifyRRLow);

        Log.e("Notifications", "notifyCTHigh: "+ notifyCTHigh);
        Log.e("Notifications", "notifyCTLow: "+ notifyCTLow);

        Log.e("Notifications", "notifyATHigh: "+ notifyATHigh);
        Log.e("Notifications", "notifyATLow: "+ notifyATLow);
        Log.e("Notifications", "!!!!!!!!!!!!!!!!!!!!!!!!");*/
    }

    public void createNotification(String aMessage) {
        final int NOTIFY_ID = 1002;
        
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelID, channelName, importance);
            mChannel.setDescription(channelDescription);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notifManager.createNotificationChannel(mChannel);

            builder = new NotificationCompat.Builder(context, channelID);

            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            builder.setContentTitle(aMessage)  // required
                    .setSmallIcon(R.drawable.k9_notification) // required
                    .setContentText(context.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(context);

            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            builder.setContentTitle(aMessage)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(context.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

}
