package com.stackbuffers.myguardianangels.Models.VideoUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("duration")
    @Expose
    public String duration;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("file")
    @Expose
    public Object file;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("id")
    @Expose
    public Integer id;

}
