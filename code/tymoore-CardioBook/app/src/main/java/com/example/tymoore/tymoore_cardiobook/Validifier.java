package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;


import java.util.regex.Pattern;

// The Validifier class, determines if the inputted data is of correct form
// able to communicate if a input set is valid, and if so send the corresponding message.
// If input is not valid then it indicates the highest order field which is incorrect.
public class Validifier{
    private Context context;
    private String formattedDate;
    private String formattedTime;
    private String systolicString;
    private String diastolicString;
    private String heartRateString;
    private String commentString;
    private String displayMessage;
    private int mode;

    // The constructor, sets all of the data values.
    public Validifier(Context context, String formattedDate, String formattedTime, String systolicString, String diastolicString, String heartRateString, String commentString, int mode) {
        this.context = context;
        this.formattedDate = formattedDate;
        this.formattedTime = formattedTime;
        this.systolicString = systolicString;
        this.diastolicString = diastolicString;
        this.heartRateString = heartRateString;
        this.commentString = commentString;
        this.mode = mode;
        this.displayMessage = null;
    }

    // determines if the date is valid, a valid date has the form yyyy-mm-dd
    private Boolean isDateValid(){
        Boolean valid = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(formattedDate).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_date);
        }

        return valid;
    }

    // determines if the time is valid, a valid time has the form hh:mm
    private Boolean isTimeValid(){
        Boolean valid = Pattern.compile("[0-9]{2}:[0-9]{2}").matcher(formattedTime).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_time);
        }

        return valid;
    }

    // determines if the systolic value is valid, a valid systolic value is a non-negative integer
    private Boolean isSystolicValid(){
        Boolean valid = Pattern.compile("[0-9]+").matcher(systolicString).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_systolic);
        }

        return valid;
    }

    // determines if the diastolic value is valid, a valid diastolic value is a non-negative integer
    private Boolean isDiastolicValid(){
        Boolean valid = Pattern.compile("[0-9]+").matcher(diastolicString).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_diastolic);
        }

        return valid;
    }

    // determines if the heart rate value is valid, a valid heart rate value is a non-negative integer
    private Boolean isHeartRateValid(){
        Boolean valid = Pattern.compile("[0-9]+").matcher(heartRateString).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_heart_rate);
        }

        return valid;
    }

    // determines if the comment is valid, a valid comment is less than or equal to 20 characters
    private Boolean isCommentValid(){
        Boolean valid = commentString.length() <= 20;

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_comment);
        }

        return valid;
    }

    // determines if the input is valid, also generates the proper display message
    public Boolean isValid(){
        Boolean valid = isDateValid() && isTimeValid() && isSystolicValid() && isDiastolicValid()
                && isHeartRateValid() && isCommentValid();

        if (valid){
            switch (mode){
                case AddOrEditMeasurement.ADD:
                    displayMessage = context.getString(R.string.submission_text);
                    break;

                case AddOrEditMeasurement.EDIT:
                    displayMessage = context.getString(R.string.save_submission_text);
                    break;
            }
        }

        return valid;
    }

    // returns the proper display message.
    public String getDisplayMessage() {
        return displayMessage;
    }

}
