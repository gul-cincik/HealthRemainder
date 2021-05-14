package com.example.healthremainder;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {

    Context context;
    public void WriteToFile(String data){
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput("test.gul",context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileHelper(Context context) {
        this.context = context;
    }


    public String ReadFromFile()  {
        String result = "";
        try {
            InputStream inputStream = context.openFileInput("test.gul");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receivedString =bufferedReader.readLine()) != null){
                    stringBuilder.append(receivedString);
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    };

}
