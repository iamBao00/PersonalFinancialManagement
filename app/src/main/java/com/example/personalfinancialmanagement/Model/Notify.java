package com.example.personalfinancialmanagement.Model;

public class Notify {
    private Integer id_notify;
    private Integer id_user;
    private String title;
    private String description;
    private Integer status;
    private String date;

    public Notify() {
    }

    public Notify(Integer id_notify, Integer id_user, String title, String description, Integer status, String date) {
        this.id_notify = id_notify;
        this.id_user = id_user;
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public int getId_notify() {
        return id_notify;
    }

    public void setId_notify(Integer id_notify) {
        this.id_notify = id_notify;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
