package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MeasurementRecyclerAdapter extends RecyclerView.Adapter<MeasurementRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Measurement> measurements = new ArrayList<>();

    public MeasurementRecyclerAdapter(Context context, ArrayList<Measurement> measurements){
        this.context = context;
        this.measurements = measurements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.measurement_list_layout, parent, false);
        MeasurementRecyclerAdapter.ViewHolder viewHolder = new MeasurementRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Integer systolic = measurements.get(position).getSystolicPressure();
        Integer diastoic = measurements.get(position).getDiastolicPressure();
        Integer heartRate = measurements.get(position).getHeartRate();

        String systolicText = "Systolic Pressure: " + systolic;
        String diastoicText = "Diastolic Pressure: " + diastoic;
        String heartRateText = "Heart Rate : " + heartRate;
        String dateText = "Date Measured: " + measurements.get(position).getDateMeasured();
        holder.systolic.setText(systolicText);
        holder.diastolic.setText(diastoicText);
        holder.heartRate.setText(heartRateText);
        holder.date.setText(dateText);

        Boolean normalPressure = systolic >= 90 && systolic <= 140 && diastoic >= 60 && diastoic <= 90;

        if (!normalPressure){
            holder.parentLayout.setBackgroundColor(context.getColor(R.color.red));
        }

        holder.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.goToAddEditMeasurementActivity(context,
                        AddOrEditMeasurement.EDIT, measurements.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout parentLayout;
        ImageView rightArrow;
        TextView systolic;
        TextView diastolic;
        TextView heartRate;
        TextView date;


        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.measurement_list_parent_view);
            rightArrow = itemView.findViewById(R.id.list_right_arrow);
            systolic = itemView.findViewById(R.id.list_systolic);
            diastolic = itemView.findViewById(R.id.list_diastolic);
            heartRate = itemView.findViewById(R.id.list_heart_rate);
            date = itemView.findViewById(R.id.list_date);

        }

    }
}
