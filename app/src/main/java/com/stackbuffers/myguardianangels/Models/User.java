package com.stackbuffers.myguardianangels.Models;

import java.util.Date;

public class User {

    public int id;
    public String name;
    public String email;
    public String phone;
    public String dob;
    public String gender;
    public Object country_code;
    public String country;
    public String city;
    public String address;
    public String avatar;
    public String is_social;
    public String role;
    public Object email_verified_at;
    public String timePeriod;
    public Date created_at;
    public Date updated_at;

    public User() {
    }

    public User(int id, String name, String email, String phone, String dob, String gender, Object country_code, String country, String city, String address, String avatar, String is_social, String role, Object email_verified_at, String timePeriod, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.country_code = country_code;
        this.country = country;
        this.city = city;
        this.address = address;
        this.avatar = avatar;
        this.is_social = is_social;
        this.role = role;
        this.email_verified_at = email_verified_at;
        this.timePeriod = timePeriod;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getCountry_code() {
        return country_code;
    }

    public void setCountry_code(Object country_code) {
        this.country_code = country_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIs_social() {
        return is_social;
    }

    public void setIs_social(String is_social) {
        this.is_social = is_social;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(Object email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
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
