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
import java.util.Calendar;

public class AddOrEditMeasurement extends AppCompatActivity {
    public static final String MODELABEL = "com.example.tymoore.tymoore_cardiobook.MODE";
    public static final String MEASUREMENTLABEL = "com.example.tymoore.tymoore_cardiobook.MEASUREMENT";
    public static final int ADD = 0;
    public static final int EDIT = 1;

    private int mode;
    private Measurement editMeasurement;
    private DataStorageManager dataStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_measurement);

        checkMode();

        dataStorageManager = new DataStorageManager(this,
                getString(R.string.measurements_file_name));
    }

    private void checkMode(){
        Intent intent = getIntent();
        mode = intent.getIntExtra(AddOrEditMeasurement.MODELABEL, -1);

        switch (mode){
            case AddOrEditMeasurement.ADD:
                startAddMode();
                break;

            case AddOrEditMeasurement.EDIT:
                String data = intent.getStringExtra(AddOrEditMeasurement.MEASUREMENTLABEL);
                startEditMode(data);
                break;
        }
    }

    private void startEditMode(String data){
        Button submit = findViewById(R.id.submit_button);
        submit.setText(getText(R.string.edit_submit_text));

        Button delete = findViewById(R.id.delete_button);
        delete.setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        editMeasurement = gson.fromJson(data, Measurement.class);
        autoFillMeasurement();

    }

    private void autoFillMeasurement(){
        Button date = findViewById(R.id.select_date_button);
        Button time = findViewById(R.id.select_time_button);
        EditText systolic = findViewById(R.id.systolic_pressure_input);
        EditText diastolic = findViewById(R.id.diastolic_pressure_input);
        EditText heartRate = findViewById(R.id.heart_rate_input);
        EditText comment = findViewById(R.id.comments_input);

        date.setText(editMeasurement.getDateMeasured());
        time.setText(editMeasurement.getTimeMeasured());
        systolic.setText(Integer.toString(editMeasurement.getSystolicPressure()));
        diastolic.setText(Integer.toString(editMeasurement.getDiastolicPressure()));
        heartRate.setText(Integer.toString(editMeasurement.getHeartRate()));
        comment.setText(editMeasurement.getComment());
    }

    private void startAddMode(){
        Button submit = findViewById(R.id.submit_button);
        submit.setText(getText(R.string.submit_text));

        Button delete = findViewById(R.id.delete_button);
        delete.setVisibility(View.GONE);
    }

    public void dateButtonOnClick(View view){
        final Button dateButton = (Button) view;

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            private String getFormattedDate(int year, int month, int day){
                DecimalFormat yearFormatter = new DecimalFormat("0000");
                String formattedYear = yearFormatter.format(year);

                DecimalFormat monthDayFormatter = new DecimalFormat("00");
                String formattedMonth = monthDayFormatter.format(month+1);
                String formattedDay = monthDayFormatter.format(day);

                return formattedYear + "-" + formattedMonth + "-" + formattedDay;
            }

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

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            private String getFormattedTime(int hour, int minute){
                DecimalFormat timeFormatter = new DecimalFormat("00");
                String formattedHour = timeFormatter.format(hour);
                String formattedMinute = timeFormatter.format(minute);

                return formattedHour + ":" + formattedMinute;
            }

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

    private void updateMeasurementValues(String date, String time, String systolic,
                                         String diastolic, String heartRate, String comment){
        editMeasurement.setDateMeasured(date);
        editMeasurement.setTimeMeasured(time);
        editMeasurement.setSystolicPressure(Integer.valueOf(systolic));
        editMeasurement.setDiastolicPressure(Integer.valueOf(diastolic));
        editMeasurement.setHeartRate(Integer.valueOf(heartRate));
        editMeasurement.setComment(comment);
    }

    public void submitButtonOnClick(View view){
        String formattedDate = ((Button) findViewById(R.id.select_date_button)).getText().toString();
        String formattedTime = ((Button) findViewById(R.id.select_time_button)).getText().toString();
        String systolicString = ((EditText) findViewById(R.id.systolic_pressure_input)).getText().toString();
        String diastolicString = ((EditText) findViewById(R.id.diastolic_pressure_input)).getText().toString();
        String heartRateString = ((EditText) findViewById(R.id.heart_rate_input)).getText().toString();
        String commentString = ((EditText) findViewById(R.id.comments_input)).getText().toString();

        Validifier validifier = new Validifier(this, formattedDate, formattedTime, systolicString,
                diastolicString, heartRateString, commentString, mode);

        Boolean validInputs = validifier.isValid();

        if (validInputs){
            switch (mode){
                case AddOrEditMeasurement.ADD:
                    Measurement measurement = new Measurement(formattedDate, formattedTime,
                            Integer.valueOf(systolicString), Integer.valueOf(diastolicString),
                            Integer.valueOf(heartRateString), commentString);
                    dataStorageManager.insertMeasurement(measurement);
                    Toast.makeText(this, validifier.getDisplayMessage(),Toast.LENGTH_SHORT).show();
                    break;

                case AddOrEditMeasurement.EDIT:
                    updateMeasurementValues(formattedDate, formattedTime, systolicString,
                            diastolicString, heartRateString, commentString);

                    dataStorageManager.updateMeasurement(editMeasurement);
                    Toast.makeText(this, validifier.getDisplayMessage(),Toast.LENGTH_SHORT).show();
                    break;
            }

            goBackToMainActivity();
        }

    }

    public void deleteButtonOnClick(View view){
        dataStorageManager.removeMeasurement(editMeasurement);
        Toast.makeText(this, getString(R.string.delete_submission_text),Toast.LENGTH_SHORT).show();
        goBackToMainActivity();
    }


}
