package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Dog entity.
 */
public class DogDTO implements Serializable {

    private Long id;

    private Double coretemp;

    private Double respiratoryrate;

    private Double abtemp;

    private String name;

    private Double heartrate;

    private Double lowcoretemp;

    private Double lowrespiratoryrate;

    private Double lowabtemp;

    private Double lowheartrate;

    private Double maxcoretemp;

    private Double maxrespiratoryrate;

    private Double maxabtemp;

    private Double maxheartrate;

    private Long ownsId;

    private String ownsLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoretemp() {
        return coretemp;
    }

    public void setCoretemp(Double coretemp) {
        this.coretemp = coretemp;
    }

    public Double getRespiratoryrate() {
        return respiratoryrate;
    }

    public void setRespiratoryrate(Double respiratoryrate) {
        this.respiratoryrate = respiratoryrate;
    }

    public Double getAbtemp() {
        return abtemp;
    }

    public void setAbtemp(Double abtemp) {
        this.abtemp = abtemp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(Double heartrate) {
        this.heartrate = heartrate;
    }

    public Double getMaxcoretemp() {
        return maxcoretemp;
    }

    public void setMaxcoretemp(Double maxcoretemp) {
        this.maxcoretemp = maxcoretemp;
    }

    public Double getMaxrespiratoryrate() {
        return maxrespiratoryrate;
    }

    public void setMaxrespiratoryrate(Double maxrespiratoryrate) {
        this.maxrespiratoryrate = maxrespiratoryrate;
    }

    public Double getMaxabtemp() {
        return maxabtemp;
    }

    public void setMaxabtemp(Double maxabtemp) {
        this.maxabtemp = maxabtemp;
    }

    public Double getMaxheartrate() {
        return maxheartrate;
    }

    public void setMaxheartrate(Double maxheartrate) {
        this.maxheartrate = maxheartrate;
    }

    public Double getLoworetemp() { return lowcoretemp; }

    public void setLowcoretemp(Double lowcoretemp) {
        this.lowcoretemp = lowcoretemp;
    }

    public Double getLowrespiratoryrate() {
        return lowrespiratoryrate;
    }

    public void setLowrespiratoryrate(Double lowrespiratoryrate) {
        this.lowrespiratoryrate = lowrespiratoryrate;
    }

    public Double getLowabtemp() {
        return lowabtemp;
    }

    public void setLowabtemp(Double lowabtemp) {
        this.maxabtemp = lowabtemp;
    }

    public Double getLowheartrate() {
        return lowheartrate;
    }

    public void setLowheartrate(Double lowheartrate) {
        this.lowheartrate = lowheartrate;
    }

    public Long getOwnsId() {
        return ownsId;
    }

    public void setOwnsId(Long userId) {
        this.ownsId = userId;
    }

    public String getOwnsLogin() {
        return ownsLogin;
    }

    public void setOwnsLogin(String userLogin) {
        this.ownsLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DogDTO dogDTO = (DogDTO) o;
        if(dogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DogDTO{" +
            "id=" + getId() +
            ", coretemp='" + getCoretemp() + "'" +
            ", respiratoryrate='" + getRespiratoryrate() + "'" +
            ", abtemp='" + getAbtemp() + "'" +
            ", name='" + getName() + "'" +
            ", heartrate='" + getHeartrate() + "'" +
            ", maxcoretemp='" + getMaxcoretemp() + "'" +
            ", maxrespiratoryrate='" + getMaxrespiratoryrate() + "'" +
            ", maxabtemp='" + getMaxabtemp() + "'" +
            ", maxheartrate='" + getMaxheartrate() + "'" +
            "}";
    }
}
