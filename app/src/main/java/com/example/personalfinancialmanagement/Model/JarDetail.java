package com.example.personalfinancialmanagement.Model;

public class JarDetail {
    private Integer idJarDetail;
    private int idUser;
    private int idJar;
    private Long money;
    private String date;

    public JarDetail() {
    }

    public JarDetail(Integer idJarDetail, int idUser, int idJar, Long money, String date) {
        this.idJarDetail = idJarDetail;
        this.idUser = idUser;
        this.idJar = idJar;
        this.money = money;
        this.date = date;
    }

    public Integer getIdJarDetail() {
        return idJarDetail;
    }

    public void setIdJarDetail(int idJarDetail) {
        this.idJarDetail = idJarDetail;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdJar() {
        return idJar;
    }

    public void setIdJar(int idJar) {
        this.idJar = idJar;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
