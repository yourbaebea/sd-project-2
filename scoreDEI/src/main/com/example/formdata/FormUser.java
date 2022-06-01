package com.example.formdata;

public class FormUser {
    private String name;
    private String password;

    public FormUser(){}

    public FormUser(String name, String password) {
        this.name = name;
        this.password = password;

    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    };

    public void setPassword(String password) {
        this.password = password;
    }

}
