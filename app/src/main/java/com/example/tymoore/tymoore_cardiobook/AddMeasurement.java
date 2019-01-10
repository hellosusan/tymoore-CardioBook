package com.example.tymoore.tymoore_cardiobook;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddMeasurement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
    }

    public void dateButtonOnClick(View view){
        Button dateButton = (Button) view;

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String formattedDate = getFormattedDate(i, i1, i2);
                Toast.makeText(AddMeasurement.this, formattedDate, Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void timeButtonOnClick(View view){
        Button timeButton = (Button) view;

    }

    private static String getFormattedDate(int year, int month, int day){
        DecimalFormat yearFormatter = new DecimalFormat("0000");
        String formattedYear = yearFormatter.format(year);

        DecimalFormat monthDayFormatter = new DecimalFormat("00");
        String formattedMonth = monthDayFormatter.format(month+1);
        String formattedDay = monthDayFormatter.format(day);

        return formattedYear + "-" + formattedMonth + "-" + formattedDay;
    }

}
