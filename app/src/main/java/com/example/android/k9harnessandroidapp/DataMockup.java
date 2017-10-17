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
        int HR = r.nextInt(81) + 60;
        int RR = r.nextInt(26) + 10;
        int AbT = r.nextInt(4) + 100;
        int AmT = r.nextInt(61) + 40;
        int CT = r.nextInt(4) + 100;

        return HR + ":" + RR + ":" + AbT + ":" + AmT + ":" + CT + "#";
    }
}
