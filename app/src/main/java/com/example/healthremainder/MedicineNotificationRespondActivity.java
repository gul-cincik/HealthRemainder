package com.example.healthremainder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.allyants.notifyme.NotifyMe;

import java.util.Calendar;

import static java.util.Calendar.HOUR_OF_DAY;

public class MedicineNotificationRespondActivity extends AppCompatActivity {
    MedicineDTO medicineDTO = new MedicineDTO();
    String medicine_name;
    Calendar medCalendar = Calendar.getInstance();

    int minute;
    int hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_notification_respond);
        medicine_name = getIntent().getStringExtra("medicineName");
        hour = getIntent().getIntExtra("hourOfDay", -1);
        minute = getIntent().getIntExtra("minute", -1);
        medCalendar.set(Calendar.HOUR_OF_DAY, hour);
        medCalendar.set(Calendar.MINUTE, minute);
        medCalendar.add(Calendar.HOUR_OF_DAY, +1);
        String stringminute = String.valueOf(minute);

       if (minute < 10) {
            stringminute = "0" + stringminute;
        }
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title("Medicine")
                .time(medCalendar)
                .content("You need to take" + " " +
                        medicine_name + "\n" +
                        "at" + " " +
                       hour + ":" +
                        stringminute)
                .color(18,87,189,255)
                .small_icon(R.drawable.medicine)
                .build();

    }
}
