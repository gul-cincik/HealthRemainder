package com.example.healthremainder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list);
        final Context context = this;
        FileHelper fileHelper = new FileHelper(this);
        String appData = fileHelper.ReadFromFile();
        Gson gson = new Gson();
        AppDataDTO appDataDTO = gson.fromJson(appData, AppDataDTO.class);
        ArrayList<NotesDTO> notesDTOS = appDataDTO.notesDTOS;
        RecyclerView recyclerView = findViewById(R.id.recycler_notes);
        NotesRecyclerAdapter notesRecyclerAdapter = new NotesRecyclerAdapter(notesDTOS,fileHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesRecyclerAdapter);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_notes);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNotesActivity();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notes_list);
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
    private void openNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
