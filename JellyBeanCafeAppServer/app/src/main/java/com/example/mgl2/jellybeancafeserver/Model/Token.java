package com.example.mgl2.jellybeancafeserver.Model;

public class Token {
    public String phone;
    public String token;
    public int isServerToken;

    public Token() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIsServerToken() {
        return isServerToken;
    }

    public void setIsServerToken(int isServerToken) {
        this.isServerToken = isServerToken;
    }
}
