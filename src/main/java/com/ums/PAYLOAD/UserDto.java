package com.ums.PAYLOAD;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


public class UserDto {
    private long id;
    @NotEmpty //not null or empty fileds
    @Size(min = 2, message ="NAME SHOULD BE ATLEAST 2 CHARACTERS") //VALIDATE AND IF NO VALID THEN SEND MESSAGE
    private String name;
    private String username;
    @Email
    private String emailId;
    private String password;
    private String userRole;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
