package com.example.database.Models.Service;

import java.time.OffsetDateTime;

public class AllChatServiceModel {

    private String username;

    private OffsetDateTime addedAt;

    private String message;

    public AllChatServiceModel() {
    }

    public AllChatServiceModel(String username, OffsetDateTime addedAt, String message) {
        this.username = username;
        this.addedAt = addedAt;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OffsetDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(OffsetDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

