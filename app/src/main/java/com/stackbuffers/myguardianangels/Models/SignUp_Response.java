package com.stackbuffers.myguardianangels.Models;

public class SignUp_Response {
    public int status;
    public SignUp_Model data;
    public String message;

    public SignUp_Response() {
    }

    public SignUp_Response(int status, SignUp_Model data, String message) {
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

    public SignUp_Model getData() {
        return data;
    }

    public void setData(SignUp_Model data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
