package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // database constants
    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "CardioBook.db";

    // Measurements Table
    private static final String MeasurementsTableName = "Measurements";
    private static final String MeasurementsColumnID = "MID";
    private static final String MeasurementsColumnDate = "Date";
    private static final String MeasurementsColumnTime = "Time";
    private static final String MeasurementsColumnSystolic = "Systolic";
    private static final String MeasurementsColumnDiastolic = "Diastolic";
    private static final String MeasurementsColumnHeartRate = "HeartRate";
    private static final String MeasurementsColumnComments = "Comments";

    SQLiteDatabase database;
    public DatabaseHandler(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        database = getWritableDatabase();
        onCreate(database);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + MeasurementsTableName + " ( " +
                        MeasurementsColumnID + " INTEGER PRIMARY KEY, " +
                        MeasurementsColumnDate + " TEXT NOT NULL, " +
                        MeasurementsColumnTime + " TEXT NOT NULL," +
                        MeasurementsColumnSystolic + " INTEGER NOT NULL," +
                        MeasurementsColumnDiastolic + " INTEGER NOT NULL," +
                        MeasurementsColumnHeartRate + " INTEGER NOT NULL," +
                        MeasurementsColumnComments + " TEXT" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MeasurementsTableName);
        onCreate(sqLiteDatabase);
    }

    public void MeasurementsInsertRow(String date, String time, int systolic, int diastolic, int heartRate, String comments){
        database.execSQL(
                "INSERT INTO " + MeasurementsTableName + " (" +
                        MeasurementsColumnDate + ", " +
                        MeasurementsColumnTime + ", " +
                        MeasurementsColumnSystolic + ", " +
                        MeasurementsColumnDiastolic + ", " +
                        MeasurementsColumnHeartRate + ", " +
                        MeasurementsColumnComments +
                        ")" +
                        " VALUES (" +
                        "'" + date + "'" + ", " +
                        "'" + time + "'" + ", " +
                        Integer.toString(systolic) + ", " +
                        Integer.toString(diastolic) + ", " +
                        Integer.toString(heartRate) + ", " +
                        "'" + comments + "'" +
                        " )");
    }


    public void MeasurementsRemoveRow(int mid){
        database.execSQL("DELETE FROM " + MeasurementsTableName + " WHERE " + MeasurementsColumnID + " IS " + Integer.toString(mid));
    }

    public ArrayList<Measurement> MeasurementsGetRows(){
        String[] columns = {MeasurementsColumnID, MeasurementsColumnDate, MeasurementsColumnTime,
            MeasurementsColumnSystolic, MeasurementsColumnDiastolic, MeasurementsColumnHeartRate,
            MeasurementsColumnComments};
        Cursor cursor = database.query(MeasurementsTableName,columns,null,null,null,null,null);
        ArrayList<Measurement> measurements = new ArrayList<>();
        while (cursor.moveToNext()) {
            int mid = cursor.getInt(cursor.getColumnIndex(MeasurementsColumnID));
            String date = cursor.getString(cursor.getColumnIndex(MeasurementsColumnDate));
            String time = cursor.getString(cursor.getColumnIndex(MeasurementsColumnTime));
            int systolic = cursor.getInt(cursor.getColumnIndex(MeasurementsColumnSystolic));
            int diastolic = cursor.getInt(cursor.getColumnIndex(MeasurementsColumnDiastolic));
            int heartRate = cursor.getInt(cursor.getColumnIndex(MeasurementsColumnHeartRate));
            String comment = cursor.getString(cursor.getColumnIndex(MeasurementsColumnComments));
            Measurement measurement = new Measurement(mid, date, time, systolic, diastolic,
                    heartRate, comment);
            measurements.add(measurement);
        }
        return measurements;
    }
}
