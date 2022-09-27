package com.nahidd.dholaimama.model;

public class SurveyInfo {
    private String customer_id,total_job_holder,monthly_landry_cost,time_and_date;

    public SurveyInfo() {
    }

    public SurveyInfo(String customer_id, String total_job_holder, String monthly_landry_cost, String time_and_date) {
        this.customer_id = customer_id;
        this.total_job_holder = total_job_holder;
        this.monthly_landry_cost = monthly_landry_cost;
        this.time_and_date = time_and_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTotal_job_holder() {
        return total_job_holder;
    }

    public void setTotal_job_holder(String total_job_holder) {
        this.total_job_holder = total_job_holder;
    }

    public String getMonthly_landry_cost() {
        return monthly_landry_cost;
    }

    public void setMonthly_landry_cost(String monthly_landry_cost) {
        this.monthly_landry_cost = monthly_landry_cost;
    }

    public String getTime_and_date() {
        return time_and_date;
    }

    public void setTime_and_date(String time_and_date) {
        this.time_and_date = time_and_date;
    }
}
