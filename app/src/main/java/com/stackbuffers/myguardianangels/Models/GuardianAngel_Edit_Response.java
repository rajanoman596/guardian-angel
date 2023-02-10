package com.stackbuffers.myguardianangels.Models;

public class GuardianAngel_Edit_Response {

    public int status;
    public Angel data;
    public String message;

    public GuardianAngel_Edit_Response() {
    }

    public GuardianAngel_Edit_Response(int status, Angel data, String message) {
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

    public Angel getData() {
        return data;
    }

    public void setData(Angel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
