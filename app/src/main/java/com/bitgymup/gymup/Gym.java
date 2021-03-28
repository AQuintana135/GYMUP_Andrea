package com.bitgymup.gymup;

import android.widget.ImageView;

public class Gym {
    private int idgym;
    private String name;
    private String email;
    private String phoneNumber;
    private String mobileNumber;
    private int rut;
    //private ImageView logo;


    public Gym(int idgym, String name, String email, String phoneNumber, String mobileNumber, int rut) {
        this.idgym = idgym;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.rut = rut;
    }

    public int getIdgym() {
        return idgym;
    }

    public void setIdgym(int idgym) {
        this.idgym = idgym;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }
}
