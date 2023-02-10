package com.stackbuffers.myguardianangels.Models;

import java.util.ArrayList;

public class Show_Angel_Request {

    public int status;
    public ArrayList<AngelData> data;
    public String message;

    public Show_Angel_Request() {
    }

    public Show_Angel_Request(int status, ArrayList<AngelData> data, String message) {
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

    public ArrayList<AngelData> getData() {
        return data;
    }

    public void setData(ArrayList<AngelData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
