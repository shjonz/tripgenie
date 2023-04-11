package com.example.fcctut;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class to represent a place fetched from the Places API
public class Place implements Comparable<Place> {
    // Serialized field for the place ID
    @SerializedName("place_id")
    private String placeId;

    // Serialized field for the place name
    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    // Serialized field for the place popularity (rating)
    @SerializedName("rating")
    private double popularity;

    // Non-serialized field for the distance from the user's location
    @SerializedName("distance")
    private double distance;

    @SerializedName("placeOpeningHour")
    private HashMap<String, String> placeOpeningHour;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("distanceFromPoint")
    private int distanceFromPoint;

    @SerializedName("durationFromPoint")
    private int durationFromPoint;

    @SerializedName("eatingPlace")
    private boolean eatingPlace;

    // Serialized field for the place geometry (contains location data)
    @SerializedName("geometry")
    private Geometry geometry;


    // Getter method for place_id
    public String getPlaceId() {
        return placeId;
    }

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

        public Geometry(Location location) {
            this.location = location;
        }

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

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

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



    public String getAddress() {
        return address;
    }





    // Used to initialise from saved Trip
    public Place(String placeId, String name, String address, Double popularity) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.popularity = popularity;
    }

    // Used for testing
    public Place(String placeId, String name, String address, Double popularity, Double latitude, Double longitude, int distanceFromPoint, int durationFromPoint) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.popularity = popularity;
        this.geometry = new Geometry(new Location(latitude, longitude));
        this.longitude = longitude;
        this.latitude = latitude;
        this.distanceFromPoint = 20;
        this.durationFromPoint = 10;
        this.placeOpeningHour = new HashMap<String, String>();
        this.eatingPlace = false;
    }

    // returns all attributes in Place object as a JSONObject
    // currently used to store all information in Trip.java
    public JSONObject getAllAttributes() {
        JSONObject json = new JSONObject();
        try {
            json.put("placeId", getPlaceId());
            json.put("name", getName());
            json.put("address", getAddress());
            json.put("popularity", getPopularity());
            json.put("latitude", getGeometry().getLocation().getLatitude());
            json.put("longitude", getGeometry().getLocation().getLongitude());
            return json;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @SerializedName("opening_hours")
    private OpeningHours openingHours;

    // Getter method for opening_hours
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    // Nested class to represent the opening and closing hours of a place
    public static class OpeningHours {
        @SerializedName("weekday_text")
        private List<String> weekdayText;

        public List<String> getWeekdayText() {
            return weekdayText;
        }

        public Map<String, String> getOpeningHours() {
            return extractHours(0);
        }

        public Map<String, String> getClosingHours() {
            return extractHours(1);
        }

        private Map<String, String> extractHours(int index) {
            Map<String, String> hoursMap = new HashMap<>();

            for (String dayHours : weekdayText) {
                String[] parts = dayHours.split(": ", 2);
                String dayOfWeek = parts[0];
                String hours = parts[1];

                if (hours.equalsIgnoreCase("Closed")) {
                    hoursMap.put(dayOfWeek, "Closed");
                } else {
                    String[] timeRange = hours.split(" – ");
                    hoursMap.put(dayOfWeek, timeRange[index]);
                }
            }

            return hoursMap;
        }
    }

    @NonNull
    @Override
    public String toString() {
        if (getGeometry() == null) {
            return "placeId: " + placeId + ", name: " + name + ", address: " + address + ", popularity: " + popularity;
        } else {
            return "placeId: " + placeId + ", name: " + name + ", address: " + address + ", popularity: " + popularity + ", (lat,long): (" + getGeometry().getLocation().getLatitude() + "," + getGeometry().getLocation().getLongitude() + ")";
        }
    }
}
