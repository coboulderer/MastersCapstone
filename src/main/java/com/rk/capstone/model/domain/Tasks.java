package com.rk.capstone.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain Class for Tasks
 */
@Entity
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private Long campaignId;
    private Date dueDate;
    private String description;
    private String asignee;
    private boolean isCompleted;

    protected Tasks() {}

    public Tasks(Long campaignId, Date dueDate, String description, String asignee, boolean isCompleted) {
        this.campaignId = campaignId;
        this.dueDate = dueDate;
        this.description = description;
        this.asignee = asignee;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskId=" + taskId +
                ", campaignId=" + campaignId +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                ", asignee='" + asignee + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAsignee() {
        return asignee;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
