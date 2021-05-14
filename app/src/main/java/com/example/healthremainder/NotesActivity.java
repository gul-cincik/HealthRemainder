package com.example.healthremainder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;

public class NotesActivity extends AppCompatActivity {



    FileHelper fileHelper;

    TextView text_note;
    Button button_save_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        fileHelper = new FileHelper(this);
        text_note = findViewById(R.id.notes_text);
        button_save_note = findViewById(R.id.button_text_save);
        button_save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveNotes();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotesListActivity();
            }
        });
    }
    private void openNotesListActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void SaveNotes() {

        String appDataJson = fileHelper.ReadFromFile();

        Gson gson = new Gson();
        AppDataDTO appDataDTO = gson.fromJson(appDataJson, AppDataDTO.class);

        NotesDTO notesDTO = new NotesDTO();
        notesDTO.setNotes(text_note.getText().toString());

        appDataDTO.notesDTOS.add(notesDTO);
        String appDataToWrite = gson.toJson(appDataDTO);
        fileHelper.WriteToFile(appDataToWrite);

        Intent i = new Intent(this,NotesListActivity.class);
        startActivity(i);

    }
}
