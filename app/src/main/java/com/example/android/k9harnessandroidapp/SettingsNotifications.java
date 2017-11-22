package com.example.android.k9harnessandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;

public class SettingsNotifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notifications);

        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch(compoundButton.getId()){
                    case R.id.switch_hr_low:
                        break;
                    case R.id.switch_hr_high:
                        break;
                    case R.id.switch_rr_low:
                        break;
                    case R.id.switch_rr_high:
                        break;
                    case R.id.switch_ct_low:
                        break;
                    case R.id.switch_ct_high:
                        break;
                    case R.id.switch_at_low:
                        break;
                    case R.id.switch_at_high:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
