package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Measurement> measurements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMeasurements();
        toggleEmptyMessage();
        initRecyclerView();
    }

    private void toggleEmptyMessage() {
        TextView emptyMessage = findViewById(R.id.empty_measurements_string);

        if (measurements.size() == 0){
            emptyMessage.setVisibility(View.VISIBLE);
        }
        else{
            emptyMessage.setVisibility(View.INVISIBLE);
        }
    }

    public void addMeasurementOnClick(View view){
        goToAddEditMeasurementActivity(this, AddOrEditMeasurement.ADD, null);
    }

    private void getMeasurements(){
        DataStorageManager dataStorageManager = new DataStorageManager(this,
                getString(R.string.measurements_file_name));

        measurements = dataStorageManager.getMeasurements();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.measurement_recycler_view);

        MeasurementRecyclerAdapter adapter = new MeasurementRecyclerAdapter(this, measurements);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

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
