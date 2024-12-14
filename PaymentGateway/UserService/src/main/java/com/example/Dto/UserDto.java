package com.example.Dto;

import jakarta.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private  String kycNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKycNumber() {
        return kycNumber;
    }

    public void setKycNumber(String kycNumber) {
        this.kycNumber = kycNumber;
    }
}
