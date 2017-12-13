package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.Intent;

/**
 * Created by rickflaget on 12/13/17.
 */

public class Navigation {

    public void goToMain(Context ctx) {
        Intent goToMainIntent = new Intent(ctx, MainActivity.class);
        ctx.startActivity(goToMainIntent);
    }

    /* Navigation methods used throughout the application.
     * Method names explain the location that the function sends the user to
     *
     * @params: context is passed in through the parameter, usually just as 'this'.
     */
    public void goToSettingsDog(Context ctx) {
        Intent goToSettingsDogIntent = new Intent(ctx, SettingsDog.class);
        ctx.startActivity(goToSettingsDogIntent);
    }

    public void goToSettingsAccount(Context ctx) {
        Intent goToSettingsAccountIntent = new Intent(ctx, SettingsAccount.class);
        ctx.startActivity(goToSettingsAccountIntent);
    }

    public void goToSettingsBluetooth(Context ctx) {
        Intent goToSettingsBluetoothIntent = new Intent(ctx, SettingsBluetooth.class);
        ctx.startActivity(goToSettingsBluetoothIntent);
    }

    public void goToSettingsNotification(Context ctx) {
        Intent goToSettingsNotificationIntent = new Intent(ctx, SettingsNotifications.class);
        ctx.startActivity(goToSettingsNotificationIntent);
    }

    private void goToMeasurementIntent(Context ctx, String measurementType) {
        Intent goToMeasurementActivityIntent = new Intent(ctx, DogMeasurement.class);
        goToMeasurementActivityIntent.putExtra("PAGE_TYPE", measurementType);
        ctx.startActivity(goToMeasurementActivityIntent);
    }

    public void goToHeartRateActivity(Context ctx) {
        goToMeasurementIntent(ctx,"HeartRate");
    }

    public void goToRespiratoryRateActivity(Context ctx) {
        goToMeasurementIntent(ctx,"RespiratoryRate");
    }

    public void goToCoreTemperatureActivity(Context ctx) {
        goToMeasurementIntent(ctx,"CoreTemperature");
    }

    public void goToAbdominalTemperatureActivity(Context ctx) {
        goToMeasurementIntent(ctx, "AbdominalTemperature");
    }

    public void goToDogOverview(Context ctx) {
        Intent DogIntent = new Intent(ctx, DogOverview.class);
        ctx.startActivity(DogIntent);
    }

    public void goToLoginPage(Context ctx) {
        Intent goToLoginPageIntent = new Intent(ctx,LoginActivity.class);
        ctx.startActivity(goToLoginPageIntent);
    }
}
