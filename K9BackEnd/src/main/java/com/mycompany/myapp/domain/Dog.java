package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Dog.
 */
@Entity
@Table(name = "dog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "coretemp")
    private Double coretemp;

    @Column(name = "respiratoryrate")
    private Double respiratoryrate;

    @Column(name = "abtemp")
    private Double abtemp;

    @Column(name = "name")
    private String name;

    @Column(name = "heartrate")
    private Double heartrate;

    @Column(name = "maxcoretemp")
    private Double maxcoretemp;

    @Column(name = "maxrespiratoryrate")
    private Double maxrespiratoryrate;

    @Column(name = "maxabtemp")
    private Double maxabtemp;

    @Column(name = "maxheartrate")
    private Double maxheartrate;

    @ManyToOne
    private User owns;

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

    public void setMaxheartrate(Double maxheartrate) {
        this.maxheartrate = maxheartrate;
    }

    public User getOwns() {
        return owns;
    }

    public Dog owns(User user) {
        this.owns = user;
        return this;
    }

    public void setOwns(User user) {
        this.owns = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dog dog = (Dog) o;
        if (dog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dog{" +
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
