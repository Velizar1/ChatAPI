package com.example.database.Models.Binding;

import com.example.database.Entities.AllChat;

import java.time.OffsetDateTime;

public class UserBindingModel {
    private String username;

    private String password;

    private boolean deleted;

    private OffsetDateTime createdAt;

    private AllChat allChat;

    public UserBindingModel() {
    }

    public AllChat getAllChat() {
        return allChat;
    }

    public void setAllChat(AllChat allChat) {
        this.allChat = allChat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
