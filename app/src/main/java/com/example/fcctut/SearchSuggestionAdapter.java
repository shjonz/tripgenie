package com.example.fcctut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchSuggestionAdapter extends ArrayAdapter<Place> implements Filterable {
    // A list of places to be displayed in the adapter.
    private List<Place> places;

    // A filtered version of the places list.
    private List<Place> placesFiltered;

    // Constructor for the adapter.
    public SearchSuggestionAdapter(Context context, List<Place> places) {
        // Call the constructor of the superclass, which initializes the ArrayAdapter with the given data and layout.
        super(context, R.layout.dropdown_item, places);

        // Store the list of places.
        this.places = places;

        // Create a copy of the places list to use for filtering.
        this.placesFiltered = new ArrayList<>(places);
    }

    // Overridden method that specifies the view to be displayed for each item in the list.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // If the view is null, inflate it from the layout file.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item, parent, false);
        }

        // Get the text view that displays the place name.
        TextView tvDropdownItem = convertView.findViewById(R.id.tv_dropdown_item);

        // Get the place at the given position.
        Place place = getItem(position);

        // If the place is not null, set the text of the text view to the name of the place.
        if (place != null) {
            tvDropdownItem.setText(place.getName());
        }

        // Return the view to be displayed.
        return convertView;
    }
    public List<Place> getPlaces() {
        return placesFiltered;
    }
    // Overridden method that specifies the filter used to display search suggestions.
    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            // This method is called in a background thread and performs the filtering of the data.
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // Create a new FilterResults object.
                FilterResults filterResults = new FilterResults();

                // If the constraint (i.e., search query) is not null.
                if (constraint != null) {
                    // Create a list to store the filtered suggestions.
                    List<Place> suggestions = new ArrayList<>();

                    // Iterate over the filtered places list and add the ones that match the search query to the suggestions list.
                    for (Place place : placesFiltered) {
                        if (place.getAddress().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            suggestions.add(place);
                        }
                    }

                    // Set the values and count of the filter results to the suggestions list.
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }

                // Return the filter results.
                return filterResults;
            }



            // This method is called in the UI thread and updates the view with the filtered data.
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // If the results are not null and contain data.
                if (results != null && results.count > 0) {
                    // Clear the current adapter data and add the filtered data.
                    clear();
                    addAll((List<Place>) results.values);
                    notifyDataSetChanged();
                } else {
                    // If the results are null or empty, notify
                }
            }
        };
    }
}
