package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataStorageManager {
    private Context context;
    private String fileName;
    private File file;
    private Gson gson;

    public DataStorageManager(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;

        this.file = new File(context.getFilesDir(), fileName);
        this.gson = new Gson();

    }


    public void insertMeasurement(Measurement measurement){
        ArrayList<Measurement> measurements = getMeasurements();
        measurements.add(measurement);
        writeMeasurementsToFile(measurements);
    }

    public void clearMeasurements(){
        writeMeasurementsToFile(new ArrayList<Measurement>());
    }

    public void removeMeasurement(Measurement measurement){
        ArrayList<Measurement> measurements = getMeasurements();
        for(Measurement m: measurements){
            if (m.getID().equals(measurement.getID())){
                measurements.remove(m);
            }
        }
        writeMeasurementsToFile(measurements);
    }

    private void writeMeasurementsToFile(ArrayList<Measurement> measurements){
        String data = gson.toJson(measurements);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }
        catch (IOException e) {
            Log.e(context.getString(R.string.testGeneralLog), e.toString());
        }
    }

    public ArrayList<Measurement> getMeasurements(){
        int fileLength = (int) file.length();

        byte[] byteArray = new byte[fileLength];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteArray);
            fileInputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e(context.getString(R.string.testGeneralLog), e.toString());
        }
        catch (IOException e) {
            Log.e(context.getString(R.string.testGeneralLog), e.toString());
        }

        String data = new String(byteArray);
        Type arrayListMeasurementType = new TypeToken<ArrayList<Measurement>>(){}.getType();

        try{
            ArrayList<Measurement> measurements = gson.fromJson(data, arrayListMeasurementType);
            return measurements;
        }
        catch (Exception e){
            return new ArrayList<Measurement>();
        }
    }



}
