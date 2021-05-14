package com.example.healthremainder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.allyants.notifyme.Notification;
import com.allyants.notifyme.NotifyMe;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Time;
import java.util.Calendar;



public class Medicine extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {




    Calendar cal = Calendar.getInstance();
    TimePickerDialog tpd;
    private int stomachId;

    MedicineDTO medDTO = new MedicineDTO();

    FileHelper fileHelper;

    TextView medicine_name;
    TextView rep_medicine;
    TextView medicine_time_textview;
    TextView medicine_day;
    Spinner spinner_medicine;
    Button medicine_save_button;
    Integer delay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        fileHelper = new FileHelper(this);

        medicine_name = findViewById(R.id.medicine_name_text);
        rep_medicine = findViewById(R.id.day_rep_medicine);
        medicine_time_textview = findViewById(R.id.textview_time_medicine);
        medicine_day = findViewById(R.id.medicine_day);
        spinner_medicine = findViewById(R.id.spinner_stomach);
        medicine_save_button = findViewById(R.id.medicine_save_button);


        tpd = TimePickerDialog.newInstance(Medicine.this,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND),
                true);

        spinner_medicine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stomachId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        medicine_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMedicine();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_medicine);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicineListActivity();
            }
        });
    }



    private void SaveMedicine() {
        //Read appdata from internal storage as json string
        String appDataJson = fileHelper.ReadFromFile();
        //Serialize json data to appDataDTO
        Gson gson = new Gson();
        AppDataDTO  appDataDTO = gson.fromJson(appDataJson,AppDataDTO.class);

        medDTO.setMedCalender(cal);
        medDTO.setMedicine_name(medicine_name.getText().toString());
        medDTO.setRep_medicine(Integer.parseInt(rep_medicine.getText().toString()));
        medDTO.setMedicine_day(Integer.parseInt(medicine_day.getText().toString()));
        medDTO.setStomach(stomachId);

        if(Integer.parseInt(rep_medicine.getText().toString()) == 1)
            delay = 0;
        //Add saved medicineDTO to MedicineDTO List in appDataDTO
        appDataDTO.medicineDTOS.add(medDTO);
        String appDataToWrite = gson.toJson(appDataDTO);
        fileHelper.WriteToFile(appDataToWrite);

        Switch alarm_medicine = findViewById(R.id.alarm_medicine);
        if(alarm_medicine.isChecked()) {
            int med_day = medDTO.medicine_day;
            int med_rep = medDTO.rep_medicine;
            int interval = 24 / med_rep;
            int count = med_day * med_rep;
            Intent med_int = new Intent(Medicine.this, MedicineNotificationRespondActivity.class);
            String minute;
            if (medDTO.medCalender.get(Calendar.MINUTE) < 10) {
                minute = "0" + medDTO.medCalender.get(Calendar.MINUTE);
            }
            else minute = String.valueOf(medDTO.medCalender.get(Calendar.MINUTE));
            med_int.putExtra("medicineName", medicine_name.getText());
            med_int.putExtra("hourOfDay", medDTO.medCalender.get(Calendar.HOUR_OF_DAY));
            med_int.putExtra("minute", medDTO.medCalender.get(Calendar.MINUTE));
            NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                    .title("Medicine")
                    .time(cal)
                    .content("You need to take" + " " +
                            medicine_name.getText() + "\n" +
                            "at" + " " +
                            medDTO.medCalender.get(Calendar.HOUR_OF_DAY) + ":" + minute)
                    .color(184, 0, 0, 255)
                    .addAction(new Intent(), "Taken", true)
                    .key(medicine_name.getText())
                    .addAction(med_int, "Delay", false)
                    .addAction(new Intent(), "Dismiss", true)
                    .rrule("FREQ=HOURLY; INTERVAL= "+ String.valueOf(interval) +"; COUNT="+ String.valueOf(count))
                    .dstart(cal)
                    .small_icon(R.drawable.medicine)
                    .build();
        }


        openMedicineListActivity();
    }


    public void showTimePicker(View view) {
        tpd.show(getFragmentManager(), "TimePickerDialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        String hour_string = Integer.toString(hourOfDay);
        String minute_string = Integer.toString(minute);
        String timeMessage = (hour_string + ":" + minute_string);
        medicine_time_textview.setText(timeMessage);
    }


    private void openMedicineListActivity() {
        Intent intent = new Intent(this, MedicineListActivity.class);
        startActivity(intent);
    }
}
