package com.stackbuffers.myguardianangels.Models.myEvidence;

import com.stackbuffers.myguardianangels.Models.AngelData;

import java.util.ArrayList;

public class MyEvidenceResponse {
    public int status;
    public ArrayList<MyEvidence> data;
    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<MyEvidence> getData() {
        return data;
    }

    public void setData(ArrayList<MyEvidence> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
