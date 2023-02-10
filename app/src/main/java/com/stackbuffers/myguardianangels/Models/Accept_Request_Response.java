package com.stackbuffers.myguardianangels.Models;

public class Accept_Request_Response {

    public int status;
    public Accept_Request_Model data;
    public String message;

    public Accept_Request_Response() {
    }

    public Accept_Request_Response(int status, Accept_Request_Model data, String message) {
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

    public Accept_Request_Model getData() {
        return data;
    }

    public void setData(Accept_Request_Model data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
