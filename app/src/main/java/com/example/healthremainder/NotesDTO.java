package com.example.healthremainder;

import java.io.Serializable;

public class NotesDTO implements Serializable {

    String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
