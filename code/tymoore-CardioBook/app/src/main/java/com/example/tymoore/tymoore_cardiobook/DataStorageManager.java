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

// This class is used to store all of the data needed for the app.
// All data is stored in a text file as a json string using the Gson class.
public class DataStorageManager {
    private Context context;
    private File file;
    private Gson gson;

    // class constructor, gets the current context and filename
    public DataStorageManager(Context context, String fileName) {
        this.context = context;
        this.file = new File(context.getFilesDir(), fileName);
        this.gson = new Gson();

    }

    // insert a measurement to the database, this is done by first loading in the data,
    // then adding a measurement, then writing back the data.
    public void insertMeasurement(Measurement measurement){
        ArrayList<Measurement> measurements = getMeasurements();
        measurements.add(measurement);
        writeMeasurementsToFile(measurements);
    }

    // update a measurement to the database, this is done by first loading in the data, then
    // updating an element, and then writing back the data.
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

    // remove a measurement from the database, this is done by first loading in the data, then
    // removing an element, and then writing back the data.
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

    // Write a list of measurements to the database, first convert the measurements to a json
    // string and then write that json string to the database.
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

    // get the measurements from the filesystem. Read the file with the provided file name
    // convert that data back to an object using the Gson library. If the file either does not
    // exist or is empty return an empty array list.
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

        ArrayList<Measurement> measurements = gson.fromJson(data, arrayListMeasurementType);
        return (measurements == null)? new ArrayList<Measurement>(): measurements;
    }



}
