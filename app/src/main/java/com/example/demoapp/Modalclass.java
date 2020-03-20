package com.example.demoapp;

public class Modalclass {
    String id = "id";
    String name = "Name";
    String number = "Phone_Number";
    String email = "Email";
    String password = "Password";

    public Modalclass(String id, String name, String number, String email, String password) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
