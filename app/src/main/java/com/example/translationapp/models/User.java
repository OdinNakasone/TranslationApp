package com.example.translationapp.models;

import androidx.annotation.NonNull;

public class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email){
        setUsername(username);
        setPassword(password);
        setEmail(email);
    }

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

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    @NonNull
    @Override
    public String toString() {
        return "User: " + getUsername() + "\nPassword: " + getPassword();
    }
}
