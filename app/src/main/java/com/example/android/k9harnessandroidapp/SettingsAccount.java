package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import java.util.Set;
//TODO: SET PASSWORD REQS... find out what DB can support!!
public class SettingsAccount extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);
        setTitle(R.string.Account_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.account_settings);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final EditText newPasswordFieldx = (EditText) findViewById(R.id.new_password_field);
        final EditText verifyPasswordFieldx = (EditText) findViewById(R.id.verify_new_password_field);

        verifyPasswordFieldx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO: ????
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (newPasswordFieldx.getText().toString().equals(verifyPasswordFieldx.getText().toString())) {
                    verifyPasswordFieldx.setTextColor(Color.rgb(0,100,0));
                }
                else {
                    verifyPasswordFieldx.setTextColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            //TODO: ????
            }
        });





        Button saveChangesButton = (Button) findViewById(R.id.button_submit_account_settings_changes);
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

                //Sending all of the textfields in Settings Account to verify/save.
                EditText currentPasswordField = (EditText) findViewById(R.id.current_password_field);
                EditText newPasswordField = (EditText) findViewById(R.id.new_password_field);
                EditText verifyPasswordField = (EditText) findViewById(R.id.verify_new_password_field);

                //send string version of text field to save Account Settings
                saveAccountSettings(currentPasswordField.getText().toString(),
                        newPasswordField.getText().toString(),
                        verifyPasswordField.getText().toString());

            }
        });
    }

    public void setPasswordFieldsToEmpty() {
        EditText currentPasswordField = (EditText) findViewById(R.id.current_password_field);
        currentPasswordField.setText("");

        EditText newPasswordField = (EditText) findViewById(R.id.new_password_field);
        newPasswordField.setText("");

        EditText verifyNewPasswordField = (EditText) findViewById(R.id.verify_new_password_field);
        verifyNewPasswordField.setText("");
    }

    private void saveAccountSettings(String oldPassword,
                                     String newPassword,
                                     String NewPasswordVerify) {
        //TODO: REPLACE WITH CALLS TO DATABASE!!!
        SharedPreferences prefs = this.getSharedPreferences("AccountSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //REPLACE WITH CALL TO GET PREVIOUS PASSWORD FROM DB
        String prevPassword = prefs.getString("Password","null password");
        if (prevPassword.equals(oldPassword)) {
            //Password entered is correct w/ password on file
            if (newPassword.equals(NewPasswordVerify)) {
                //new password entered is equal in both entered fields
                //TODO: replace with call to replace account password from DB
                editor.putString("Password", newPassword);
                Toast.makeText(SettingsAccount.this, "Password Change Successful",Toast.LENGTH_SHORT).show();
                setPasswordFieldsToEmpty();
            }
            else {
                Toast.makeText(SettingsAccount.this, "New Passwords don't match", Toast.LENGTH_SHORT).show();
                setPasswordFieldsToEmpty();
            }
        }
        else {
            Toast.makeText(SettingsAccount.this, "The current password you've entered is incorrect!",Toast.LENGTH_SHORT).show();
            setPasswordFieldsToEmpty();
        }
        editor.apply();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.account_settings);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_dog) {
            goToSettingsDog();
            finish();
            return true;
            // Handle the camera action
        } else if (id == R.id.nav_account) {
           // goToSettingsAccount();
            //finish();
            //THIS IS THIS ACTIVITY
            return true;

        } else if (id == R.id.nav_bluetooth) {
            goToSettingsBluetooth();
            finish();
            return true;

        } else if (id == R.id.nav_notification) {
            goToSettingsNotification();
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dog_overview){
            goToDogOverview();
            finish();
            //TODO: CONSIDER SEPERATING XML FILES FOR EACH ACTIVITY
        }
        else if (id == R.id.nav_heart_rate){
            finish();
            //TODO: nav to heart rate specific page
        }
        else if (id == R.id.nav_resp_rate){
            //TODO: nav to resp rate specific page
            finish();
        }
        else if (id == R.id.nav_core_temp){
            //TODO: nav to core temp specific page
            finish();
        }
        else if (id == R.id.nav_ab_temp){
            finish();
            //TODO: nav to ab temp specific page
        }
        else if (id == R.id.nav_logOut) {
            finish();
            //TODO: logout function!

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.account_settings);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToSettingsDog() {
        Intent goToSettingsDogIntent = new Intent(this, SettingsDog.class);
        startActivity(goToSettingsDogIntent);
    }

    public void goToSettingsAccount() {
        Intent goToSettingsAccountIntent = new Intent(this, SettingsAccount.class);
        startActivity(goToSettingsAccountIntent);
    }

    public void goToSettingsBluetooth() {
        Intent goToSettingsBluetoothIntent = new Intent(this, SettingsBluetooth.class);
        startActivity(goToSettingsBluetoothIntent);
    }

    public void goToSettingsNotification() {
        Intent goToSettingsNotificationIntent = new Intent(this, SettingsNotifications.class);
        startActivity(goToSettingsNotificationIntent);
    }
    public void goToDogOverview() {
        Intent DogIntent = new Intent(this, DogOverview.class);
        startActivity(DogIntent);
    }
}
