package com.example.android.k9harnessandroidapp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Double.parseDouble;

/**
 * Created by grc on 12/8/17.
 */

public class Session {
    private int dogId;
    private int sessionId;
    private Date sessionDate;
    private List<String> sessionTicks;

    public Session(int dogId, int sessionId, String parseString) {
        this.dogId = dogId;
        this.sessionId = sessionId;
        this.sessionDate = new Date();
        this.sessionTicks = new ArrayList<String>();
    }

    private static Double[] parseSessionTick(String parseString){
        String[] sessionString = parseString.split(":");
        Double coreTemp = parseDouble(sessionString[3].substring(0, sessionString[3].length() - 1));
        Double respRate = parseDouble(sessionString[0]);
        Double abTemp = parseDouble(sessionString[1]);
        Double heartRate = parseDouble(sessionString[2]);

        return new Double[]{coreTemp, respRate, abTemp, heartRate};
    }

    public void addSessionTick(String sessionTick) {
        this.sessionTicks.add(sessionTick);
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }
}
