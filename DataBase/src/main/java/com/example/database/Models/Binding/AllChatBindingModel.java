package com.example.database.Models.Binding;

import java.time.OffsetDateTime;

public class AllChatBindingModel {


    private String username;

    private OffsetDateTime addedAt;

    private String message;

    public AllChatBindingModel() {
    }

    public AllChatBindingModel(String username, OffsetDateTime addedAte, String message) {
        this.username = username;
        this.addedAt = addedAte;
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
