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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicineRecyclerAdapter extends RecyclerView.Adapter<MedicineRecyclerAdapter.MedicineViewHolder> {


    ArrayList<MedicineDTO> medicineDTOList;
    FileHelper fileHelper;
    Context appContext;

    public MedicineRecyclerAdapter(ArrayList<MedicineDTO> medicineDTOList,FileHelper fileHelper, Context appContext) {
        this.medicineDTOList = medicineDTOList;
        this.fileHelper = fileHelper;
        this.appContext = appContext;
    }

    @NonNull
    @Override
    public MedicineRecyclerAdapter.MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_list_item_view, parent, false);
        return  new MedicineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, final int position) {
        final MedicineDTO medicineDTO = medicineDTOList.get(position);
        String minute;
        if(medicineDTO.medCalender.get(Calendar.MINUTE) < 10){
            minute = "0" + medicineDTO.medCalender.get(Calendar.MINUTE);
        }
        else
            minute = String.valueOf(medicineDTO.medCalender.get(Calendar.MINUTE));
        holder.medicine_name.setText(medicineDTO.medicine_name);
        holder.medicine_time.setText(medicineDTO.medCalender.get(Calendar.HOUR_OF_DAY) + ":" +
                minute);
        holder.button_delete_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //listeden random ilac silmesini engellemek icin
                int i = 0;
                int pos = 0;
                for (MedicineDTO med : medicineDTOList) {
                    if (med.medicine_name == medicineDTO.medicine_name){
                        pos = i;
                    }
                    i++;
                }
                //notificationı iptal etmek için
                //key med name idi
                NotifyMe.cancel(appContext, medicineDTOList.get(pos).medicine_name);
                medicineDTOList.remove(pos);
                String appDataJson = fileHelper.ReadFromFile();
                Gson gson = new Gson();
                AppDataDTO appDataDTO = gson.fromJson(appDataJson, AppDataDTO.class);
                appDataDTO.medicineDTOS = medicineDTOList;
                String appDataToWrite = gson.toJson(appDataDTO);
                fileHelper.WriteToFile(appDataToWrite);
                //notifyItemRemoved(pos);
                notifyDataSetChanged();//Data listten bir sey silindiği icin recycler a kendisini kontrol etmesini söyler
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicineDTOList.size();
    }

    public class MedicineViewHolder extends RecyclerView.ViewHolder {

        public TextView medicine_name, medicine_time;
        public Button button_delete_medicine;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicine_name = itemView.findViewById(R.id.medicine_name);
            medicine_time = itemView.findViewById(R.id.medicine_time);
            button_delete_medicine = itemView.findViewById(R.id.button_delete_medicine);

        }
    }
}
