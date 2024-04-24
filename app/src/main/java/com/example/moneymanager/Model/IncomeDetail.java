package com.example.moneymanager.Model;

public class IncomeDetail {
    private int idJarDetail;
    private int idIncome;
    private int co_cau;
    private Long detailMoney;

    public IncomeDetail(){}

    public IncomeDetail(int idJarDetail, int idIncome, int co_cau, Long detailMoney) {
        this.idJarDetail = idJarDetail;
        this.idIncome = idIncome;
        this.co_cau = co_cau;
        this.detailMoney = detailMoney;
    }

    public int getIdJarDetail() {
        return idJarDetail;
    }

    public void setIdJarDetail(int idJarDetail) {
        this.idJarDetail = idJarDetail;
    }

    public int getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(int idIncome) {
        this.idIncome = idIncome;
    }

    public int getCo_cau() {
        return co_cau;
    }

    public void setCo_cau(int co_cau) {
        this.co_cau = co_cau;
    }

    public Long getDetailMoney() {
        return detailMoney;
    }

    public void setDetailMoney(Long detailMoney) {
        this.detailMoney = detailMoney;
    }
}
