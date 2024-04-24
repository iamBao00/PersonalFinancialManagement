package com.example.moneymanager.Model;

public class Income {
    private Integer idIncome;
    private Long total_money;
    private String description;
    private String date;

    public Income(){}
    public Income(Integer idIncome, Long total_money, String description, String date) {
        this.idIncome = idIncome;
        this.total_money = total_money;
        this.description = description;
        this.date = date;
    }

    public Integer getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(int idIncome) {
        this.idIncome = idIncome;
    }

    public Long getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Long total_money) {
        this.total_money = total_money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
