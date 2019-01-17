package com.example.tymoore.tymoore_cardiobook;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// This class handles the RecyclerView which displays the list of measurements on the main activity.
// This class is responsible for the binding of the data to visual elements. It also starts the
// transition to the edit measurement activity. It also indicates abnormal measurements by
// highlighting them as red.
public class MeasurementRecyclerAdapter extends RecyclerView.Adapter<MeasurementRecyclerAdapter.ViewHolder>{

    private Context context; // the current measurement
    private ArrayList<Measurement> measurements = new ArrayList<>(); // the list of measurements

    // the constructor, sets the context and measurements to display
    public MeasurementRecyclerAdapter(Context context, ArrayList<Measurement> measurements){
        this.context = context;
        this.measurements = measurements;
    }

    // When the view holder is created, it needs to be initalized,
    // this method initializes it by linking the recycler view to the proper xml file.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.measurement_list_layout, parent, false);
        MeasurementRecyclerAdapter.ViewHolder viewHolder = new MeasurementRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Binds the data of each element to the corresponding visual element defined in the xml file.
    // also determines if the pressure is normal, if not then the background of the element is
    // highlighted red to indicate the irregularity. Also sets the on click listener of the
    // right arrow button, so that when it is clicked the edit screen will be initialized.
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

        // if normal pressure then background is white, otherwise it is red.
        Boolean normalPressure = systolic >= 90 && systolic <= 140 && diastoic >= 60 && diastoic <= 90;
        if (normalPressure){
            holder.parentLayout.setBackgroundColor(context.getColor(R.color.white));
        }
        else{
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

    // gets the itemcount in order to determine how many elements to display
    @Override
    public int getItemCount() {
        return measurements.size();
    }

    // Inner Class ViewHolder defines all of the elements in the corresponding xml file.
    // it allows us to set their properties during onBind.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout parentLayout;
        ImageView rightArrow;
        TextView systolic;
        TextView diastolic;
        TextView heartRate;
        TextView date;


        // ViewHolder constructor
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
