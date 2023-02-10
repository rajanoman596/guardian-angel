package com.stackbuffers.myguardianangels.Models;

import java.util.Date;

public class AudioModel {

    public String user_id;
    public String duration;
    public String format;
    public String longitude;
    public String latitude;
    public Object file;
    public Date updated_at;
    public Date created_at;
    public int id;

    public AudioModel() {
    }

    public AudioModel(String user_id, String duration, String format, String longitude, String latitude, Object file, Date updated_at, Date created_at, int id) {
        this.user_id = user_id;
        this.duration = duration;
        this.format = format;
        this.longitude = longitude;
        this.latitude = latitude;
        this.file = file;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
