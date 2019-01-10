package com.example.tymoore.tymoore_cardiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Measurement> measurements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleEmptyMessage();
        getMeasurements();
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
        goToAddMeasurementActivity();
    }

    private void goToAddMeasurementActivity(){
        Intent intent = new Intent(this, AddMeasurement.class);
        startActivity(intent);
    }

    private void getMeasurements(){
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        measurements = databaseHandler.MeasurementsGetRows();
    }

}
