package com.example.database.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.OffsetDateTime;
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;
    @NotNull
    @Column(nullable = false)
    private String password;
    @NotNull
    @Column(nullable = false)
    private boolean deleted;
    @NotNull
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AllChat allChat;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.deleted = false;
        setTime();
    }

    @PrePersist
    public void setTime() {
        this.createdAt = OffsetDateTime.now();
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

    public AllChat getAllChat() {
        return allChat;
    }

    public void setAllChat(AllChat allChat) {
        this.allChat = allChat;
    }

}
