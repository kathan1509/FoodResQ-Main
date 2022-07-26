package com.example.foodresq;

public class UserProfile {
    private String uName,uEmailID, uType, uAddress;
    private String uContact;

    UserProfile(String uName,String uType, String uEmailID, String uAddress, String uContact){
        this.uName = uName;
        this.uEmailID = uEmailID;
        this.uType = uType;
        this.uAddress = uAddress;
        this.uContact = uContact;
    }

    public String getuName(){
        return this.uName;
    }

    public void setuName(String uName){
        this.uName = uName;
    }

    public String getuEmailID(){
        return this.uEmailID;
    }

    public void setuEmailID(String uEmailID){
        this.uEmailID = uEmailID;
    }

    public String getuType(){
        return this.uType;
    }

    public void setuType(String uType){
        this.uType = uType;
    }

    public String getuAddress(){
        return this.uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public String getuContact() {
        return this.uContact;
    }

    public void setuContact(String uContact) {
        this.uContact = uContact;
    }
}
