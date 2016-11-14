package com.rk.capstone.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Domain Class for Campaign
 */
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long campaignId;

    private Long customerId;

    private String name;
    private String strength;
    private String summary;
    private String closeStatus;

    private Date startDate;
    private Date closeDate;

    private int revenue;

    @ManyToOne
    @JsonBackReference
    private User owner;

    protected Campaign() {}

    public Campaign(Long customerId, String name, String strength, String summary, String closeStatus,
                    Date startDate, Date closeDate, int revenue, User owner) {
        this.customerId = customerId;
        this.name = name;
        this.strength = strength;
        this.summary = summary;
        this.closeStatus = closeStatus;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.revenue = revenue;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "campaignId=" + campaignId +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", strength='" + strength + '\'' +
                ", summary='" + summary + '\'' +
                ", closeStatus='" + closeStatus + '\'' +
                ", startDate=" + startDate +
                ", closeDate=" + closeDate +
                ", revenue=" + revenue +
                ", owner=" + owner +
                '}';
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
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

    public String getCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(String closeStatus) {
        this.closeStatus = closeStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
