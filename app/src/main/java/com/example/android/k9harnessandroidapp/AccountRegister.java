package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class AccountRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);



        Button saveChangesButton = (Button) findViewById(R.id.button_submit_account_settings_register);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove keyboard on button click
                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                catch (Exception e) {

                }

                EditText email = (EditText) findViewById(R.id.email);
                EditText firstName = (EditText) findViewById(R.id.first_name);
                EditText lastName = (EditText) findViewById(R.id.last_name);
                EditText password = (EditText) findViewById(R.id.password_field);
                EditText dogName = (EditText) findViewById(R.id.dog_name);

                String em = email.getText().toString();
                String fn = firstName.getText().toString();
                String ln = lastName.getText().toString();
                String pw = password.getText().toString();
                String doge = dogName.getText().toString();

                SharedPreferences prefs = getSharedPreferences("AccountSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("dogName", doge);
                editor.apply();



            }
        });

    }

}
