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
public class RecyclerViewDatesItemAdapter extends
        RecyclerView.Adapter<RecyclerViewDatesItemAdapter.ViewHolder> {

    private ArrayList<ArrayList<Place>> places;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.itinerary_dates_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set item views based on your views and data model
        TextView textView = holder.txtViewDatesItem;
        textView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public RecyclerViewDatesItemAdapter(ArrayList<ArrayList<Place>> places) {
        this.places = places;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtViewDatesItem;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtViewDatesItem = (TextView) itemView.findViewById(R.id.txtViewDateItem);
        }
    }
}