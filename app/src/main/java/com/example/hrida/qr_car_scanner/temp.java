package com.example.hrida.qr_car_scanner;

public class temp {
    private String Fname, Lname, CarMod, Check;

    public temp(String Fname, String Lname, String CarMod, String Check) {

        this.Fname = Fname;
        this.Lname = Lname;
        this.CarMod = CarMod;
        this.Check = Check;
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
}
