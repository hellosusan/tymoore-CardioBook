package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataStorageManager {
    private Context context;
    private File file;
    private Gson gson;

    public DataStorageManager(Context context, String fileName) {
        this.context = context;
        this.file = new File(context.getFilesDir(), fileName);
        this.gson = new Gson();

    }


    public void insertMeasurement(Measurement measurement){
        ArrayList<Measurement> measurements = getMeasurements();
        measurements.add(measurement);
        writeMeasurementsToFile(measurements);
    }

    public void updateMeasurement(Measurement measurement){
        ArrayList<Measurement> measurements = getMeasurements();
        for (int i=0; i<measurements.size(); i++){
            Measurement m = measurements.get(i);
            if (m.getID().equals(measurement.getID())){
                measurements.set(i, measurement);
            }
        }
        writeMeasurementsToFile(measurements);
    }

    public void clearMeasurements(){
        writeMeasurementsToFile(new ArrayList<Measurement>());
    }

    public void removeMeasurement(Measurement measurement){
        ArrayList<Measurement> measurements = getMeasurements();

        int idToRemove = -1;
        for (int i=0; i<measurements.size(); i++){
            Measurement m = measurements.get(i);
            if (m.getID().equals(measurement.getID())){
                idToRemove = i;
                break;
            }
        }

        if (idToRemove < 0){
            Log.d(context.getString(R.string.testGeneralLog), "Error removing");
        }

        measurements.remove(idToRemove);
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
