package com.example.android.k9harnessandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsDog extends AppCompatActivity {

     private static final int DOG_SETTINGS_NUM = 8;

     private static final String HEART_RATE_HIGH_KEY = "Heart Rate High";
     private static final String HEART_RATE_LOW_KEY = "Heart Rate Low";
     private static final String RESP_RATE_HIGH_KEY = "Resp Rate High";
     private static final String RESP_RATE_LOW_KEY = "Resp Rate Low";
     private static final String CORE_TEMP_HIGH_KEY = "Core Temp High";
     private static final String CORE_TEMP_LOW_KEY = "Core Temp Low";
     private static final String AB_TEMP_HIGH_KEY = "Ab Temp High";
     private static final String AB_TEMP_LOW_KEY = "Ab Temp Low";

     private static final int HEART_RATE_HIGH_LOC = 0;
     private static final int HEART_RATE_LOW_LOC = 1;
     private static final int RESP_RATE_HIGH_LOC = 2;
     private static final int RESP_RATE_LOW_LOC = 3;
     private static final int CORE_TEMP_HIGH_LOC = 4;
     private static final int CORE_TEMP_LOW_LOC = 5;
     private static final int AB_TEMP_HIGH_LOC = 6;
     private static final int AB_TEMP_LOW_LOC = 7;

    /* TODO
    Add real time checking to integer value input to make sure input is valid.

    Implement using text Watcher action.
    
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_dog);


        initializeDogSettings(this);
        Button submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Placing all of the fields in Dog Settings into an integer array to save.
                int[] dogSettingsArray = new int[DOG_SETTINGS_NUM];

                EditText heartRateHighInput = (EditText) findViewById(R.id.setting_hr_high);
                dogSettingsArray[HEART_RATE_HIGH_LOC] = Integer.parseInt(heartRateHighInput.getText().toString());

                EditText heartRateLowInput = (EditText) findViewById(R.id.setting_hr_low);
                dogSettingsArray[HEART_RATE_LOW_LOC] = Integer.parseInt(heartRateLowInput.getText().toString());

                EditText respRateHighInput = (EditText) findViewById(R.id.setting_rr_high);
                dogSettingsArray[RESP_RATE_HIGH_LOC] = Integer.parseInt(respRateHighInput.getText().toString());

                EditText respRateLowInput = (EditText) findViewById(R.id.setting_rr_low);
                dogSettingsArray[RESP_RATE_LOW_LOC] = Integer.parseInt(respRateLowInput.getText().toString());

                EditText coreTempHighInput = (EditText) findViewById(R.id.setting_ct_high);
                dogSettingsArray[CORE_TEMP_HIGH_LOC] = Integer.parseInt(coreTempHighInput.getText().toString());

                EditText coreTempLowInput = (EditText) findViewById(R.id.setting_ct_low);
                dogSettingsArray[CORE_TEMP_LOW_LOC] = Integer.parseInt(coreTempLowInput.getText().toString());

                EditText abTempHighInput = (EditText) findViewById(R.id.setting_at_high);
                dogSettingsArray[AB_TEMP_HIGH_LOC] = Integer.parseInt(abTempHighInput.getText().toString());

                EditText abTempLowInput = (EditText) findViewById(R.id.setting_at_low);
                dogSettingsArray[AB_TEMP_LOW_LOC] = Integer.parseInt(abTempLowInput.getText().toString());

                saveDogSettings(dogSettingsArray);
            }
        });

    }

    public void initializeDogSettings(Context context) {
        int[] InitValArray = getDogSettings(context);
        EditText heartRateHighInput = (EditText) findViewById(R.id.setting_hr_high);
        heartRateHighInput.setText("" + InitValArray[HEART_RATE_HIGH_LOC]);

        EditText heartRateLowInput = (EditText) findViewById(R.id.setting_hr_low);
        heartRateLowInput.setText("" + InitValArray[HEART_RATE_LOW_LOC]);

        EditText respRateHighInput = (EditText) findViewById(R.id.setting_rr_high);
        respRateHighInput.setText("" + InitValArray[RESP_RATE_HIGH_LOC]);

        EditText respRateLowInput = (EditText) findViewById(R.id.setting_rr_low);
        respRateLowInput.setText("" + InitValArray[RESP_RATE_LOW_LOC]);

        EditText coreTempHighInput = (EditText) findViewById(R.id.setting_ct_high);
        coreTempHighInput.setText("" + InitValArray[CORE_TEMP_HIGH_LOC]);

        EditText coreTempLowInput = (EditText) findViewById(R.id.setting_ct_low);
        coreTempLowInput.setText("" + InitValArray[CORE_TEMP_LOW_LOC]);

        EditText abTempHighInput = (EditText) findViewById(R.id.setting_at_high);
        abTempHighInput.setText("" + InitValArray[AB_TEMP_HIGH_LOC]);

        EditText abTempLowInput = (EditText) findViewById(R.id.setting_at_low);
        abTempLowInput.setText("" + InitValArray[AB_TEMP_LOW_LOC]);

    }

    private void saveDogSettings(int[] settingsArray) {
        SharedPreferences prefs = this.getSharedPreferences("DogSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HEART_RATE_HIGH_KEY, settingsArray[HEART_RATE_HIGH_LOC]);
        editor.putInt(HEART_RATE_LOW_KEY, settingsArray[HEART_RATE_LOW_LOC]);
        editor.putInt(RESP_RATE_HIGH_KEY, settingsArray[RESP_RATE_HIGH_LOC]);
        editor.putInt(RESP_RATE_LOW_KEY, settingsArray[RESP_RATE_LOW_LOC]);
        editor.putInt(CORE_TEMP_HIGH_KEY, settingsArray[CORE_TEMP_HIGH_LOC]);
        editor.putInt(CORE_TEMP_LOW_KEY, settingsArray[CORE_TEMP_LOW_LOC]);
        editor.putInt(AB_TEMP_HIGH_KEY, settingsArray[AB_TEMP_HIGH_LOC]);
        editor.putInt(AB_TEMP_LOW_KEY, settingsArray[AB_TEMP_LOW_LOC]);
        editor.apply();
    }

    static public int[] getDogSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("DogSettings", MODE_PRIVATE);
        //TODO: Change default value
        int[] array = new int[DOG_SETTINGS_NUM];

        array[HEART_RATE_HIGH_LOC] = prefs.getInt(HEART_RATE_HIGH_KEY, 0);
        array[HEART_RATE_LOW_LOC]  = prefs.getInt(HEART_RATE_LOW_KEY, 0);
        array[RESP_RATE_HIGH_LOC]  = prefs.getInt(RESP_RATE_HIGH_KEY, 0);
        array[RESP_RATE_LOW_LOC]   = prefs.getInt(RESP_RATE_LOW_KEY, 0);
        array[CORE_TEMP_HIGH_LOC]  = prefs.getInt(CORE_TEMP_HIGH_KEY, 0);
        array[CORE_TEMP_LOW_LOC]   = prefs.getInt(CORE_TEMP_LOW_KEY, 0);
        array[AB_TEMP_HIGH_LOC]    = prefs.getInt(AB_TEMP_HIGH_KEY, 0);
        array[AB_TEMP_LOW_LOC]     = prefs.getInt(AB_TEMP_LOW_KEY, 0);

        return array;
    }
}
