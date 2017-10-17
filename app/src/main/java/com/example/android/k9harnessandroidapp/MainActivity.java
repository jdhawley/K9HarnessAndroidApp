package com.example.android.k9harnessandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Test();
    }

    private void Test(){
        DataMockup dm = new DataMockup();

        Toast toast = Toast.makeText(getApplicationContext(), dm.getDataPoint(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
