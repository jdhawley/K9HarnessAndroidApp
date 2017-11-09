package com.example.android.k9harnessandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsTypeMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_type_menu);
    }

    public void goToSettingsDog(View view) {
        Intent goToSettingsDogIntent = new Intent(this, SettingsDog.class);
        startActivity(goToSettingsDogIntent);
    }

    public void goToSettingsAccount(View view) {
        Intent goToSettingsAccountIntent = new Intent(this, SettingsAccount.class);
        startActivity(goToSettingsAccountIntent);
    }

    public void goToSettingsBluetooth(View view) {
        Intent goToSettingsBluetoothIntent = new Intent(this, SettingsBluetooth.class);
        startActivity(goToSettingsBluetoothIntent);
    }

    public void goToSettingsNotification(View view) {
        Intent goToSettingsNotificationIntent = new Intent(this, SettingsNotifications.class);
        startActivity(goToSettingsNotificationIntent);
    }
}
