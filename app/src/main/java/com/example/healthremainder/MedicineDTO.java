package com.example.healthremainder;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;

public class MedicineDTO implements Serializable {

    String medicine_name;
    int rep_medicine;
    int medicine_day;
    int stomach;
    Calendar medCalender;

    public Calendar getMedCalender() {
        return medCalender;
    }

    public void setMedCalender(Calendar medCalender) {
        this.medCalender = medCalender;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public int getRep_medicine() {
        return rep_medicine;
    }

    public void setRep_medicine(int rep_medicine) {
        this.rep_medicine = rep_medicine;
    }

    public int getMedicine_day() {
        return medicine_day;
    }

    public void setMedicine_day(int medicine_day) {
        this.medicine_day = medicine_day;
    }

    public int getStomach() {
        return stomach;
    }

    public void setStomach(int stomach) {
        this.stomach = stomach;
    }


}
