// Import necessary classes
package com.example.fcctut;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Create a PlaceAdapter class that extends RecyclerView.Adapter
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    // Declare a List of Place objects to store the data
    private List<Place> places;

    // Constructor for the PlaceAdapter class takes a List of Place objects as input
    public PlaceAdapter(List<Place> places) {
        this.places = places;
    }

    // onCreateViewHolder method inflates the layout for each place item and returns a new PlaceViewHolder
    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the place_item layout and create a new View object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        // Return a new PlaceViewHolder object with the inflated view
        return new PlaceViewHolder(view);
    }

    // onBindViewHolder method sets the data for each item in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        // Get the Place object at the current position
        Place place = places.get(position);
        // Set the text for the placeName TextView in the PlaceViewHolder
        holder.placeName.setText(place.getName());
        // Log the binding process for debugging purposes
        Log.d("PlaceAdapter", "Binding place: " + place.getName());
    }

    // getItemCount method returns the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return places.size();
    }

    // Create a PlaceViewHolder class that extends RecyclerView.ViewHolder
    class PlaceViewHolder extends RecyclerView.ViewHolder {
        // Declare a TextView variable for the placeName
        TextView placeName;

        // Constructor for the PlaceViewHolder class takes a View object as input
        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the placeName TextView from the itemView
            placeName = itemView.findViewById(R.id.placeName);
        }
    }
    public void updatePlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }
}
