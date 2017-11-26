package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsBluetooth extends AppCompatActivity {
    /*
    TODO:
    Add bluetooth device ID field!

    search for devices???


     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_bluetooth);
        initalizeBluetoothSettings(this);
        Button submitButton = (Button) findViewById(R.id.button_save_changes);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText bluetoothID = (EditText) findViewById(R.id.bluetooth_ID_field);
                saveBluetoothSettings(bluetoothID.getText().toString());
            }
        });
    }
    public void initalizeBluetoothSettings(Context context) {
        String ID = getBluetoothSettings(context);
        EditText bluetoothID = (EditText) findViewById(R.id.bluetooth_ID_field);
        bluetoothID.setText(ID);
    }
    private void saveBluetoothSettings(String ID) {
        SharedPreferences prefs = this.getSharedPreferences("BluetoothSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("BluetoothID", ID);
        editor.apply();
    }

    static public String getBluetoothSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("BluetoothSettings", MODE_PRIVATE);
        return prefs.getString("BluetoothID", "123456789012");
    }
}
