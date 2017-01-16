package com.rk.capstone.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain Class for Task
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private Long campaignId;
    private Date dueDate;
    private String description;
    private String assignedTo;
    private boolean completed;

    protected Task() {}

    public Task(Long campaignId, Date dueDate, String description, String assignedTo, boolean completed) {
        this.campaignId = campaignId;
        this.dueDate = dueDate;
        this.description = description;
        this.assignedTo = assignedTo;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", campaignId=" + campaignId +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", completed=" + completed +
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
