package com.example.personalfinancialmanagement.Model;

public class Jar {
    private int idJar;
    private String jarName;

    public Jar(){}
    public Jar(int idJar, String jarName) {
        this.idJar = idJar;
        this.jarName = jarName;
    }

    public int getIdJar() {
        return idJar;
    }

    public void setIdJar(int idJar) {
        this.idJar = idJar;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }
}
