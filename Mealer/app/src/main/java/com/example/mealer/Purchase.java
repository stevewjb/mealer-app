package com.example.mealer;

public class Purchase {
    private String id;
    private String clientId;
    private String cookId;
    private String cookName;
    private String pickupTime;
    private String mealName;
    private String address;

    private String status;

    public Purchase() {

    }

    public Purchase(String clientId, String cookId, String cookName, String pickupTime, String mealName, String address, String status) {
        this.clientId = clientId;
        this.cookId = cookId;
        this.cookName = cookName;
        this.pickupTime = pickupTime;
        this.mealName = mealName;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCookId() {
        return cookId;
    }

    public String getCookName() {
        return cookName;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getMealName() {
        return mealName;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
