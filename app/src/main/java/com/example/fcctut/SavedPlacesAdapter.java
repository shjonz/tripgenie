package com.example.fcctut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// The SavedPlacesAdapter is a custom adapter class for a RecyclerView displaying a list of saved places.
public class SavedPlacesAdapter extends RecyclerView.Adapter<SavedPlacesAdapter.ViewHolder> {

    // Declare member variables
    private List<Place> savedPlaces; // A list of Place objects representing saved places
    private Context mContext; // The context in which the adapter is operating
    private OnPlaceClickListener mOnPlaceClickListener; // A custom click listener for handling place clicks

    // The constructor for the SavedPlacesAdapter
    public SavedPlacesAdapter(Context context, List<Place> savedPlaces, OnPlaceClickListener onPlaceClickListener) {
        this.mContext = context;
        this.savedPlaces = savedPlaces;
        this.mOnPlaceClickListener = onPlaceClickListener;
    }

    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_details_item, parent, false);
        // Create a new ViewHolder with the inflated layout and return it
        return new ViewHolder(view, mOnPlaceClickListener);
    }

    // Called by RecyclerView to display the data at the specified position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the Place object at the current position
        Place place = savedPlaces.get(position);
       // TextView txtViewAddressOfPlace = holder.txtViewNameOfPlace;
        // Set the place name in the corresponding TextView
        holder.placeName.setText(place.getName());

// Set an OnClickListener for the "unsave" button
        holder.unsaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition(); // Get the current position in the RecyclerView
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Remove the item from the data list
                    savedPlaces.remove(currentPosition);
                    // Update the saved places in SharedPreferences
                    SharedPreferenceUtil.savePlaces(mContext, savedPlaces);
                    // Notify the adapter that the item has been removed
                    notifyItemRemoved(currentPosition);
                    // Notify the adapter about the change in the data set
                    notifyItemRangeChanged(currentPosition, savedPlaces.size());
                }
            }
        });
    }
    // Returns the total number of items in the data set held by the adapter
    @Override
    public int getItemCount() {
        return savedPlaces.size();
    }

    // ViewHolder class representing the views in each item of the RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView placeName; // TextView displaying the place name
        Button addToItineraryButton; // Button to add the place to the itinerary
        Button unsaveButton; // Button to unsave the place
        OnPlaceClickListener onPlaceClickListener; // Custom click listener for handling place clicks

        // ViewHolder constructor
        public ViewHolder(@NonNull View itemView, OnPlaceClickListener onPlaceClickListener) {
            super(itemView);
            // Initialize the views
            placeName = itemView.findViewById(R.id.place_name);
            addToItineraryButton = itemView.findViewById(R.id.add_to_itinerary_button);
            unsaveButton = itemView.findViewById(R.id.unsave_button);
            this.onPlaceClickListener = onPlaceClickListener;
            // Set an OnClickListener for the "add to itinerary" button
            addToItineraryButton.setOnClickListener(this);
        }

        // Called when a view is clicked
        @Override
        public void onClick(View view) {
            onPlaceClickListener.onPlaceClick(getAdapterPosition());
        }
    }

    public interface OnPlaceClickListener {
        void onPlaceClick(int position);
    }
}

