package com.example.healthremainder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppDataDTO implements Serializable {
    ArrayList<AppointmentDTO> appointmentDTOS;
    ArrayList<MedicineDTO> medicineDTOS;
    ArrayList<NotesDTO> notesDTOS;

    public ArrayList<AppointmentDTO> getAppointmentDTOS() {
        return appointmentDTOS;
    }

    public void setAppointmentDTOS(ArrayList<AppointmentDTO> appointmentDTOS) {
        this.appointmentDTOS = appointmentDTOS;
    }

    public ArrayList<MedicineDTO> getMedicineDTOS() {
        return medicineDTOS;
    }

    public void setMedicineDTOS(ArrayList<MedicineDTO> medicineDTOS) {
        this.medicineDTOS = medicineDTOS;
    }

    public ArrayList<NotesDTO> getNotesDTOS() {
        return notesDTOS;
    }

    public void setNotesDTOS(ArrayList<NotesDTO> notesDTOS) {
        this.notesDTOS = notesDTOS;
    }
}
