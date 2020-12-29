package com.example.database.Models.Respond;

import java.time.OffsetDateTime;

public class AllChatRespondModel {
    private boolean isAdded;
    private String information;
    private OffsetDateTime OffsetDateTime;
    private String from;
    private String message;

    public AllChatRespondModel() {
    }

    public AllChatRespondModel(boolean isAdded, String information, OffsetDateTime OffsetDateTime, String from, String message) {
        this.isAdded = isAdded;
        this.information = information;
        this.OffsetDateTime = OffsetDateTime;
        this.from = from;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public OffsetDateTime getOffsetDateTime() {
        return OffsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime OffsetDateTime) {
        this.OffsetDateTime = OffsetDateTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
