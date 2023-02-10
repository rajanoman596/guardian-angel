package com.stackbuffers.myguardianangels.Models;

import java.util.Date;

public class Angel {

    public int id;
    public String user_id;
    public String angel_id;
    public String angel_name;
    public String angel_email;
    public String angel_relation;
    public String status;
    public Date created_at;
    public Date updated_at;

    public Angel() {
    }

    public Angel(int id, String user_id, String angel_id, String angel_name, String angel_email, String angel_relation, String status, Date created_at, Date updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.angel_id = angel_id;
        this.angel_name = angel_name;
        this.angel_email = angel_email;
        this.angel_relation = angel_relation;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAngel_id() {
        return angel_id;
    }

    public void setAngel_id(String angel_id) {
        this.angel_id = angel_id;
    }

    public String getAngel_name() {
        return angel_name;
    }

    public void setAngel_name(String angel_name) {
        this.angel_name = angel_name;
    }

    public String getAngel_email() {
        return angel_email;
    }

    public void setAngel_email(String angel_email) {
        this.angel_email = angel_email;
    }

    public String getAngel_relation() {
        return angel_relation;
    }

    public void setAngel_relation(String angel_relation) {
        this.angel_relation = angel_relation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
