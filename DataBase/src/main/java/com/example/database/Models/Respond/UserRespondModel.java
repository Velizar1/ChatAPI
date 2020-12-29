package com.example.database.Models.Respond;

public class UserRespondModel {
    private String username;
    private boolean exists;
    private String information;
    private boolean authenticated;

    public UserRespondModel() {
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exist) {
        this.exists = exist;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}

