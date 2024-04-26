package com.example.moneymanager.Model;

public class Spending {
    private Integer idSpending;
    private int idJarDetail;
    private Long money;
    private String description;
    private String date;

    public Spending() {
    }

    public Spending(Integer idSpending, int idJarDetail, Long money, String description, String date) {
        this.idSpending = idSpending;
        this.idJarDetail = idJarDetail;
        this.money = money;
        this.description = description;
        this.date = date;
    }

    public int getIdSpending() {
        return idSpending;
    }

    public void setIdSpending(int idSpending) {
        this.idSpending = idSpending;
    }

    public int getIdJarDetail() {
        return idJarDetail;
    }

    public void setIdJarDetail(int idJarDetail) {
        this.idJarDetail = idJarDetail;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
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
