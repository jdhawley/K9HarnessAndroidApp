package com.example.android.k9harnessandroidapp;

import java.util.Random;

class DataMockup {
    private Random r;

    DataMockup(){
        r = new Random();
    }

    // XXX:XXX:XXX:XXX:XXX#
    // HeartRate: RespiratoryRate: AbdominalTemp: AmbientTemp: ChestTemp#
    // 60-140: 10-35: 100-103: 40-100: 100-103
    String getDataPoint(){
        int hr = r.nextInt(41) + 60;
        int rr = r.nextInt(26) + 10;
        int ct = r.nextInt(3) + 101;
        int amt = r.nextInt(3) + 101;
        int abt = r.nextInt(3) + 101;
        return hr + ":" + rr + ":" + ct + ":" + amt + ":" + abt + "#";
    }
}
