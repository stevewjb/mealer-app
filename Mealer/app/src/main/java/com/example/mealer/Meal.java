package com.example.mealer;

public class Meal {
    private String mealName;
    private String mealType;
    private String cuisineType;
    private String ingredients;
    private String allergens;
    private String price;
    private String description;

    private String id;
    private String status;

    public Meal() {

    }

    public Meal(String mealName, String mealType, String cuisineType, String ingredients,
                String allergens, String price, String description) {
        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.price = price;
        this.description = description;
    }

    public String getMealName() {
        return mealName;
    }

    public String getMealType() {
        return mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getAllergens() {
        return allergens;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
