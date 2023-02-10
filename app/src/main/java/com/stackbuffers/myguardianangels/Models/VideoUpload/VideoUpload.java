package com.stackbuffers.myguardianangels.Models.VideoUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoUpload {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;

}
