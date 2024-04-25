package com.example.moneymanager.Model;

public class User {
    private int id_user;
    private String username, password, email, fullname;

    public User() {
    }

    public User(int id_user, String username, String password, String email, String fullname) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
    }

    public int getId_user() {
        return id_user;
    }

//    public void setId_user(int id_user) {
//        this.id_user = id_user;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
