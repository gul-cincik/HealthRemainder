package com.example.healthremainder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends  RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>{

    ArrayList<NotesDTO> notesDTOArrayList;
    FileHelper fileHelper;

    public NotesRecyclerAdapter(ArrayList<NotesDTO> notesDTOArrayList, FileHelper fileHelper){
        this.notesDTOArrayList = notesDTOArrayList;
        this.fileHelper = fileHelper;
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item_view, parent, false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        NotesDTO notesDTO = notesDTOArrayList.get(position);
        holder.note.setText(notesDTO.notes);
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesDTOArrayList.remove(position);
                String  appDataJson = fileHelper.ReadFromFile();
                Gson gson = new Gson();
                AppDataDTO appDataDTO = gson.fromJson(appDataJson, AppDataDTO.class);
                appDataDTO.notesDTOS = notesDTOArrayList;
                String appDataToWrite = gson.toJson(appDataDTO);
                fileHelper.WriteToFile(appDataToWrite);
                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notesDTOArrayList.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{

        public TextView note;
        public Button button_delete;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.notes_text);
            button_delete = itemView.findViewById(R.id.button_delete_notes);
        }
    }
}
