package com.example.fcctut;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class to represent a place fetched from the Places API
public class Place implements Comparable<Place> {
    // Serialized field for the place ID
    @SerializedName("place_id")
    private String placeId;

    // Getter method for place_id
    public String getPlaceId() {
        return placeId;
    }

    // Serialized field for the place name
    @SerializedName("name")
    private String name;

    // Serialized field for the place geometry (contains location data)
    @SerializedName("geometry")
    private Geometry geometry;

    // Serialized field for the place popularity (rating)
    @SerializedName("rating")
    private double popularity;

    // Non-serialized field for the distance from the user's location
    private double distance;

    // Getter methods
    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getDistance() {
        return distance;
    }

    // Setter method for the distance field
    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Method to compare two Place objects based on distance and popularity
    // Implements the compareTo method from the Comparable interface
    // This method is used in the Recommendations Button to rank the recommendations based on distance
    @Override
    public int compareTo(Place other) {
        // Sort the places by distance first, then by popularity
        if (this.distance < other.distance) {
            return -1;
        } else if (this.distance > other.distance) {
            return 1;
        } else {
            return Double.compare(other.popularity, this.popularity);
        }
    }

    // Nested class to represent the geometry of a place
    public static class Geometry {
        // Serialized field for the location data (latitude and longitude)
        @SerializedName("location")
        private Location location;

        // Getter method for the location data
        public Location getLocation() {
            return location;
        }
    }

    // Nested class to represent the location of a place (latitude and longitude)
    public static class Location {
        // Serialized field for the latitude value
        @SerializedName("lat")
        private double latitude;

        // Serialized field for the longitude value
        @SerializedName("lng")
        private double longitude;

        // Getter method for the latitude value
        public double getLatitude() {
            return latitude;
        }

        // Getter method for the longitude value
        public double getLongitude() {
            return longitude;
        }
    }
    // Used for the autocompletion  of the location in the New Locations page
    public Place(String placeId, String name, String address) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
    }
    private String address;
    public String getAddress() {
        return address;
    }

    // Serialized field for the place opening hours
    @SerializedName("opening_hours")
    private OpeningHours openingHours;

    // Getter method for opening_hours
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    // Nested class to represent the opening and closing hours of a place
    public static class OpeningHours {
        // Serialized field for the weekday hours
        @SerializedName("weekday_text")
        private List<String> weekdayText;

        // Getter method for the weekday hours
        public List<String> getWeekdayText() {
            return weekdayText;
        }

        // Method to get opening and closing hours for each day of the week
        public Map<String, String[]> getOpeningClosingHours() {
            // Create a map to store the opening and closing hours for each day of the week
            Map<String, String[]> openingClosingHours = new HashMap<>();

            // Loop through the weekdayText list which contains the opening hours string for each day
            for (String dayHours : weekdayText) {
                // Split the dayHours string into two parts at the first occurrence of ": "
                // The first part will be the day of the week, and the second part will be the hours
                String[] parts = dayHours.split(": ", 2);
                String dayOfWeek = parts[0];
                String hours = parts[1];

                // If the hours string is "Closed", the place is closed for the entire day
                if (hours.equalsIgnoreCase("Closed")) {
                    // Store "Closed" for both opening and closing hours in the map
                    openingClosingHours.put(dayOfWeek, new String[] { "Closed", "Closed" });
                } else {
                    // Otherwise, split the hours string at " – " to get the opening and closing hours
                    String[] timeRange = hours.split(" – ");
                    // Store the opening and closing hours in the map with the dayOfWeek as the key
                    openingClosingHours.put(dayOfWeek, timeRange);
                }
            }

            // Return the map containing the opening and closing hours for each day of the week
            return openingClosingHours;
        }
    }
    //Result is the a map where the key is the day of the week and the value is an array containing the opening and closing hours


}
