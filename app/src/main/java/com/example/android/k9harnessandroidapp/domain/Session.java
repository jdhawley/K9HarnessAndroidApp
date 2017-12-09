package com.example.android.k9harnessandroidapp.domain;

import java.util.Date;

import static java.lang.Double.parseDouble;

/**
 * Created by grc on 12/8/17.
 */

public class Session {
    private int dogId;
    private int sessionId;
    private double coreTemp;
    private double respRate;
    private double abTemp;
    private double heartRate;
    private Date sessionDate;

    public Session(int dogId, int sessionId, String parseString) {
        this.dogId = dogId;
        this.sessionId = sessionId;
        String[] sessionString = parseString.split(":");
        this.coreTemp = parseDouble(sessionString[3].substring(0, sessionString[3].length() - 1));
        this.respRate = parseDouble(sessionString[0]);
        this.abTemp = parseDouble(sessionString[1]);
        this.heartRate = parseDouble(sessionString[2]);
        this.sessionDate = new Date();
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

    public double getCoreTemp() {
        return coreTemp;
    }

    public void setCoreTemp(double coreTemp) {
        this.coreTemp = coreTemp;
    }

    public double getRespRate() {
        return respRate;
    }

    public void setRespRate(double respRate) {
        this.respRate = respRate;
    }

    public double getAbTemp() {
        return abTemp;
    }

    public void setAbTemp(double abTemp) {
        this.abTemp = abTemp;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }
}
