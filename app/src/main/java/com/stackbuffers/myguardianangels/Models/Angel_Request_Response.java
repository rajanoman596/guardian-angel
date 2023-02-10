package com.stackbuffers.myguardianangels.Models;

import java.util.ArrayList;

public class Angel_Request_Response {
    public int status;
    public ArrayList<AngelData> data;
    public String message;

    public Angel_Request_Response() {
    }

    public Angel_Request_Response(int status, ArrayList<AngelData> data, String message) {
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
