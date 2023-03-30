package com.example.fcctut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// The RecommendationAdapter is responsible for binding the data of places to the RecyclerView.
public class RecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Define constants for header and item view types
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    // List containing both headers (String) and Place items (Place objects)
    private List<Object> recommendations;

    // Constructor to initialize the adapter with the given list of recommendations
    public RecommendationAdapter(List<Object> recommendations) {
        this.recommendations = recommendations;
    }

    // Method to add Place items to the recommendations list at a specific index and notify the adapter
    public void addPlaces(int index, List<Place> items) {
        if (recommendations == null) {
            recommendations = new ArrayList<>();
        }
        recommendations.addAll(index, items);
        notifyDataSetChanged();
    }

    // Method to determine the view type based on the item at the given position
    @Override
    public int getItemViewType(int position) {
        if (recommendations.get(position) instanceof String) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    // Method to create a ViewHolder based on the viewType and inflate the appropriate layout
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

    // Method to bind data to the ViewHolder based on the item's position
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
        }
    }

    // Method to get the total count of items in the recommendations list
    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    // ViewHolder class for header items
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.header_text_view);
        }
    }

    // ViewHolder class for Place items
    public static class RecommendationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView distanceTextView;

        public RecommendationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            distanceTextView = itemView.findViewById(R.id.distance_text_view);
        }
    }
}
