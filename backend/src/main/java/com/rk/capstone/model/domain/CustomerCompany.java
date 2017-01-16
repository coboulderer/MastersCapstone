package com.rk.capstone.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain class representing a Customer Company
 */
@Entity
public class CustomerCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String summary;
    private String initiatives;

    protected  CustomerCompany(){}

    public CustomerCompany(String name, String summary, String initiatives) {
        this.name = name;
        this.summary = summary;
        this.initiatives = initiatives;
    }

    @Override
    public String toString() {
        return "CustomerCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", initiatives='" + initiatives + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInitiatives() {
        return initiatives;
    }

    public void setInitiatives(String initiatives) {
        this.initiatives = initiatives;
    }
}
