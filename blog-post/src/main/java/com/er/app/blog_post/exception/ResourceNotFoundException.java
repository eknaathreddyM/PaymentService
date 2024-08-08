package com.er.app.blog_post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private  String filedName;
    private Long filedValue;

    public ResourceNotFoundException(String resourceName, String filedName, Long filedValue) {
        super(String.format("%s not found with the %s : %s",resourceName, filedName, filedValue));
        this.filedName = filedName;
        this.filedValue = filedValue;
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getFiledValue() {
        return filedValue;
    }

    public void setFiledValue(Long filedValue) {
        this.filedValue = filedValue;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }
}
