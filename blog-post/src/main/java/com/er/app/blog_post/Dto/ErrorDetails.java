package com.er.app.blog_post.Dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private HttpStatus http;

    public ErrorDetails(Date timestamp, String message, String details,HttpStatus http) {
        this.details = details;
        this.message = message;
        this.timestamp = timestamp;
        this.http=http;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttp() {
        return http;
    }
}
