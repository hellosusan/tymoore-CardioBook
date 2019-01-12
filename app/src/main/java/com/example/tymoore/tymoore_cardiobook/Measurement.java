package com.example.tymoore.tymoore_cardiobook;

import java.util.UUID;

public class Measurement {
    private UUID ID;
    private String dateMeasured;
    private String timeMeasured;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Integer heartRate;
    private String comment;


    public Measurement(String dateMeasured, String timeMeasured, Integer systolicPressure, Integer diastolicPressure, Integer heartRate, String comment) {
        this.dateMeasured = dateMeasured;
        this.timeMeasured = timeMeasured;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;

        this.ID = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "ID: " + ID + ", DateMeasured: " + dateMeasured +
                ", TimeMeasured: " + timeMeasured + ", SystolicPressure: " + systolicPressure +
                ", DiastolicPressure: " + diastolicPressure + ", HeartRate: " + heartRate +
                ", Comment: " + comment;
    }

    public String getDateMeasured() {
        return dateMeasured;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public UUID getID() {
        return ID;
    }
}
