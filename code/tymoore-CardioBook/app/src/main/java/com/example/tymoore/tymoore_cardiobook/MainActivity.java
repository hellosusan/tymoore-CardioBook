package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

// The main activity. This activity is the first activity the user sees when opening the app.
// It contains a list of all the measurements and displays them.
// It allows users to select a specific measurement to edit/delete it, or create a new measurement.
public class MainActivity extends AppCompatActivity {

    // the list of measurements
    private ArrayList<Measurement> measurements = new ArrayList<>();

    // OnCreate, set the content view, get the measurements from storage, toggle the empty message
    // and then initialize the recycler view.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMeasurements();
        toggleEmptyMessage();
        initRecyclerView();
    }

    // If there are no measurements stored then display an empty message.
    private void toggleEmptyMessage() {
        TextView emptyMessage = findViewById(R.id.empty_measurements_string);

        if (measurements == null || measurements.size() == 0){
            emptyMessage.setVisibility(View.VISIBLE);
        }
        else{
            emptyMessage.setVisibility(View.INVISIBLE);
        }
    }

    // when the add button is selected this method is called, it opens the measurement screen
    // in add mode.
    public void addMeasurementOnClick(View view){
        goToAddEditMeasurementActivity(this, AddOrEditMeasurement.ADD, null);
    }

    // get the stored measurements from the phone using the DataStoreManager class
    private void getMeasurements(){
        DataStorageManager dataStorageManager = new DataStorageManager(this,
                getString(R.string.measurements_file_name));

        measurements = dataStorageManager.getMeasurements();
    }

    // Initialize the recycler view. The recycler view will hold all of the stored measurements.
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.measurement_recycler_view);

        MeasurementRecyclerAdapter adapter = new MeasurementRecyclerAdapter(this, measurements);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Transition to the add/edit measurement activity, we also pass it a mode, either ADD or EDIT
    public static void goToAddEditMeasurementActivity(Context context, int mode, Measurement measurement){
        Intent intent = new Intent(context, AddOrEditMeasurement.class);
        intent.putExtra(AddOrEditMeasurement.MODELABEL, mode);
        if (mode == AddOrEditMeasurement.EDIT){
            Gson gson = new Gson();
            intent.putExtra(AddOrEditMeasurement.MEASUREMENTLABEL, gson.toJson(measurement));
        }
        context.startActivity(intent);
    }

}
