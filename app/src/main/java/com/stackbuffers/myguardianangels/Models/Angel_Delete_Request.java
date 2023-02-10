package com.stackbuffers.myguardianangels.Models;

public class Angel_Delete_Request {
    public int status;
    public String message;

    public Angel_Delete_Request() {
    }

    public Angel_Delete_Request(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
