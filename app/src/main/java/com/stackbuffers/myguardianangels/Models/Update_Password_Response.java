package com.stackbuffers.myguardianangels.Models;

public class Update_Password_Response {

    public int status;
    public String message;

    public Update_Password_Response() {
    }

    public Update_Password_Response(int status) {
        this.status = status;
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
