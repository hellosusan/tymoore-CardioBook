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

// The add or edit measurement activity. In this activity a user can add, edit, or delete
// a measurement.
public class AddOrEditMeasurement extends AppCompatActivity {

    // Constant strings/values used for selecting the mode and intent keys
    public static final String MODELABEL = "com.example.tymoore.tymoore_cardiobook.MODE";
    public static final String MEASUREMENTLABEL = "com.example.tymoore.tymoore_cardiobook.MEASUREMENT";
    public static final int ADD = 0;
    public static final int EDIT = 1;

    private int mode; // the mode we are in
    private Measurement editMeasurement; // if we are in edit mode, the measurement we are editing.
    private DataStorageManager dataStorageManager; // an instance of the DataStorageManager class

    // OnCreate we set the content view, then we determine the mode and initialize the storage manager.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_measurement);

        checkMode();

        dataStorageManager = new DataStorageManager(this,
                getString(R.string.measurements_file_name));
    }

    // check the mode we are in, then call the corresponding method
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

    // if we are in edit mode we change the text of the submit button and ensure
    // the delete button is visible. We then get the measurement we are editing and autofill
    // its information.
    private void startEditMode(String data){
        Button submit = findViewById(R.id.submit_button);
        submit.setText(getText(R.string.edit_submit_text));

        Button delete = findViewById(R.id.delete_button);
        delete.setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        editMeasurement = gson.fromJson(data, Measurement.class);
        autoFillMeasurement();

    }

    // Called in edit mode, this method loads the data of the measurement we are editing, and
    // displays its values on the screen by auto-filling the form fields.
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

    // when we start add mode we set the submit button to the correct text and hide the delete
    // button
    private void startAddMode(){
        Button submit = findViewById(R.id.submit_button);
        submit.setText(getText(R.string.submit_text));

        Button delete = findViewById(R.id.delete_button);
        delete.setVisibility(View.GONE);
    }

    // This method is called when the date button is clicked, it brings up a datepickerdialog,
    // which allows the user to select a date
    public void dateButtonOnClick(View view){
        final Button dateButton = (Button) view;

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        // the datepickerdialog which allows the user to select a date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            // converts a year, day, and month, into a fortmatted string
            // of the format yyyy-mm-dd
            private String getFormattedDate(int year, int month, int day){
                DecimalFormat yearFormatter = new DecimalFormat("0000");
                String formattedYear = yearFormatter.format(year);

                DecimalFormat monthDayFormatter = new DecimalFormat("00");
                String formattedMonth = monthDayFormatter.format(month+1);
                String formattedDay = monthDayFormatter.format(day);

                return formattedYear + "-" + formattedMonth + "-" + formattedDay;
            }

            // set the text of the datebutton to be the selected date.
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String formattedDate = getFormattedDate(i, i1, i2);
                dateButton.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    // This method is called when the time button is clicked, it brings up a timepickerdialog,
    // which allows the user to select a time
    public void timeButtonOnClick(View view){
        final Button timeButton = (Button) view;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // the timepickerdialog which allows the user to select a time
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            // converts a hour and minute into a formatted string
            // of the format hh:mm
            private String getFormattedTime(int hour, int minute){
                DecimalFormat timeFormatter = new DecimalFormat("00");
                String formattedHour = timeFormatter.format(hour);
                String formattedMinute = timeFormatter.format(minute);

                return formattedHour + ":" + formattedMinute;
            }

            // set the text of the timebutton to be the selected time.
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String formattedTime = getFormattedTime(i, i1);
                timeButton.setText(formattedTime);
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

    // return back to the main activity
    private void goBackToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // when an edit is completed we need to update the values of the measurement we are editing
    // this method performs those updates.
    private void updateMeasurementValues(String date, String time, String systolic,
                                         String diastolic, String heartRate, String comment){
        editMeasurement.setDateMeasured(date);
        editMeasurement.setTimeMeasured(time);
        editMeasurement.setSystolicPressure(Integer.valueOf(systolic));
        editMeasurement.setDiastolicPressure(Integer.valueOf(diastolic));
        editMeasurement.setHeartRate(Integer.valueOf(heartRate));
        editMeasurement.setComment(comment);
    }

    // when the submit/save button is clicked this method is called. It retrieves all data entered
    // and then calls the validifier class to determine if the output is valid.
    // If it is not valid we can get the highest priority invalid field (highest priority is the
    // field on the top of the page) and display the error message. If all fields are valid
    // we can display a success message, and then either add or update the measurement to the database
    // depending on the current mode we are in. Finally the user is returned to the main activity.
    public void submitButtonOnClick(View view){
        String formattedDate = ((Button) findViewById(R.id.select_date_button)).getText().toString();
        String formattedTime = ((Button) findViewById(R.id.select_time_button)).getText().toString();
        String systolicString = ((EditText) findViewById(R.id.systolic_pressure_input)).getText().toString();
        String diastolicString = ((EditText) findViewById(R.id.diastolic_pressure_input)).getText().toString();
        String heartRateString = ((EditText) findViewById(R.id.heart_rate_input)).getText().toString();
        String commentString = ((EditText) findViewById(R.id.comments_input)).getText().toString();

        // the validifier class, determines if our outputs are correct and then provides us a message
        // to display indicating either a success message or what the user should fix
        Validifier validifier = new Validifier(this, formattedDate, formattedTime, systolicString,
                diastolicString, heartRateString, commentString, mode);

        // determines if the output is valid
        Boolean validInputs = validifier.isValid();

        // display the corresponding message to the user (either success or error)
        Toast.makeText(this, validifier.getDisplayMessage(),Toast.LENGTH_SHORT).show();

        if (validInputs){
            switch (mode){
                // if we have a valid add we add the new measurement to the database
                case AddOrEditMeasurement.ADD:
                    Measurement measurement = new Measurement(formattedDate, formattedTime,
                            Integer.valueOf(systolicString), Integer.valueOf(diastolicString),
                            Integer.valueOf(heartRateString), commentString);
                    dataStorageManager.insertMeasurement(measurement);
                    break;

                // if we have a valid edit we save the changes in the database
                case AddOrEditMeasurement.EDIT:
                    updateMeasurementValues(formattedDate, formattedTime, systolicString,
                            diastolicString, heartRateString, commentString);

                    dataStorageManager.updateMeasurement(editMeasurement);
                    break;
            }

            // finally we return to the main activity.
            goBackToMainActivity();
        }

    }

    // this method is called when the delete button is selected in edit mode.
    // the current measurement is removed from the database and the user is returned to the main
    // activity.
    public void deleteButtonOnClick(View view){
        dataStorageManager.removeMeasurement(editMeasurement);
        Toast.makeText(this, getString(R.string.delete_submission_text),Toast.LENGTH_SHORT).show();
        goBackToMainActivity();
    }


}
