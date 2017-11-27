package com.example.android.k9harnessandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private SQLiteHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new SQLiteHelper(this);
    }

    public void goToSettingsMenu(View view) {
        Intent goToSettingsMenuIntent = new Intent(this, SettingsTypeMenu.class);
        startActivity(goToSettingsMenuIntent);
    }
}


