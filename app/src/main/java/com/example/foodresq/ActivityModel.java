package com.example.foodresq;

public class ActivityModel {

    private String res_name;
    private String food_qty;
    private String food_type;
    private String food_desc;

    public ActivityModel(String res_name, String food_qty, String food_type, String food_desc){
        this.res_name = res_name;
        this.food_qty = food_qty;
        this.food_type = food_type;
        this.food_desc = food_desc;
    }


    public String getFood_qty() {
        return food_qty;
    }

    public void setFood_qty(String food_qty) {
        this.food_qty = food_qty;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getFood_desc() {
        return food_desc;
    }

    public void setFood_desc(String food_desc) {
        this.food_desc = food_desc;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }
}
