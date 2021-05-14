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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_list);
        final Context context = this;
        FileHelper fileHelper = new FileHelper(this);
        final String appDate = fileHelper.ReadFromFile();
        Gson gson = new Gson();
        AppDataDTO appDataDTO = gson.fromJson(appDate, AppDataDTO.class);
        ArrayList<MedicineDTO> medicineDTOS = appDataDTO.medicineDTOS;

        if(medicineDTOS == null) medicineDTOS = new ArrayList<MedicineDTO>();

        RecyclerView recyclerView = findViewById(R.id.recycler_medicine);
        MedicineRecyclerAdapter medicineRecyclerAdapter = new MedicineRecyclerAdapter(medicineDTOS,fileHelper,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(medicineRecyclerAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_medicine);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Medicine.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_medicine_list);
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
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
