package com.example.mealer;

public class User {
    private String username;
    private String password;
    private String role;

    private String id;
    private String status;
    private String rating;

    private String firstName;
    private String lastName;
    private String cheque;
    private String address;
    private String description;
    private String creditCard;

    public User() {

    }

    public User(String username, String password, String role, String firstName, String lastName,
                String cheque, String address, String description) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cheque = cheque;
        this.address = address;
        this.description = description;
    }

    public User(String username, String password, String role, String firstName, String lastName,
                String address, String creditCard) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.creditCard = creditCard;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getRating() {
        return rating;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCheque() {
        return cheque;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
