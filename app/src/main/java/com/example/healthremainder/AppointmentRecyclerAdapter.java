package com.example.healthremainder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allyants.notifyme.NotifyMe;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentRecyclerAdapter extends RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentViewHolder>{

    ArrayList<AppointmentDTO> appointmentDTOArrayList;
    FileHelper fileHelper;
    String[] dep;
    Context appContext;



    public AppointmentRecyclerAdapter(ArrayList<AppointmentDTO> appointmentDTOArrayList, FileHelper fileHelper, String[] dep, Context appContext){
        this.appointmentDTOArrayList = appointmentDTOArrayList;
        this.fileHelper = fileHelper;
        this.dep = dep;
        this.appContext = appContext;
    }

    @NonNull
    @Override
    public AppointmentRecyclerAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_item_view, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, final int position) {
       final AppointmentDTO appointmentDTO = appointmentDTOArrayList.get(position);
       String minute;
       if(appointmentDTO.appCalendar.get(Calendar.MINUTE) < 10) minute = "0" + String.valueOf(appointmentDTO.appCalendar.get(Calendar.MINUTE));
       else minute = String.valueOf(appointmentDTO.appCalendar.get(Calendar.MINUTE));
       holder.department.setText(dep[appointmentDTO.department]);
       holder.appointment_date.setText((appointmentDTO.appCalendar.get(Calendar.DAY_OF_MONTH ) + 1) + "/" +
               ((appointmentDTO.appCalendar.get(Calendar.MONTH)) + 1) + "/" +
               +
               appointmentDTO.appCalendar.get(Calendar.YEAR));
       holder.appointment_time.setText(appointmentDTO.appCalendar.get(Calendar.HOUR_OF_DAY) + ":" +
               minute);
       holder.button_delete_appointment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int i = 0;
               int pos = 0;
               for (AppointmentDTO app : appointmentDTOArrayList) {
                   if (app.appCalendar == appointmentDTO.appCalendar) pos = i;
                   i++;
               }
               NotifyMe.cancel(appContext, String.valueOf(appointmentDTOArrayList.get(pos).appCalendar));
               appointmentDTOArrayList.remove(pos);
               String appDataJson = fileHelper.ReadFromFile();
               Gson gson = new Gson();
               AppDataDTO appDataDTO = gson.fromJson(appDataJson, AppDataDTO.class);
               appDataDTO.appointmentDTOS = appointmentDTOArrayList;
               String appDataToWrite = gson.toJson(appDataDTO);
               fileHelper.WriteToFile(appDataToWrite);
               //notifyItemRemoved(position);
               notifyDataSetChanged();

           }
       });
    }

    @Override
    public int getItemCount() {
        return appointmentDTOArrayList.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder{

        public TextView department, appointment_date, appointment_time;
        public Button button_delete_appointment;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            department = itemView.findViewById(R.id.appointment_dep);
            appointment_date = itemView.findViewById(R.id.appointment_date);
            appointment_time = itemView.findViewById(R.id.appointment_time);
            button_delete_appointment = itemView.findViewById(R.id.button_delete_appoi);
        }
    }
}
