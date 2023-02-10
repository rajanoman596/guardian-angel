package com.stackbuffers.myguardianangels.Models;

import java.util.ArrayList;

public class Login_Response {
    public int status;
    public ArrayList<User> data;
    public String message;

    public Login_Response() {
    }

    public Login_Response(int status, ArrayList<User> data, String message) {
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

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
