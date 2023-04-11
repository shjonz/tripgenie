package com.example.fcctut;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Open {

    // A list of strings representing the formatted opening hours text for each day of the week.
    private List<String> weekdayText;

    // Constructs an Open object from a JSON input string.
    public Open(String jsonInput) {
        try {
            // Parse the JSON input string and create a JSONObject.
            JSONObject openingHoursJson = new JSONObject(jsonInput);

            // Extract the "weekday_text" array from the JSONObject.
            JSONArray weekdayTextArray = openingHoursJson.getJSONArray("weekday_text");

            // Initialize the weekdayText list.
            weekdayText = new ArrayList<>();

            // Iterate through the elements of the "weekday_text" array.
            for (int i = 0; i < weekdayTextArray.length(); i++) {
                // Add each element to the weekdayText list.
                weekdayText.add(weekdayTextArray.getString(i));
            }
        } catch (JSONException e) {
            // If there is an exception while parsing the JSON input, print the stack trace and
            // initialize an empty weekdayText list.
            e.printStackTrace();
            weekdayText = new ArrayList<>();
        }
    }

    // Returns the list of formatted opening hours text for each day of the week.
    public List<String> getWeekdayText() {
        return weekdayText;
    }
}

/*
// Output :
Monday: 9:00 AM – 5:00 PM
        Tuesday: 9:00 AM – 5:00 PM
        Wednesday: 9:00 AM – 5:00 PM
        Thursday: 9:00 AM – 5:00 PM
        Friday: 9:00 AM – 5:00 PM
        Saturday: 10:00 AM – 4:00 PM
        Sunday: Closed
//*/
