package com.example.fcctut;

import com.google.android.libraries.places.api.model.Period;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.OpeningHours;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class PlacesSchedule {
    String address;
    String place_Id;
    Double latitude;
    Double longitude;
    LocalTime openingHours;
    LocalTime closingHours;
    Integer distance_From_point;
    Integer duration_From_point;

    Duration time_spent; //time spent at that location

    boolean eating_place;

    String origin;

    boolean open_now;

    public boolean getEating_place() {
        return eating_place;
    }

    public void setEating_place(boolean eating_place) {
        this.eating_place = eating_place;
    }

    public Duration getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(Duration time_spent) {
        this.time_spent = time_spent;
    }

    public boolean getOpen_now() {
        return open_now;
    }

    public void setOpen_now(LocalTime current) {
        if (current.isBefore( this.closingHours ) && current.isAfter( this.openingHours) ) {
            this.open_now = true;
        } else {
            this.open_now = false;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    public LocalTime getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }

    public Integer getDistance_From_point() {
        return distance_From_point;
    }

    public void setDistance_From_point(String origin) {
        calc_Dist_two_places(origin);

    }

    public long getDuration_From_point() {
        return duration_From_point;
    }

    public void setDuration_From_point(Integer duration_From_point) {
        this.duration_From_point = duration_From_point;
    }

    public PlacesSchedule(String addr) {

        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(BuildConfig.WEB_API_KEY)
                    .build();
            //GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(place.lat, place.lng)).await();
            GeocodingResult[] results = GeocodingApi.geocode(context, addr).await();
            if (results.length > 0) {
                this.place_Id = results[0].placeId;
                this.address = results[0].formattedAddress;
                this.latitude = results[0].geometry.location.lat;
                this.longitude = results[0].geometry.location.lng;
                //PlaceDetails placeDetails = PlacesApi.placeDetails(context, this.place_Id).await();
                //String openingHours = placeDetails.openingHours.toString();
                //this.openingHours = openingHours;


//                PlaceDetails placeDetails = PlacesApi.placeDetails(context, this.place_Id).await();
//                OpeningHours openingHours = placeDetails.openingHours;
//                if (openingHours != null) {
//                    OpeningHours.Period[] periods = openingHours.periods;
//                    System.out.println("opening hours: " + Arrays.asList(periods) + " " + Arrays.toString(periods) );
//
//
//                    //this.openingHours = LocalTime.parse(  );
//                } if (openingHours == null ) {
//                    System.out.println("open 24/7 ");
//                }





            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void calc_Dist_two_places(String origin) {
        try {
            System.out.println("origin to this address: " + origin + " to " + this.address);
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(BuildConfig.WEB_API_KEY)
                    .build();
            DirectionsResult result =
                    DirectionsApi.newRequest(context)
                            .mode(TravelMode.TRANSIT)
                            .units(Unit.METRIC)
                            .origin(origin)
                            .destination(this.address)
                            .await();

            DirectionsRoute route = result.routes[0];
            System.out.println("inside calc dist " + route);
            long durationInSecs = route.legs[0].duration.inSeconds;
            long distanceInMeters = route.legs[0].distance.inMeters;
            this.duration_From_point = Math.toIntExact(durationInSecs);
            this.distance_From_point = Math.toIntExact(distanceInMeters);
            System.out.println("calc_dist_two_places origin: " + origin
                    + " origin to address " + this.address+ " dist " + this.distance_From_point + " duration " + this.duration_From_point);
            context.shutdown();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }








}
