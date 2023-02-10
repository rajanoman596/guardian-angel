package com.stackbuffers.myguardianangels.Models;

public class GetProfile_Response {
    public int status;
    public User data;
    public String message;

    public GetProfile_Response() {
    }

    public GetProfile_Response(int status, User data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
