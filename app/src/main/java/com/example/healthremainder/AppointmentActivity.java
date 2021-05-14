package com.example.healthremainder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.allyants.notifyme.NotifyMe;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {



    private int departmentId;
    private String note;
    FileHelper fileHelper;

    Button save_button;
    TextView date_textview;
    TextView time_textview;
    EditText editText_note;
    Spinner spinner_departments;
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        fileHelper = new FileHelper(this);

        save_button = findViewById(R.id.appointment_save_button);
        date_textview = findViewById(R.id.date_texview);
        time_textview = findViewById(R.id.time_textview);
        editText_note = findViewById(R.id.editText);
        spinner_departments = findViewById(R.id.spinner_departmentId);

        dpd = DatePickerDialog.newInstance(AppointmentActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        tpd = TimePickerDialog.newInstance(AppointmentActivity.this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                true);



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAppointment();
            }
        });



        spinner_departments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_appointment);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppointmentListActivity();
            }
        });
    }



    private void SaveAppointment() {
        String appDataJson = fileHelper.ReadFromFile();
        Gson gson = new Gson();
        AppDataDTO appDataDTO = gson.fromJson(appDataJson, AppDataDTO.class);
        //AppointmentDTO yu bilgiyle doldurduk(Sadece kod içinde tutuluyor)
        AppointmentDTO appDTO = new AppointmentDTO();
        appDTO.setAppCalendar(calendar);
        appDTO.setDepartment(departmentId);
        appDTO.setNotes(editText_note.getText().toString());
        appDataDTO.appointmentDTOS.add(appDTO);
        String appDataToWrite = gson.toJson(appDataDTO);
        fileHelper.WriteToFile(appDataToWrite);

        Switch alarm_noti = findViewById(R.id.alarm_appointment);
        if (alarm_noti.isChecked()){
            int day = appDTO.appCalendar.get(Calendar.DAY_OF_MONTH) + 1;
            int month = appDTO.appCalendar.get(Calendar.MONTH) + 1;
            String[] departmens = getResources().getStringArray(R.array.Departments);
            NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                    .title("Appointment")
                    .content("In" + " "+
                            departmens[departmentId] + "\n" + "at" + " " +
                            appDTO.appCalendar.get(Calendar.HOUR_OF_DAY) + ":" +
                            appDTO.appCalendar.get(Calendar.MINUTE) + "\n" + "on"+ " " +
                            day + "/" +
                            month + "/" +
                            appDTO.appCalendar.get(Calendar.YEAR))
                    .time(calendar)
                    .color(188, 2, 228, 255)
                    .addAction(new Intent(), "OK", true)
                    .key(String.valueOf(appDTO.appCalendar))
                    .led_color(253, 156, 173, 255)
                    .small_icon(R.drawable.appointment)
                    .build();

        }

        Intent i = new Intent(this,AppointmentListActivity.class);
        startActivity(i);

    }



    public void showDatePicker(View view) {
        dpd.show(getFragmentManager(), "DatePicker" );

    }

    public void showTimePicker(View view) {
        tpd.show(getFragmentManager(), "TimePickerDialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String month_string = Integer.toString(monthOfYear+1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);
        date_textview.setText(dateMessage);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        String hour_string = Integer.toString(hourOfDay);
        String minute_string = Integer.toString(minute);
        String timeMessage = (hour_string + ":" + minute_string);
        time_textview.setText(timeMessage);
    }

    private void openAppointmentListActivity() {
        Intent intent = new Intent(this, AppointmentListActivity.class);
        startActivity(intent);
    }
}
