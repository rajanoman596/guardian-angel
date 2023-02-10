package com.stackbuffers.myguardianangels.Models;

import android.net.Uri;

public class UploadImage {

    public int status;
    public String url;
    public String message;

    public UploadImage(int status, String url, String message) {
        this.status = status;
        this.url = url;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
