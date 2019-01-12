package com.example.tymoore.tymoore_cardiobook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMeasurement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
    }

    private static String getFormattedDate(int year, int month, int day){
        DecimalFormat yearFormatter = new DecimalFormat("0000");
        String formattedYear = yearFormatter.format(year);

        DecimalFormat monthDayFormatter = new DecimalFormat("00");
        String formattedMonth = monthDayFormatter.format(month+1);
        String formattedDay = monthDayFormatter.format(day);

        return formattedYear + "-" + formattedMonth + "-" + formattedDay;
    }

    private static String getFormattedTime(int hour, int minute){
        DecimalFormat timeFormatter = new DecimalFormat("00");
        String formattedHour = timeFormatter.format(hour);
        String formattedMinute = timeFormatter.format(minute);

        return formattedHour + ":" + formattedMinute;
    }

    public void dateButtonOnClick(View view){
        final Button dateButton = (Button) view;

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String formattedDate = getFormattedDate(i, i1, i2);
                dateButton.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void timeButtonOnClick(View view){
        final Button timeButton = (Button) view;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeasurement.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String formattedTime = getFormattedTime(i, i1);
                timeButton.setText(formattedTime);
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }


    private void goBackToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void storeMeasurement(String date, String time, String systolic, String diastolic,
                                  String heartRate, String comment){
        Measurement measurement = new Measurement(date, time, Integer.valueOf(systolic),
                Integer.valueOf(diastolic), Integer.valueOf(heartRate), comment);

        DataStorageManager dataStorageManager = new DataStorageManager(this,
                getString(R.string.measurements_file_name));

        dataStorageManager.insertMeasurement(measurement);
    }

    public void submitButtonOnClick(View view){
        String formattedDate = ((Button) findViewById(R.id.select_date_button)).getText().toString();
        String formattedTime = ((Button) findViewById(R.id.select_time_button)).getText().toString();
        String systolicString = ((EditText) findViewById(R.id.systolic_pressure_input)).getText().toString();
        String diastolicString = ((EditText) findViewById(R.id.diastolic_pressure_input)).getText().toString();
        String heartRateString = ((EditText) findViewById(R.id.heart_rate_input)).getText().toString();
        String commentString = ((EditText) findViewById(R.id.comments_input)).getText().toString();

        Validifier validifier = new Validifier(this, formattedDate, formattedTime, systolicString,
                diastolicString, heartRateString, commentString);

        Boolean validInputs = validifier.isValid();
        Toast.makeText(this, validifier.getDisplayMessage(),Toast.LENGTH_SHORT).show();

        if (validInputs){
            storeMeasurement(formattedDate, formattedTime, systolicString, diastolicString,
                    heartRateString, commentString);
            goBackToMainActivity();
        }

    }



}
