/*
 * RecommendationAdapter.java
 * This adapter is used to display recommendations for places of interest and places to eat in a RecyclerView.
 */

package com.example.fcctut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<Object> recommendations;

    public RecommendationAdapter(Context context, List<Object> recommendations) {
        this.mContext = context;
        this.recommendations = recommendations;
    }

    /*
     * addPlaces(int index, List<Place> items)
     * Adds the given list of places to the recommendations list at the given index.
     * @param index The index at which to insert the list of places in the recommendations list.
     * @param items The list of places to add to the recommendations list.
     */
    public void addPlaces(int index, List<Place> items) {
        if (recommendations == null) {
            recommendations = new ArrayList<>();
        }
        recommendations.addAll(index, items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (recommendations.get(position) instanceof String) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    /*
     * onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
     * Creates a new ViewHolder for the specified view type and returns it.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder for the specified view type.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.header_item, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.recommendation_item, parent, false);
            return new RecommendationViewHolder(view);
        }
    }

    /*
     * onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
     * Binds the data at the specified position to the given ViewHolder.
     * @param holder The ViewHolder to bind the data to.
     * @param position The position of the data in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTextView.setText((String) recommendations.get(position));
        } else {
            RecommendationViewHolder recommendationHolder = (RecommendationViewHolder) holder;
            Place place = (Place) recommendations.get(position);
            recommendationHolder.nameTextView.setText(place.getName());
            recommendationHolder.distanceTextView.setText(String.format(Locale.getDefault(), "%.1f km", place.getDistance() / 1000));

            // Set an OnClickListener for the "add_recommendation_button"
            recommendationHolder.addRecommendationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = holder.getAdapterPosition(); // Get the current position in the RecyclerView
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        // Save the item to SharedPreferences
                        Place placeToSave = (Place)
                                recommendations.get(currentPosition);
                        List<Place> savedPlaces = SharedPreferenceUtil.getSavedPlaces(mContext);
                        savedPlaces.add(placeToSave);
                        SharedPreferenceUtil.savePlaces(mContext, savedPlaces);

                        // Remove the item from the data list
                        recommendations.remove(currentPosition);
                        // Notify the adapter that the item has been removed
                        notifyItemRemoved(currentPosition);
                        // Notify the adapter about the change in the data set
                        notifyItemRangeChanged(currentPosition, recommendations.size());
                    }
                }
            });
        }
    }

    /*
     * getItemCount()
     * Returns the total number of items in the recommendations list.
     * @return The number of items in the recommendations list.
     */
    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    /*
     * HeaderViewHolder
     * A ViewHolder for the header item in the RecyclerView.
     */
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.header_text_view);
        }
    }

    /*
     * RecommendationViewHolder
     * A ViewHolder for a recommendation item in the RecyclerView.
     */
    public static class RecommendationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView distanceTextView;
        ImageButton addRecommendationButton;

        public RecommendationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            distanceTextView = itemView.findViewById(R.id.distance_text_view);
            addRecommendationButton = itemView.findViewById(R.id.add_recommendation_button);
        }
    }
}