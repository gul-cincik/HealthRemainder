package com.example.healthremainder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_list);

        final Context context =this;



        //Read from file yapanimek icin FileHelper tipinde obje olusturduk
        FileHelper fileHelper = new FileHelper(this);
        //String oarak read yaptık
        String appData = fileHelper.ReadFromFile();

        //Jsn tipinde tutulan datayı AppDataDTO obje tipine cevirdik sebebi->
        Gson gson = new Gson();
        AppDataDTO appDataDTO = gson.fromJson(appData, AppDataDTO.class);

        //->AppDataDTOnun icinden listeye ekleyebimek icin
        ArrayList<AppointmentDTO> appointmentDTOS = appDataDTO.appointmentDTOS;

        RecyclerView recyclerView = findViewById(R.id.recycler_appointment);
        AppointmentRecyclerAdapter appointmentRecyclerAdapter = new AppointmentRecyclerAdapter(appointmentDTOS,fileHelper, getResources().getStringArray(R.array.Departments), getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(appointmentRecyclerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_appointment_list);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_appointment);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppointmentActivity.class);
                startActivity(intent);
            }
        });

    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
