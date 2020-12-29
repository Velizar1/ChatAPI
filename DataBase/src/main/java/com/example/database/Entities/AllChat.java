package com.example.database.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Time;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "all_chat")
public class AllChat extends BaseEntity {

    @OneToMany(mappedBy = "allChat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private OffsetDateTime addedAt;

    @NotNull
    @Column(nullable = false)
    private String message;

    public AllChat() {
        users = new HashSet<>();
    }

    public AllChat(Set<User> users, String username, OffsetDateTime addedAt, String message) {
        this.users = users;
        this.username = username;
        this.addedAt = addedAt;
        this.message = message;
    }

    public AllChat(User user, String message) {
        this.username = user.getUsername();
        users = new HashSet<>();
        this.users.add(user);
        setTime();
        this.message = message;

    }

    @PrePersist
    public void setTime() {
        this.addedAt = OffsetDateTime.now();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
