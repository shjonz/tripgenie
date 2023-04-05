package com.example.fcctut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class PlacesDetailsRecyclerViewAdapter extends RecyclerView.Adapter<PlacesDetailsRecyclerViewAdapter.MyViewHolder>  {

    public int layout_id;
    protected List<?> dataList = new ArrayList<>();
    public View itemview;
    Context context;
    ArrayList<PlacesDetailsModel> placesDetailsModels;

    //constructor to get values for variables
    public PlacesDetailsRecyclerViewAdapter(Context context, ArrayList<PlacesDetailsModel> placesDetailsModels) {

        this.context = context;
        this.placesDetailsModels = placesDetailsModels;
    }

    //inflate layout (give each row its look)
    @NonNull
    @Override
    public PlacesDetailsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.saved_locations, parent , false);
        return new PlacesDetailsRecyclerViewAdapter.MyViewHolder(view);
    }

    //assign values to views we created in recyclerview_row layout file
    // based on position of recycler view(?)
    @Override
    public void onBindViewHolder(@NonNull PlacesDetailsRecyclerViewAdapter.MyViewHolder holder, int position) {
        //set values to ui
        holder.addressView.setText(placesDetailsModels.get(position).getAddress());
        holder.openingHourView.setText(placesDetailsModels.get(position).getOpeningHours());
    }

    //recyclerview wants to know how many items to display
    @Override
    public int getItemCount() {
        return placesDetailsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //akin to OnCreate: grab views from recycler view row layout and assign them to varaibles
        TextView addressView, openingHourView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            addressView = itemView.findViewById(R.id.addressTextView);
            openingHourView = itemView.findViewById(R.id.openingHourTextView);




        }

    }


}

