package com.ums.PAYLOAD;

import java.util.Date;

public class ErrorDetails {
    private String message;
    private Date date;
    private String description;


    public ErrorDetails(String message, Date date, String description) {
        this.message = message;
        this.date = date;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
