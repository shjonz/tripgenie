package com.example.fcctut;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    // List of places to be displayed
    public List<Place> places;

    // Listener for add place button clicks
    private OnAddPlaceClickListener mOnAddPlaceClickListener;

    public PlaceAdapter(Context context, List<Place> places, OnAddPlaceClickListener onAddPlaceClickListener) {
        this.places = places;
        mOnAddPlaceClickListener = onAddPlaceClickListener;
    }

    /*
     * onCreateViewHolder()
     * Called when a new ViewHolder is created.
     * Inflates the layout for the place item view and returns a new PlaceViewHolder.
     * @return A new PlaceViewHolder with the inflated place_item view.
     */
    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new PlaceViewHolder(view);
    }

    /*
     * onBindViewHolder()
     * Called when a ViewHolder is bound to a position.
     * Binds the place data to the views in the PlaceViewHolder.
     * Sets an OnClickListener for the addPlaceButton.
     */
    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = places.get(position);
        holder.placeName.setText(place.getName());
        Log.d("PlaceAdapter", "Binding place: " + place.getName());

        holder.addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();

                // Check if the listener is set and the position is valid
                if (mOnAddPlaceClickListener != null && currentPosition != RecyclerView.NO_POSITION) {
                    // Call the listener's onAddPlaceClick method
                    mOnAddPlaceClickListener.onAddPlaceClick(currentPosition);

                    // Update the saved places in SharedPreferences
                    List<Place> savedPlaces = SharedPreferenceUtil.getSavedPlaces(view.getContext());

                    // Check if the place already exists in the savedPlaces list
                    boolean placeExists = false;
                    for (Place place : savedPlaces) {
                        if (place.getPlaceId().equals(places.get(currentPosition).getPlaceId())) {
                            placeExists = true;
                            break;
                        }
                    }

                    // If the place doesn't already exist, add it to the savedPlaces list and save it
                    if (!placeExists) {
                        savedPlaces.add(places.get(currentPosition));
                        SharedPreferenceUtil.savePlaces(view.getContext(), savedPlaces);
                    }

                    // Remove the item from the list
                    places.remove(currentPosition);
                    // Notify the adapter that the item has been removed
                    notifyItemRemoved(currentPosition);
                    // Notify the adapter about the change in the data set
                    notifyItemRangeChanged(currentPosition, places.size());
                }
            }
        });
    }

    /*
     * getItemCount()
     * Returns the total number of items in the places list.
     * @return The number of items in the places list.
     */
    @Override
    public int getItemCount() {
        return places.size();
    }

    /*
     * OnAddPlaceClickListener
     * An interface for handling add place button clicks.
     */
    public interface OnAddPlaceClickListener {
        void onAddPlaceClick(int position);
    }

    /*
     * PlaceViewHolder
     * Inner class representing the ViewHolder for the place item view.
     * Contains references to the views in the place_item layout.
     */
    class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        ImageButton addPlaceButton;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            addPlaceButton = itemView.findViewById(R.id.add_place_button);
        }
    }

    /*
     * updatePlaces()
     * Updates the places list with the given list of places and notifies the adapter of the change.
     * @param places The new list of places to be displayed.
     */
    public void updatePlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }
}
