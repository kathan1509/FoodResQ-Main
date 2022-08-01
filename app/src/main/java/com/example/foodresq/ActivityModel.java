package com.example.foodresq;

public class ActivityModel {

    private String res_name;
    private String order_status;
    private String food_qty;
    private String food_type;
    private String food_desc;
    private  String item_ID;

    public ActivityModel(String res_name, String order_status, String food_qty, String food_type, String food_desc,String item_ID){
        this.res_name = res_name;
        this.order_status = order_status;
        this.food_qty = food_qty;
        this.food_type = food_type;
        this.food_desc = food_desc;
        this.item_ID = item_ID;
    }


    public String getFood_qty() {
        return food_qty;
    }

    public void setFood_qty(String food_qty) {
        this.food_qty = food_qty;
    }

    public String getOrder_Status() {
        return order_status;
    }

    public void setOrder_Status(String order_status) {
        this.order_status = order_status;
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

    public String getItem_ID() {
        return item_ID;
    }

    public void setItem_ID(String item_ID) {
        this.item_ID = item_ID;
    }
}
