// Import necessary classes
package com.example.fcctut;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

// Create a DividerItemDecoration class that extends RecyclerView.ItemDecoration
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    // Declare a Drawable variable for the divider
    private Drawable mDivider;

    // Constructor for the DividerItemDecoration class takes a Context and a drawable resource ID as input
    public DividerItemDecoration(Context context, int drawableResId) {
        // Initialize the divider Drawable using the Context and drawable resource ID
        mDivider = ContextCompat.getDrawable(context, drawableResId);
    }

    // onDrawOver method is called to draw the decoration over the RecyclerView items
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Calculate the left and right boundaries for the divider
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        // Get the number of child views in the RecyclerView
        int childCount = parent.getChildCount();
        // Loop through each child view
        for (int i = 0; i < childCount; i++) {
            // Get the current child view
            View child = parent.getChildAt(i);

            // Get the LayoutParams of the child view
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // Calculate the top and bottom boundaries for the divider
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            // Set the bounds of the divider and draw it on the canvas
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
