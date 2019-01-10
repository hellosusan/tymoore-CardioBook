package com.example.tymoore.tymoore_cardiobook;

public class Measurement {
    private Integer measurementID;
    private String dateMeasured;
    private String timeMeasured;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Integer heartRate;
    private String comment;

    public Measurement(Integer measurementID, String dateMeasured, String timeMeasured, Integer systolicPressure, Integer diastolicPressure, Integer heartRate, String comment) {
        this.measurementID = measurementID;
        this.dateMeasured = dateMeasured;
        this.timeMeasured = timeMeasured;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MeasurementID : " + measurementID + ", DateMeasured: " + dateMeasured +
                ", TimeMeasured: " + timeMeasured + ", SystolicPressure: " + systolicPressure +
                ", DiastolicPressure: " + diastolicPressure + ", HeartRate: " + heartRate +
                ", Comment: " + comment;
    }
}
