package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import java.util.Set;
//TODO: SET PASSWORD REQS... find out what DB can support!!
public class SettingsAccount extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);

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
}
