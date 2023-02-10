package com.stackbuffers.myguardianangels.Models;

public class Google_Signup_Response {

    public int status;
    public Google_Signup_Model data;
    public String message;

    public Google_Signup_Response() {
    }

    public Google_Signup_Response(int status, Google_Signup_Model data, String message) {
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

    public Google_Signup_Model getData() {
        return data;
    }

    public void setData(Google_Signup_Model data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
