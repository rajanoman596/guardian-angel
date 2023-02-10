package com.stackbuffers.myguardianangels.Models;

public class Verify_Email_Response {

    public int status;
    public String message;

    public Verify_Email_Response() {
    }

    public Verify_Email_Response(int status, String message) {
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
