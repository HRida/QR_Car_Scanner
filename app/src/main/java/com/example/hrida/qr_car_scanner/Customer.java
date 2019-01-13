package com.example.hrida.qr_car_scanner;

public class Customer {
    private int id;
    private String Fname, Lname, CarMod, Check;

    public Customer(int id, String Fname, String Lname, String CarMod, String Check) {
        this.id = id;
        this.Fname = Fname;
        this.Lname = Lname;
        this.CarMod = CarMod;
        this.Check = Check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return Fname;
    }

    public void setFName(String Fname) {
        this.Fname = Fname;
    }

    public String getLName() {
        return Lname;
    }

    public void setLName(String Lname) {
        this.Lname = Lname;
    }

    public String getCarType() {
        return CarMod;
    }

    public void setCarType(String CarMod) {
        this.CarMod = CarMod;
    }

    public String getCheck() {
        return Check;
    }

    public void setCheck(String Check) {
        this.Check = Check;
    }

    @Override
    public String toString() {
        return "ID: " + id + " ," + "Name: " + Fname + " " + Lname + " ," + "Car Module: " + CarMod + " ," + "CheckUp Date: " + Check;
    }
}
