package com.example.tymoore.tymoore_cardiobook;

import java.util.UUID;

// The measurement class, defines the structure and functionality of a measurement,
// it only contains, a constructor, getters and setters, and a toString method.
public class Measurement {
    private UUID ID; // used to uniquely identify the measurement, used for editing and deleting
    private String dateMeasured;
    private String timeMeasured;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Integer heartRate;
    private String comment;


    // The constructor, set all the data fields
    public Measurement(String dateMeasured, String timeMeasured, Integer systolicPressure, Integer diastolicPressure, Integer heartRate, String comment) {
        this.dateMeasured = dateMeasured;
        this.timeMeasured = timeMeasured;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;

        this.ID = UUID.randomUUID();
    }

    // Returns the string version of the measurement, helpful for debugging.
    @Override
    public String toString() {
        return "ID: " + ID + ", DateMeasured: " + dateMeasured +
                ", TimeMeasured: " + timeMeasured + ", SystolicPressure: " + systolicPressure +
                ", DiastolicPressure: " + diastolicPressure + ", HeartRate: " + heartRate +
                ", Comment: " + comment;
    }

    // get the Measurement's unique identifier.
    public UUID getID() {
        return ID;
    }

    // get the Measurement's date
    public String getDateMeasured() {
        return dateMeasured;
    }

    // get the Measurement's time
    public String getTimeMeasured() {
        return timeMeasured;
    }

    // get the Measurement's systolic pressure
    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    // get the Measurement's diastolic pressure
    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    // get the Measurement's heart rate
    public Integer getHeartRate() {
        return heartRate;
    }

    // get the Measurement's comment
    public String getComment() {
        return comment;
    }

    // set the Measurement's date
    public void setDateMeasured(String dateMeasured) {
        this.dateMeasured = dateMeasured;
    }

    // set the Measurement's time
    public void setTimeMeasured(String timeMeasured) {
        this.timeMeasured = timeMeasured;
    }

    // set the Measurement's systolic pressure
    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    // set the Measurement's diastolic pressure
    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    // set the Measurement's heart rate
    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    // set the Measurement's comment
    public void setComment(String comment) {
        this.comment = comment;
    }
}
