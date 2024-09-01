package com.lmfm.api.dto;

public class LoginRequest {
    private int legajo;
    private String password;

    public LoginRequest() {}

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

