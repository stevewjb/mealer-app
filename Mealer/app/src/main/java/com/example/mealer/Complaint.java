package com.example.mealer;

public class Complaint {
    private String id;
    private String description;
    private String clientId, cookId;

    public Complaint() {

    }

    public Complaint(String description, String clientId, String cookId) {
        this.description = description;
        this.clientId = clientId;
        this.cookId = cookId;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCookId() {
        return cookId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }
}
