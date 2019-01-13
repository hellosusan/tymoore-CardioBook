package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;


import java.util.regex.Pattern;

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

    private Boolean isDateValid(){
        Boolean valid = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(formattedDate).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_date);
        }

        return valid;
    }

    private Boolean isTimeValid(){
        Boolean valid = Pattern.compile("[0-9]{2}:[0-9]{2}").matcher(formattedTime).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_time);
        }

        return valid;
    }

    private Boolean isSystolicValid(){
        Boolean valid = Pattern.compile("[0-9]+").matcher(systolicString).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_systolic);
        }

        return valid;
    }

    private Boolean isDiastolicValid(){
        Boolean valid = Pattern.compile("[0-9]+").matcher(diastolicString).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_diastolic);
        }

        return valid;
    }

    private Boolean isHeartRateValid(){
        Boolean valid = Pattern.compile("[0-9]+").matcher(heartRateString).matches();

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_heart_rate);
        }

        return valid;
    }

    private Boolean isCommentValid(){
        Boolean valid = commentString.length() <= 20;

        if (!valid && displayMessage == null){
            displayMessage = context.getString(R.string.invalid_comment);
        }

        return valid;
    }

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

    public String getDisplayMessage() {
        return displayMessage;
    }

}
