package com.example.andrei.myapplication.model;

import com.example.andrei.myapplication.model.UserRegister;

public class RegisterPost {

    private UserRegister userRegister;

    public RegisterPost(UserRegister userRegister) {
        this.userRegister = userRegister;
    }
    /* private String email;
    private String password,confirmPassword;
    private String firstName,lastName;

    public RegisterPost(String email, String password, String confirmPassword, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }*/
}
