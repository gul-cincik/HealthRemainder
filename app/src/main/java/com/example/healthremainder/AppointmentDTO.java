package com.example.healthremainder;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class AppointmentDTO implements Serializable {

    Calendar appCalendar;
    int department;
    String notes;

    public Calendar getAppCalendar() {
        return appCalendar;
    }

    public void setAppCalendar(Calendar appCalendar) {
        this.appCalendar = appCalendar;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
