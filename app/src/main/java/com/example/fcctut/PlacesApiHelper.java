package com.example.fcctut;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// PlacesApiHelper is a helper class that handles the communication with the Google Places API.
public class PlacesApiHelper {

    // Callback interface to handle the result of API requests.
    public interface PlacesApiCallback {
        void onPlacesFetched(List<Place> places);
        void onFailure();
    }


    // New interface to handle the result of the Place Details API requests.
    public interface PlaceDetailsCallback {
        void onPlaceDetailsFetched(Place place);
        void onFailure();
    }

    // Standard URL retrieved  from the Google Places API
    private static final String API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";



    // Method to fetch places based on the user's current location, radius, and API key.
    public static void fetchPlaces(double latitude, double longitude, int radius, String placetype,String rankBy, String apiKey, PlacesApiCallback callback) {
        Log.d("PlacesApiHelper", "fetchPlaces called");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL for the API request.
        HttpUrl url = HttpUrl.parse(API_BASE_URL).newBuilder()
                .addQueryParameter("location", String.format("%f,%f", latitude, longitude))
                .addQueryParameter("radius", String.valueOf(radius))
                .addQueryParameter("type", placetype)
                .addQueryParameter("rankby", rankBy) // rankBy parameter
                .addQueryParameter("key", apiKey)
                .addQueryParameter("fields", "place_id,name,geometry,rating,opening_hours") // fields parameter
                .build();

        Log.d("PlacesApiHelper", "Request URL: " + url.toString());

        // Create and enqueue the API request.
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make an asynchronous API call using OkHttpClient
        client.newCall(request).enqueue(new Callback() {
            // If the API call fails, this method will be called
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("PlacesApiHelper", "Request failed: " + e.getMessage());
                callback.onFailure();
            }

            // This method will be called if the API call is successful
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // If the response is successful, parse the JSON response
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Log.d("PlacesApiHelper", "API response: " + jsonResponse);
                    PlacesApiResponse placesApiResponse = gson.fromJson(jsonResponse, PlacesApiResponse.class);
                    List<Place> places = placesApiResponse.getResults();
                    Log.d("PlacesApiHelper", "Fetched " + places.size() + " places");

                    // Sort places by their distance to the user's location.
                    places.sort(new Comparator<Place>() {
                        @Override
                        public int compare(Place p1, Place p2) {
                            // Create Location objects for the two places being compared
                            Location loc1 = new Location("");
                            loc1.setLatitude(p1.getGeometry().getLocation().getLatitude());
                            loc1.setLongitude(p1.getGeometry().getLocation().getLongitude());

                            Location loc2 = new Location("");
                            loc2.setLatitude(p2.getGeometry().getLocation().getLatitude());
                            loc2.setLongitude(p2.getGeometry().getLocation().getLongitude());

                            // Create a Location object for the user's location
                            Location userLocation = new Location("");
                            userLocation.setLatitude(latitude);
                            userLocation.setLongitude(longitude);

                            // Calculate the distances between the user's location and the two places
                            float distance1 = loc1.distanceTo(userLocation);
                            float distance2 = loc2.distanceTo(userLocation);

                            // Set the distances for the places
                            p1.setDistance(distance1);
                            p2.setDistance(distance2);

                            // Compare the distances and sort the places accordingly
                            return Float.compare(distance1, distance2);
                        }
                    });
                    // Return the fetched places to the callback method
                    callback.onPlacesFetched(places);
                } else {
                    // If the response is not successful, log the error and call the onFailure() method of the callback
                    Log.e("PlacesApiHelper", "Unsuccessful response: " + response.code());
                    callback.onFailure();
                }
            }
        });
    }

    // Method to fetch place details based on the place ID and API key.
    public static void getPlaceDetails(String placeId, String apiKey, PlaceDetailsCallback callback) {
        Log.d("PlacesApiHelper", "getPlaceDetails called");

        // Fetch the place details using the existing fetchPlaceDetails method
        fetchPlaceDetails(placeId, apiKey, new PlacesApiCallback() {
            @Override
            public void onPlacesFetched(List<Place> places) {
                // If the place details are fetched successfully, return the first place in the list
                if (places != null && !places.isEmpty()) {
                    callback.onPlaceDetailsFetched(places.get(0));
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure() {
                callback.onFailure();
            }
        });
    }

    // Method to fetch place details based on the place ID and API key.
    public static void fetchPlaceDetails(String placeId, String apiKey, PlacesApiCallback callback) {
        Log.d("PlacesApiHelper", "fetchPlaceDetails called");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().create();

        // Build the URL for the API request.
        HttpUrl url = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/details/json").newBuilder()
                .addQueryParameter("placeid", placeId)
                .addQueryParameter("key", apiKey)
                .addQueryParameter("fields", "place_id,name,geometry,rating,opening_hours") //fields parameter
                .build();
        Log.d("PlacesApiHelper", "==============Request URL: " + url.toString());

        // Create and enqueue the API request.
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make an asynchronous API call using OkHttpClient
        client.newCall(request).enqueue(new Callback() {
            // If the API call fails, this method will be called
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("PlacesApiHelper", "Request failed: " + e.getMessage());
                // Notify the caller that the request failed
                callback.onFailure();
            }

            // This method will be called if the API call is successful
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // If the response is successful, parse the JSON response
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Log.d("PlacesApiHelper", "API response: " + jsonResponse);
                    // Deserialize the JSON response using Gson into a PlaceDetailsApiResponse object
                    PlaceDetailsApiResponse placeDetailsApiResponse = gson.fromJson(jsonResponse, PlaceDetailsApiResponse.class);
                    // Get the place details from the deserialized response
                    Place place = placeDetailsApiResponse.getResult();
                    // Pass the fetched place to the callback method wrapped in a singleton list
                    callback.onPlacesFetched(Collections.singletonList(place));
                } else {
                    // If the response is not successful, log the error and call the onFailure() method of the callback
                    Log.e("PlacesApiHelper", "Unsuccessful response: " + response.code());
                    callback.onFailure();
                }

            }
        });
    }

    // Class to handle the response for the Place Details API.
    private static class PlaceDetailsApiResponse {
        @SerializedName("result")
        private Place result;

        public Place getResult() {
            return result;
        }
    }
    // Class to handle the response for the Nearby Search API.
    private static class PlacesApiResponse {
        @SerializedName("results")
        private List<Place> results;

        public List<Place> getResults() {
            return results;
        }
    }
}




