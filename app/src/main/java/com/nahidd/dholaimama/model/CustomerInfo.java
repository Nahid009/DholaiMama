package com.nahidd.dholaimama.model;

public class CustomerInfo {
    private String customer_id, customer_name,customer_address,customer_phone_number,user_id,currentDate;
    private double lati,longi;
    private boolean isInterested;


    public CustomerInfo() {}



    public CustomerInfo(String customer_id, String name, String address, String phone_number, String user_id, String currentDate, double lati, double longi, boolean isChecked) {

        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.customer_phone_number = customer_phone_number;
        this.user_id = user_id;
        this.currentDate = currentDate;
        this.lati = lati;
        this.longi = longi;
        this.isInterested = isInterested;

    }


    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

//    public String getMonthlySurvey(){return monthlySurvey;}

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public void setCustomer_phone_number(String customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public boolean isInterested() {
        return isInterested;
    }

    public void setInterested(boolean interested) {
        isInterested = interested;
    }
}
