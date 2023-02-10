package com.stackbuffers.myguardianangels.Models;

import java.util.List;

public class CountryResponse {
    public int status;
    public List<Country> data;
    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Country> getData() {
        return data;
    }

    public void setData(List<Country> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
