package com.example.fcctut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ItineraryListViewAdapter extends
        RecyclerView.Adapter<ItineraryListViewAdapter.ViewHolder> {

    private ArrayList<Place> places;

    public ItineraryListViewAdapter(ArrayList<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.itinerary_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Place place = places.get(position);

        // Set item views based on your views and data model
        TextView txtViewNameOfPlace = holder.txtViewNameOfPlace;
        txtViewNameOfPlace.setText(place.getAddress());
        TextView txtViewTime = holder.txtViewTime;
        //TODO: change this to time obtained
//        txtViewTime.setText(String.valueOf(place.getPopularity()));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtViewNameOfPlace;
        public TextView txtViewTime;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtViewNameOfPlace = (TextView) itemView.findViewById(R.id.txtViewNameOfPlace);
            txtViewTime = (TextView) itemView.findViewById(R.id.txtViewTime);
        }
    }
}