package com.example.android.k9harnessandroidapp.domain;

/**
 * Created by grc on 11/29/17.
 */

public class Dog {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Double coretemp;
    private Double respiratoryrate;
    private Double abtemp;
    private String name;
    private Double heartrate;
    private Double maxcoretemp;
    private Double maxrespiratoryrate;
    private Double maxabtemp;
    private Double maxheartrate;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoretemp() {
        return coretemp;
    }

    public Dog coretemp(Double coretemp) {
        this.coretemp = coretemp;
        return this;
    }

    public void setCoretemp(Double coretemp) {
        this.coretemp = coretemp;
    }

    public Double getRespiratoryrate() {
        return respiratoryrate;
    }

    public Dog respiratoryrate(Double respiratoryrate) {
        this.respiratoryrate = respiratoryrate;
        return this;
    }

    public void setRespiratoryrate(Double respiratoryrate) {
        this.respiratoryrate = respiratoryrate;
    }

    public Double getAbtemp() {
        return abtemp;
    }

    public Dog abtemp(Double abtemp) {
        this.abtemp = abtemp;
        return this;
    }

    public void setAbtemp(Double abtemp) {
        this.abtemp = abtemp;
    }

    public String getName() {
        return name;
    }

    public Dog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeartrate() {
        return heartrate;
    }

    public Dog heartrate(Double heartrate) {
        this.heartrate = heartrate;
        return this;
    }

    public void setHeartrate(Double heartrate) {
        this.heartrate = heartrate;
    }

    public Double getMaxcoretemp() {
        return maxcoretemp;
    }

    public Dog maxcoretemp(Double maxcoretemp) {
        this.maxcoretemp = maxcoretemp;
        return this;
    }

    public void setMaxcoretemp(Double maxcoretemp) {
        this.maxcoretemp = maxcoretemp;
    }

    public Double getMaxrespiratoryrate() {
        return maxrespiratoryrate;
    }

    public Dog maxrespiratoryrate(Double maxrespiratoryrate) {
        this.maxrespiratoryrate = maxrespiratoryrate;
        return this;
    }

    public void setMaxrespiratoryrate(Double maxrespiratoryrate) {
        this.maxrespiratoryrate = maxrespiratoryrate;
    }

    public Double getMaxabtemp() {
        return maxabtemp;
    }

    public Dog maxabtemp(Double maxabtemp) {
        this.maxabtemp = maxabtemp;
        return this;
    }

    public void setMaxabtemp(Double maxabtemp) {
        this.maxabtemp = maxabtemp;
    }

    public Double getMaxheartrate() {
        return maxheartrate;
    }

    public Dog maxheartrate(Double maxheartrate) {
        this.maxheartrate = maxheartrate;
        return this;
    }
}
