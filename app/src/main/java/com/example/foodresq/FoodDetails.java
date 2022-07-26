package com.example.foodresq;

public class FoodDetails {
    private String foodType, foodQty, bestBefore, description, foodOrderStatus, user;

    FoodDetails(String foodType, String foodQty, String bestBefore, String description, String foodOrderStatus, String user){
        this.foodType = foodType;
        this.foodQty = foodQty;
        this.bestBefore = bestBefore;
        this.description = description;
        this.foodOrderStatus = foodOrderStatus;
        this.user = user;
    }

    public String getFoodType(){
        return this.foodType;
    }

    public void setFoodType(String foodType){
        this.foodType = foodType;
    }

    public String getFoodQty(){
        return this.foodQty;
    }

    public void setFoodQty(String foodQty){
        this.foodQty = foodQty;
    }

    public String getBestBefore(){
        return this.bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodOrderStatus() {
        return this.foodOrderStatus;
    }

    public void setFoodOrderStatus(String foodOrderStatus) {
        this.foodOrderStatus = foodOrderStatus;
    }
    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
