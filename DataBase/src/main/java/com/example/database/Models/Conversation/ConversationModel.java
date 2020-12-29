package com.example.database.Models.Conversation;

import java.time.OffsetDateTime;

public class ConversationModel {

    private String from_user;
    private String to_user;
    private String message;
    private OffsetDateTime local_time;

    public ConversationModel() {
    }

    public ConversationModel(String from_user, String to_user, String message) {
        this.from_user = from_user;
        this.to_user = to_user;
        this.message = message;
        this.local_time = OffsetDateTime.now();
    }

    public ConversationModel(String from_user, String to_user, String message, OffsetDateTime local_time) {
        this.from_user = from_user;
        this.to_user = to_user;
        this.message = message;
        this.local_time = local_time;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }

    public OffsetDateTime getLocal_time() {
        return local_time;
    }

    public void setLocal_time(OffsetDateTime local_time) {
        this.local_time = local_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
