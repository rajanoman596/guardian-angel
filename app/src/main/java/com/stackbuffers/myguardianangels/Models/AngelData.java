package com.stackbuffers.myguardianangels.Models;

public class AngelData {

    public int id;
    public String user_id;
    public String angel_id;
    public String angel_relation;
    public String name;
    public String email;

    public AngelData() {
    }

    public AngelData(int id, String user_id, String angel_id, String angel_relation, String name, String email) {
        this.id = id;
        this.user_id = user_id;
        this.angel_id = angel_id;
        this.angel_relation = angel_relation;
        this.name = name;
        this.email = email;
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

    public String getAngel_relation() {
        return angel_relation;
    }

    public void setAngel_relation(String angel_relation) {
        this.angel_relation = angel_relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
