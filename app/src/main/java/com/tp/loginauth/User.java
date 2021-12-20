package com.tp.loginauth;

public class User {
    public String fullName, email, pass,mobile ;

    public User() {

    }

    public User(String fullName, String email,String pass,String mobile){
        this.fullName = fullName;
        this.email = email;
        this.pass=pass;
        this.mobile=mobile;
    }
}
