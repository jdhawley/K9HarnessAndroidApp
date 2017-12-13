package com.example.android.k9harnessandroidapp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by George on 12/8/2017.
 */

public class Dog {
    private Long id;
    private String name;
    private int dogId ;
    private Integer[] notification;
    private Integer sessionID;
    private double lowCT;
    private double lowAT;
    private double lowHR;
    private double lowRR;
    private double highCT;
    private double highAT;
    private double highHR;
    private double highRR;
    private Long ownedById;
    private String ownedByLogin;

    private List<Session> sessionList;

    public Dog() {

    }

    public Dog(int dogId, String name,
               double lowCT, double lowAT, double lowHR, double lowRR,
               double highCT, double highAT, double highHR, double highRR) {
        this.name = name;
        this.dogId = dogId;
        this.notification = new Integer[]{1, 1, 1, 1};
        this.sessionID = 1;
        this.lowCT = lowCT;
        this.lowAT = lowAT;
        this.lowHR = lowHR;
        this.lowRR = lowRR;
        this.highCT = highCT;
        this.highAT = highAT;
        this.highHR = highHR;
        this.highRR = highRR;
        this.sessionList = new ArrayList<Session>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnedById() {
        return ownedById;
    }

    public void setOwnedById(Long ownedById) {
        this.ownedById = ownedById;
    }

    public String getOwnedByLogin() {
        return ownedByLogin;
    }

    public void setOwnedByLogin(String ownedByLogin) {
        this.ownedByLogin = ownedByLogin;
    }

    public void addSession(Session s) {
        this.sessionList.add(s);
    }

    public List<Session> getSessionList(){
        return this.sessionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int id) {
        this.dogId = id;
    }

    public Integer[] getNotification() {
        return notification;
    }

    public void setNotification(Integer[] notification) {
        this.notification = notification;
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public double getLowCT() {
        return lowCT;
    }

    public void setLowCT(double lowCT) {
        this.lowCT = lowCT;
    }

    public double getLowAT() {
        return lowAT;
    }

    public void setLowAT(double lowAT) {
        this.lowAT = lowAT;
    }

    public double getLowHR() {
        return lowHR;
    }

    public void setLowHR(double lowHR) {
        this.lowHR = lowHR;
    }

    public double getLowRR() {
        return lowRR;
    }

    public void setLowRR(double lowRR) {
        this.lowRR = lowRR;
    }

    public double getHighCT() {
        return highCT;
    }

    public void setHighCT(double highCT) {
        this.highCT = highCT;
    }

    public double getHighAT() {
        return highAT;
    }

    public void setHighAT(double highAT) {
        this.highAT = highAT;
    }

    public double getHighHR() {
        return highHR;
    }

    public void setHighHR(double highHR) {
        this.highHR = highHR;
    }

    public double getHighRR() {
        return highRR;
    }

    public void setHighRR(double highRR) {
        this.highRR = highRR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog)) return false;

        Dog dog = (Dog) o;

        if (getName() != null ? !getName().equals(dog.getName()) : dog.getName() != null)
            return false;
        return getDogId() == dog.getDogId();
    }
}
