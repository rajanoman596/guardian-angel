package com.stackbuffers.myguardianangels.Models;

public class StoreAudio_Response {
    public int status;
    public AudioModel data;
    public String message;

    public StoreAudio_Response() {
    }

    public StoreAudio_Response(int status, AudioModel data, String message) {
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

    public AudioModel getData() {
        return data;
    }

    public void setData(AudioModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
