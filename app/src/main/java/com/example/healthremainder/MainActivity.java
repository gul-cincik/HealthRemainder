package com.example.healthremainder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.allyants.notifyme.NotifyMe;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Appointment listesine gecen activity
        Button calender_button = (Button) findViewById(R.id.appointment_button);
        calender_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openAppointmentListActivity();
            }
        });

        //notes listesine gecen activity
        Button appointment_button = (Button) findViewById(R.id.note_button);
        appointment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openNotesListActivity();
            }
        });

        //medicine listesine geciren activit
        Button medicine_button = (Button) findViewById(R.id.medicine_button);
        medicine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicineListActivity();
            }
        });

        //bütün dataları yani listeleri appDatada toplar
        FileHelper fileHelper = new FileHelper(this);
        String json = fileHelper.ReadFromFile();
        Gson gson = new Gson();
        AppDataDTO appDataDTO = gson.fromJson(json,AppDataDTO.class);

        if(appDataDTO == null) {
            appDataDTO = new AppDataDTO();
            appDataDTO.notesDTOS = new ArrayList<>();
            appDataDTO.appointmentDTOS = new ArrayList<>();
            appDataDTO.medicineDTOS = new ArrayList<>();
            String appDataJson = gson.toJson(appDataDTO);
            fileHelper.WriteToFile(appDataJson);
        }
    }


    private void openMedicineListActivity() {
        Intent intent = new Intent(this, MedicineListActivity.class);
        startActivity(intent);
    }

    public void openNotesListActivity(){
        Intent intent = new Intent(this, NotesListActivity.class);
        startActivity(intent);
    }

    public void openAppointmentListActivity(){
        Intent intent = new Intent(this, AppointmentListActivity.class);
        startActivity(intent);
    }

}
