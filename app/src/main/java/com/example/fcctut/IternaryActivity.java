package com.example.fcctut;

import static java.util.stream.Collectors.toSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;





import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.maps.GeoApiContext;
//import com.google.maps.GeoApiContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class IternaryActivity extends AppCompatActivity {
    String savedPlaceslist[] = {"savedplace1", "savedplace2"}; //grab data
    //int savedPlacesimages[] = {R.drawable. , R.drawable. };
    ListView listView;
    private Button btn_toaddplaces;
    private BottomNavigationView bottomNavigationView;



    //for google org tools library
    //Loader loader = new Loader();

    //test 2 new places
    LatLng lat = new LatLng(40.75943, -73.98459);
    LatLng lat2 = new LatLng(40.64956, -73.77827);

    //arraylist
    String waypoint_order;
    ArrayList<String> destinations = new ArrayList<String>();
    Gson gson = new Gson();






    private Button homebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iternary);


        //google or tools testing dont touch
//        loader.loadNativeLibraries();
//        final TSP.DataModel data = new TSP.DataModel();  //instantiate data problem
//
//        //create routing index manager
//        RoutingIndexManager manager = new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber,  data.depot);
//
//        //create routing model
//        RoutingModel routing = new RoutingModel(manager);

        //create and register a transit callback
//        final int transitCallbackIndex = routing.registerTransitCallback( (long fromIndex, long toIndex) -> {
//            //convert from routing variable index to user node index
//            int fromNode = manager.indexToNode(fromIndex);
//            int toNode = manager.indexToNode(toIndex);
//
//            //get the distance between the two nodes
//            //double distance = data.distanceMatrix[fromNode][toNode];
//
//            //display the distance
//            return data.distanceMatrix[fromNode][toNode];
//            //System.out.println("Distance between " + fromNode + " and " + toNode + ": " + distance);
//        });
//
//        //define cost of each arc
//        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);
//
//        // Setting first solution heuristic.
//        RoutingSearchParameters searchParameters =
//                main.defaultRoutingSearchParameters()
//                        .toBuilder()
//                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
//                        .build();
//
//        // Solve the problem.
//        Assignment solution = routing.solveWithParameters(searchParameters);
//
//        // Print solution on console.
//        TSP.printSolution(routing, manager, solution);

        //end of this



        Thread getPlacesapi = new Thread(new Runnable() {
            @Override
            public void run() {
                try {






//                    //INTEGRATE K MEANS + GREEDY NEAREST DISTANCE ALGO
//                    //RUN kmeans clustering
//
//                    List<Record> records = new ArrayList<Record>();
//                    //Start adding Records
//                    List<Double> coord1 = new ArrayList<>();
//                    coord1.add(37.56819053958451);
//                    coord1.add(127.00847759788303);
//                    //location1 is a point with supposedly multiple features
//                    Map<String, List<Double>> location1 = new HashMap<>();
//                    //add distance feature
//                    location1.put("Location", coord1);
//                    //assign location1 as location of Record object address1
//                    Record address1 = new Record("263 Jangchungdan-ro, Jung-gu, Seoul, South Korea", location1);
////        System.out.println(address1
////                .getFeatures());
//
//                    List<Double> coord2 = new ArrayList<>();
//                    coord2.add(37.57020985495065);
//                    coord2.add(126.99959286887606);
//                    Map<String, List<Double>> location2 = new HashMap<>();
//                    location2.put("Location", coord2);
//                    Record address2 = new Record("88 Changgyeonggung-ro, Jongno-gu, Seoul, South Korea", location2);
//
//                    List<Double> coord3 = new ArrayList<>();
//                    coord3.add(37.57982948171247);
//                    coord3.add(126.97709462440277);
//                    Map<String, List<Double>> location3 = new HashMap<>();
//                    location3.put("Location", coord3);
//                    Record address3 = new Record("161 Sajik-ro, Jongno-gu, Seoul, South Korea", location3);
//
//                    List<Double> coord4 = new ArrayList<>();
//                    coord4.add(37.573806731717866);
//                    coord4.add(126.99016465032621);
//                    Map<String, List<Double>> location4 = new HashMap<>();
//                    location4.put("Location", coord4);
//                    Record address4 = new Record("28 Samil-daero 30-gil, Ikseon-dong, Jongno-gu, Seoul, South Korea", location4);
//
//                    List<Double> coord5 = new ArrayList<>();
//                    coord5.add(37.57793303286747);
//                    coord5.add(126.98647139635473);
//                    Map<String, List<Double>> location5 = new HashMap<>();
//                    location5.put("Location", coord5);
//                    Record address5 = new Record("8 Achasan-ro 9-gil, Seongsu-dong 2(i)-ga, Seongdong-gu, Seoul, South Korea", location5);
//
//                    List<Double> coord6 = new ArrayList<>();
//                    coord6.add(37.55130547441948);
//                    coord6.add(126.98820514003933);
//                    Map<String, List<Double>> location6 = new HashMap<>();
//                    location6.put("Location", coord6);
//                    Record address6 = new Record("105 Namsangongwon-gil, Yongsan-gu, Seoul, South Korea", location6);
//
//                    List<Double> coord7 = new ArrayList<>();
//                    coord7.add(37.56214305462524);
//                    coord7.add(126.92501691305641);
//                    Map<String, List<Double>> location7 = new HashMap<>();
//                    location7.put("Location", coord7);
//                    Record address7 = new Record("South Korea, Seoul, Mapo-gu, 연남동 390-71", location7);
//
//                    List<Double> coord8 = new ArrayList<>();
//                    coord8.add(37.29391);
//                    coord8.add(127.20256);
////        coord8.add(37.56314305462524);
////        coord8.add(126.93501691305641);
//                    Map<String, List<Double>> location8 = new HashMap<>();
//                    location8.put("Location", coord8);
//                    Record address8 = new Record("199 Everland-ro, Pogok-eup, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea", location8);
//
//                    List<Double> coord9 = new ArrayList<>();
//                    coord9.add(37.52403);
//                    coord9.add(127.02343);
//                    Map<String, List<Double>> location9 = new HashMap<>();
//                    location9.put("Location", coord9);
//                    Record address9 = new Record("68 Nonhyeon-ro 175-gil, Gangnam-gu, Seoul, South Korea", location9);
//
//                    List<Double> coord10 = new ArrayList<>();
//                    coord10.add(37.57962);
//                    coord10.add(126.97096);
//                    Map<String, List<Double>> location10 = new HashMap<>();
//                    location10.put("Location", coord10);
//                    Record address10 = new Record("4 Jahamun-ro 11-gil, Jongno-gu, Seoul, South Korea", location10);
//
//                    List<Double> coord11 = new ArrayList<>();
//                    coord11.add(37.52385);
//                    coord11.add(126.98047);
//                    Map<String, List<Double>> location11 = new HashMap<>();
//                    location11.put("Location", coord11);
//                    Record address11 = new Record("137 Seobinggo-ro, Yongsan-gu, 서울특별시 South Korea", location11);
//
//                    List<Double> coord12 = new ArrayList<>();
//                    coord12.add(37.571);
//                    coord12.add(126.97694);
//                    Map<String, List<Double>> location12 = new HashMap<>();
//                    location12.put("Location", coord12);
//                    Record address12 = new Record("172 Sejong-daero, Sejongno, Jongno-gu, Seoul, South Korea", location12);
//
//                    records.add(address1);
//                    records.add(address2);
//                    records.add(address3);
//                    records.add(address4);
//                    records.add(address5);
//                    records.add(address6);
//                    records.add(address7);
//                    records.add(address8);
//                    records.add(address9);
//                    records.add(address10);
//                    records.add(address11);
//                    records.add(address12);
//
//
//                    System.out.println("\n");
//                    System.out.println("\n");
//
//
//                    Map<Centroid, List<Record>> clusters = KMeans.fit(records, 4, new EuclideanDistance(), 10);
//                    int targetClusterSize = records.size() / 4; // Replace 4 with the desired number of clusters
//                    System.out.println("\n");
//                    System.out.println("\n");
//
//
//                    System.out.println(clusters);
//                    // Printing the cluster configuration
//                    clusters.forEach((key, value) -> {
//                        System.out.println("-------------------------- CLUSTER ----------------------------");
//                        // Sorting the coordinates to see the most significant tags first.
//                        System.out.println(key);
////                        System.out.println(value);
//                        String members = String.join(", ", value.stream().map(Record::getAddress).collect(toSet()));
//                        System.out.print("members: "+ members + "\n");
//                    });
//                    System.out.println("\n");
//                    System.out.println("\n");
//
//                    System.out.println("-------------------clusters overall information---------------: \n"+ clusters.keySet());
//                    // System.out.println("number of clusters: "+redistributedClusters.size());
//
//
//                    //ArrayList<Centroid> centroidkeys = new ArrayList<Centroid>();
//                    ArrayList<ArrayList<Record>> array_array_of_clusters = new ArrayList<ArrayList<Record>>();
//                    clusters.forEach((key, value) -> {
//                        System.out.println(" centroidKeys " + value + " size: "  + value.size());
//                        //centroidkeys.add(key);
//                        array_array_of_clusters.add((ArrayList<Record>) value );
//
//                    });
//
//                    ArrayList<ArrayList<PlacesSchedule>> return_array_of_clusers = new ArrayList<ArrayList<PlacesSchedule>>();
//
//                    System.out.println("array_array_of_clusters" + array_array_of_clusters.size() );
//                    for (int i = 0; i < array_array_of_clusters.size(); i++) {
//                        System.out.println(" ========== array array of clusters each array " + array_array_of_clusters.get(i).toString());
//
//
//                        // ==============================
//
//                        ArrayList<Record> cluster1 = array_array_of_clusters.get(i);
//
//                        for (int a = 0; a < cluster1.size(); a++) {
//                            System.out.println("inside cluster1 arraylist " + cluster1.get(a).address + cluster1.size());
//                        }
//
//
//                        GeoApiContext context = new GeoApiContext.Builder()
//                                .apiKey(BuildConfig.WEB_API_KEY)
//                                .build();
//                        //ArrayList<String> locations = new ArrayList<String>();
//
//
//                        LocalTime startTime = LocalTime.parse("09:00");
//                        LocalTime endTime = LocalTime.parse("22:00");
//                        LocalTime lunchTimestart = LocalTime.parse("11:00");
//                        LocalTime lunchTimeend = LocalTime.parse("14:00");
//                        LocalTime dinnerTimestart = LocalTime.parse("17:30");
//                        LocalTime dinnerTimeend = LocalTime.parse("20:00");
//
//                        String origin = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
//                        String destination = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
//
//                        ArrayList<PlacesSchedule> locations_data = new ArrayList<PlacesSchedule>();
//
//
//                        //add all places and get address, latitude and longitude
//                        for (int d = 0; d < cluster1.size(); d++) {
//                            System.out.println("check if places can be added " + cluster1.get(d).address);
//                            PlacesSchedule new_place = new PlacesSchedule(cluster1.get(d).address);
//                            System.out.println(new_place.getAddress() + " " + new_place.getLongitude() + " lat " + new_place.getLatitude());
//                            new_place.calc_Dist_two_places(origin);
//                            locations_data.add(new_place);
//                            System.out.println(" =========== check place: " + new_place.place_Id + " " +
//                                    new_place.address + " " + new_place.duration_From_point.toString() + " " +
//                                    new_place.longitude + " " + new_place.distance_From_point.toString());
//
//                        }
//
//
//                        ArrayList<PlacesSchedule> iternarySchedule = new ArrayList<PlacesSchedule>();
//                        iternarySchedule.add(new PlacesSchedule(origin));
//                        LocalTime currentTime = startTime;
//                        int locations_data_pointer = 0;
//                        int iternarySchedule_pointer = 0;
//
//
//                        while (currentTime.isBefore(endTime)) {
//
//                            if (locations_data.size() > 0) {
//
//                                System.out.println("inside locations current time " + currentTime + " locations data size "
//                                        + locations_data.size());
//
//                                if (iternarySchedule_pointer < iternarySchedule.size()) {
//                                    for (int x = 0; x < locations_data.size(); x++) {
//                                        locations_data.get(x).calc_Dist_two_places(iternarySchedule.get(iternarySchedule_pointer).address);
//                                    }
//                                }
//                                locations_data.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                        return Integer.compare(o1.getDistance_From_point(), o2.getDistance_From_point());
//                                    }
//                                });
//                                iternarySchedule.add(locations_data.get(locations_data_pointer));
//                                Duration duration_to_add = Duration.ofSeconds(locations_data.get(locations_data_pointer).duration_From_point);
//                                currentTime = currentTime.plus(duration_to_add);
//                                //TODO: add how long people typically spend here and check opening hours
//
//                                System.out.println("location added " + locations_data.get(locations_data_pointer).address + " " + duration_to_add + " " + currentTime);
//                                locations_data.remove(locations_data.get(locations_data_pointer));
//
//                                System.out.println("remove locations data " + locations_data.size());
//                                iternarySchedule_pointer += 1;
//                            } else {
//                                System.out.println("Break ");
//                                break;
//                            }
//
//
//                            for (int z = 0; z < iternarySchedule.size(); z++) {
//                                System.out.println("iternary schedule: " + iternarySchedule.get(z).address);
//                            }
//
//
//                            context.shutdown();
//                            return_array_of_clusers.add(iternarySchedule);
//
//
//                        }
//
//                    }






















                   

//                    //INTEGRATE K MEANS + GREEDY NEAREST DISTANCE ALGO
//                    //RUN kmeans clustering
//
//                    List<Record> records = new ArrayList<Record>();
//                    //Start adding Records
//                    List<Double> coord1 = new ArrayList<>();
//                    coord1.add(37.56819053958451);
//                    coord1.add(127.00847759788303);
//                    //location1 is a point with supposedly multiple features
//                    Map<String, List<Double>> location1 = new HashMap<>();
//                    //add distance feature
//                    location1.put("Location", coord1);
//                    //assign location1 as location of Record object address1
//                    Record address1 = new Record("263 Jangchungdan-ro, Jung-gu, Seoul, South Korea", location1);
////        System.out.println(address1
////                .getFeatures());
//
//                    List<Double> coord2 = new ArrayList<>();
//                    coord2.add(37.57020985495065);
//                    coord2.add(126.99959286887606);
//                    Map<String, List<Double>> location2 = new HashMap<>();
//                    location2.put("Location", coord2);
//                    Record address2 = new Record("88 Changgyeonggung-ro, Jongno-gu, Seoul, South Korea", location2);
//
//                    List<Double> coord3 = new ArrayList<>();
//                    coord3.add(37.57982948171247);
//                    coord3.add(126.97709462440277);
//                    Map<String, List<Double>> location3 = new HashMap<>();
//                    location3.put("Location", coord3);
//                    Record address3 = new Record("161 Sajik-ro, Jongno-gu, Seoul, South Korea", location3);
//
//                    List<Double> coord4 = new ArrayList<>();
//                    coord4.add(37.573806731717866);
//                    coord4.add(126.99016465032621);
//                    Map<String, List<Double>> location4 = new HashMap<>();
//                    location4.put("Location", coord4);
//                    Record address4 = new Record("28 Samil-daero 30-gil, Ikseon-dong, Jongno-gu, Seoul, South Korea", location4);
//
//                    List<Double> coord5 = new ArrayList<>();
//                    coord5.add(37.57793303286747);
//                    coord5.add(126.98647139635473);
//                    Map<String, List<Double>> location5 = new HashMap<>();
//                    location5.put("Location", coord5);
//                    Record address5 = new Record("8 Achasan-ro 9-gil, Seongsu-dong 2(i)-ga, Seongdong-gu, Seoul, South Korea", location5);
//
//                    List<Double> coord6 = new ArrayList<>();
//                    coord6.add(37.55130547441948);
//                    coord6.add(126.98820514003933);
//                    Map<String, List<Double>> location6 = new HashMap<>();
//                    location6.put("Location", coord6);
//                    Record address6 = new Record("105 Namsangongwon-gil, Yongsan-gu, Seoul, South Korea", location6);
//
//                    List<Double> coord7 = new ArrayList<>();
//                    coord7.add(37.56214305462524);
//                    coord7.add(126.92501691305641);
//                    Map<String, List<Double>> location7 = new HashMap<>();
//                    location7.put("Location", coord7);
//                    Record address7 = new Record("South Korea, Seoul, Mapo-gu, 연남동 390-71", location7);
//
//                    List<Double> coord8 = new ArrayList<>();
//                    coord8.add(37.29391);
//                    coord8.add(127.20256);
////        coord8.add(37.56314305462524);
////        coord8.add(126.93501691305641);
//                    Map<String, List<Double>> location8 = new HashMap<>();
//                    location8.put("Location", coord8);
//                    Record address8 = new Record("199 Everland-ro, Pogok-eup, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea", location8);
//
//                    List<Double> coord9 = new ArrayList<>();
//                    coord9.add(37.52403);
//                    coord9.add(127.02343);
//                    Map<String, List<Double>> location9 = new HashMap<>();
//                    location9.put("Location", coord9);
//                    Record address9 = new Record("68 Nonhyeon-ro 175-gil, Gangnam-gu, Seoul, South Korea", location9);
//
//                    List<Double> coord10 = new ArrayList<>();
//                    coord10.add(37.57962);
//                    coord10.add(126.97096);
//                    Map<String, List<Double>> location10 = new HashMap<>();
//                    location10.put("Location", coord10);
//                    Record address10 = new Record("4 Jahamun-ro 11-gil, Jongno-gu, Seoul, South Korea", location10);
//
//                    List<Double> coord11 = new ArrayList<>();
//                    coord11.add(37.52385);
//                    coord11.add(126.98047);
//                    Map<String, List<Double>> location11 = new HashMap<>();
//                    location11.put("Location", coord11);
//                    Record address11 = new Record("137 Seobinggo-ro, Yongsan-gu, 서울특별시 South Korea", location11);
//
//                    List<Double> coord12 = new ArrayList<>();
//                    coord12.add(37.571);
//                    coord12.add(126.97694);
//                    Map<String, List<Double>> location12 = new HashMap<>();
//                    location12.put("Location", coord12);
//                    Record address12 = new Record("172 Sejong-daero, Sejongno, Jongno-gu, Seoul, South Korea", location12);
//
//                    records.add(address1);
//                    records.add(address2);
//                    records.add(address3);
//                    records.add(address4);
//                    records.add(address5);
//                    records.add(address6);
//                    records.add(address7);
//                    records.add(address8);
//                    records.add(address9);
//                    records.add(address10);
//                    records.add(address11);
//                    records.add(address12);
//
//
//                    System.out.println("\n");
//                    System.out.println("\n");
//
//
//                    Map<Centroid, List<Record>> clusters = KMeans.fit(records, 4, new EuclideanDistance(), 10);
//                    int targetClusterSize = records.size() / 4; // Replace 4 with the desired number of clusters
//                    System.out.println("\n");
//                    System.out.println("\n");
//
//
//                    System.out.println(clusters);
//                    // Printing the cluster configuration
//                    clusters.forEach((key, value) -> {
//                        System.out.println("-------------------------- CLUSTER ----------------------------");
//                        // Sorting the coordinates to see the most significant tags first.
//                        System.out.println(key);
////                        System.out.println(value);
//                        String members = String.join(", ", value.stream().map(Record::getAddress).collect(toSet()));
//                        System.out.print("members: "+ members + "\n");
//                    });
//                    System.out.println("\n");
//                    System.out.println("\n");
//
//                    System.out.println("-------------------clusters overall information---------------: \n"+ clusters.keySet());
//                    // System.out.println("number of clusters: "+redistributedClusters.size());
//
//
//                    //ArrayList<Centroid> centroidkeys = new ArrayList<Centroid>();
//                    ArrayList<ArrayList<Record>> array_array_of_clusters = new ArrayList<ArrayList<Record>>();
//                    clusters.forEach((key, value) -> {
//                        System.out.println(" centroidKeys " + value + " size: "  + value.size());
//                        //centroidkeys.add(key);
//                        array_array_of_clusters.add((ArrayList<Record>) value );
//
//                    });
//
//                    ArrayList<ArrayList<PlacesSchedule>> return_array_of_clusers = new ArrayList<ArrayList<PlacesSchedule>>();
//
//                    System.out.println("array_array_of_clusters" + array_array_of_clusters.size() );
//                    for (int i = 0; i < array_array_of_clusters.size(); i++) {
//                        System.out.println(" ========== array array of clusters each array " + array_array_of_clusters.get(i).toString());
//
//
//                        // ==============================
//
//                        ArrayList<Record> cluster1 = array_array_of_clusters.get(i);
//
//                        for (int a = 0; a < cluster1.size(); a++) {
//                            System.out.println("inside cluster1 arraylist " + cluster1.get(a).address + cluster1.size());
//                        }
//
//
//                        GeoApiContext context = new GeoApiContext.Builder()
//                                .apiKey(BuildConfig.WEB_API_KEY)
//                                .build();
//                        //ArrayList<String> locations = new ArrayList<String>();
//
//
//                        LocalTime startTime = LocalTime.parse("09:00");
//                        LocalTime endTime = LocalTime.parse("22:00");
//                        LocalTime lunchTimestart = LocalTime.parse("11:00");
//                        LocalTime lunchTimeend = LocalTime.parse("14:00");
//                        LocalTime dinnerTimestart = LocalTime.parse("17:30");
//                        LocalTime dinnerTimeend = LocalTime.parse("20:00");
//
//                        String origin = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
//                        String destination = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
//
//                        ArrayList<PlacesSchedule> locations_data = new ArrayList<PlacesSchedule>();
//
//
//                        //add all places and get address, latitude and longitude
//                        for (int d = 0; d < cluster1.size(); d++) {
//                            System.out.println("check if places can be added " + cluster1.get(d).address);
//                            PlacesSchedule new_place = new PlacesSchedule(cluster1.get(d).address);
//                            System.out.println(new_place.getAddress() + " " + new_place.getLongitude() + " lat " + new_place.getLatitude());
//                            new_place.calc_Dist_two_places(origin);
//                            locations_data.add(new_place);
//                            System.out.println(" =========== check place: " + new_place.place_Id + " " +
//                                    new_place.address + " " + new_place.duration_From_point.toString() + " " +
//                                    new_place.longitude + " " + new_place.distance_From_point.toString());
//
//                        }
//
//
//                        ArrayList<PlacesSchedule> iternarySchedule = new ArrayList<PlacesSchedule>();
//                        iternarySchedule.add(new PlacesSchedule(origin));
//                        LocalTime currentTime = startTime;
//                        int locations_data_pointer = 0;
//                        int iternarySchedule_pointer = 0;
//
//
//                        while (currentTime.isBefore(endTime)) {
//
//                            if (locations_data.size() > 0) {
//
//                                System.out.println("inside locations current time " + currentTime + " locations data size "
//                                        + locations_data.size());
//
//                                if (iternarySchedule_pointer < iternarySchedule.size()) {
//                                    for (int x = 0; x < locations_data.size(); x++) {
//                                        locations_data.get(x).calc_Dist_two_places(iternarySchedule.get(iternarySchedule_pointer).address);
//                                    }
//                                }
//                                locations_data.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                        return Integer.compare(o1.getDistance_From_point(), o2.getDistance_From_point());
//                                    }
//                                });
//                                iternarySchedule.add(locations_data.get(locations_data_pointer));
//                                Duration duration_to_add = Duration.ofSeconds(locations_data.get(locations_data_pointer).duration_From_point);
//                                currentTime = currentTime.plus(duration_to_add);
//                                //TODO: add how long people typically spend here and check opening hours
//
//                                System.out.println("location added " + locations_data.get(locations_data_pointer).address + " " + duration_to_add + " " + currentTime);
//                                locations_data.remove(locations_data.get(locations_data_pointer));
//
//                                System.out.println("remove locations data " + locations_data.size());
//                                iternarySchedule_pointer += 1;
//                            } else {
//                                System.out.println("Break ");
//                                break;
//                            }
//
//
//                            for (int z = 0; z < iternarySchedule.size(); z++) {
//                                System.out.println("iternary schedule: " + iternarySchedule.get(z).address);
//                            }
//
//
//                            context.shutdown();
//                            return_array_of_clusers.add(iternarySchedule);
//
//
//                        }
//
//                    }
//
//                    for (int i = 0; i < return_array_of_clusers.size(); i++) {
//                        System.out.println(return_array_of_clusers.get(i).toString());
//                        //for (int j = 0; j < return_array_of_clusers.get(i).size(); j++) {
//                            //System.out.println(return_array_of_clusers.get(i).get(j).address);
//                        //}
//
//                    }










                    //INTEGRATE K MEANS + GREEDY NEAREST DISTANCE ALGO
                    //RUN kmeans clustering

                    List<Record> records = new ArrayList<Record>();
                    //Start adding Records
                    List<Double> coord1 = new ArrayList<>();
                    coord1.add(37.56819053958451);
                    coord1.add(127.00847759788303);
                    //location1 is a point with supposedly multiple features
                    Map<String, List<Double>> location1 = new HashMap<>();
                    //add distance feature
                    location1.put("Location", coord1);
                    //assign location1 as location of Record object address1
                    Record address1 = new Record("263 Jangchungdan-ro, Jung-gu, Seoul, South Korea", location1);
//        System.out.println(address1
//                .getFeatures());

                    List<Double> coord2 = new ArrayList<>();
                    coord2.add(37.57020985495065);
                    coord2.add(126.99959286887606);
                    Map<String, List<Double>> location2 = new HashMap<>();
                    location2.put("Location", coord2);
                    Record address2 = new Record("88 Changgyeonggung-ro, Jongno-gu, Seoul, South Korea", location2);

                    List<Double> coord3 = new ArrayList<>();
                    coord3.add(37.57982948171247);
                    coord3.add(126.97709462440277);
                    Map<String, List<Double>> location3 = new HashMap<>();
                    location3.put("Location", coord3);
                    Record address3 = new Record("161 Sajik-ro, Jongno-gu, Seoul, South Korea", location3);

                    List<Double> coord4 = new ArrayList<>();
                    coord4.add(37.573806731717866);
                    coord4.add(126.99016465032621);
                    Map<String, List<Double>> location4 = new HashMap<>();
                    location4.put("Location", coord4);
                    Record address4 = new Record("28 Samil-daero 30-gil, Ikseon-dong, Jongno-gu, Seoul, South Korea", location4);

                    List<Double> coord5 = new ArrayList<>();
                    coord5.add(37.57793303286747);
                    coord5.add(126.98647139635473);
                    Map<String, List<Double>> location5 = new HashMap<>();
                    location5.put("Location", coord5);
                    Record address5 = new Record("8 Achasan-ro 9-gil, Seongsu-dong 2(i)-ga, Seongdong-gu, Seoul, South Korea", location5);

                    List<Double> coord6 = new ArrayList<>();
                    coord6.add(37.55130547441948);
                    coord6.add(126.98820514003933);
                    Map<String, List<Double>> location6 = new HashMap<>();
                    location6.put("Location", coord6);
                    Record address6 = new Record("105 Namsangongwon-gil, Yongsan-gu, Seoul, South Korea", location6);

                    List<Double> coord7 = new ArrayList<>();
                    coord7.add(37.56214305462524);
                    coord7.add(126.92501691305641);
                    Map<String, List<Double>> location7 = new HashMap<>();
                    location7.put("Location", coord7);
                    Record address7 = new Record("South Korea, Seoul, Mapo-gu, 연남동 390-71", location7);

                    List<Double> coord8 = new ArrayList<>();
                    coord8.add(37.29391);
                    coord8.add(127.20256);
//        coord8.add(37.56314305462524);
//        coord8.add(126.93501691305641);
                    Map<String, List<Double>> location8 = new HashMap<>();
                    location8.put("Location", coord8);
                    Record address8 = new Record("199 Everland-ro, Pogok-eup, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea", location8);

                    List<Double> coord9 = new ArrayList<>();
                    coord9.add(37.52403);
                    coord9.add(127.02343);
                    Map<String, List<Double>> location9 = new HashMap<>();
                    location9.put("Location", coord9);
                    Record address9 = new Record("68 Nonhyeon-ro 175-gil, Gangnam-gu, Seoul, South Korea", location9);

                    List<Double> coord10 = new ArrayList<>();
                    coord10.add(37.57962);
                    coord10.add(126.97096);
                    Map<String, List<Double>> location10 = new HashMap<>();
                    location10.put("Location", coord10);
                    Record address10 = new Record("18 Jahamun-ro 15-gil, Jongno-gu, Seoul, South Korea", location10);

                    List<Double> coord11 = new ArrayList<>();
                    coord11.add(37.52385);
                    coord11.add(126.98047);
                    Map<String, List<Double>> location11 = new HashMap<>();
                    location11.put("Location", coord11);
                    Record address11 = new Record("137 Seobinggo-ro, Yongsan-gu, 서울특별시 South Korea", location11);

                    List<Double> coord12 = new ArrayList<>();
                    coord12.add(37.571);
                    coord12.add(126.97694);
                    Map<String, List<Double>> location12 = new HashMap<>();
                    location12.put("Location", coord12);
                    Record address12 = new Record("172 Sejong-daero, Sejongno, Jongno-gu, Seoul, South Korea", location12);

                    records.add(address1);
                    records.add(address2);
                    records.add(address3);
                    records.add(address4);
                    records.add(address5);
                    records.add(address6);
                    records.add(address7);
                    records.add(address8);
                    records.add(address9);
                    records.add(address10);
                    records.add(address11);
                    records.add(address12);


                    System.out.println("\n");
                    System.out.println("\n");


                    Map<Centroid, List<Record>> clusters = KMeans.fit(records, 4, new EuclideanDistance(), 10);
                    int targetClusterSize = records.size() / 4; // Replace 4 with the desired number of clusters
                    System.out.println("\n");
                    System.out.println("\n");


                    System.out.println(clusters);
                    // Printing the cluster configuration
                    clusters.forEach((key, value) -> {
                        System.out.println("-------------------------- CLUSTER ----------------------------");
                        // Sorting the coordinates to see the most significant tags first.
                        System.out.println(key);
//                        System.out.println(value);
                        String members = String.join(", ", value.stream().map(Record::getAddress).collect(toSet()));
                        System.out.print("members: "+ members + "\n");
                    });
                    System.out.println("\n");
                    System.out.println("\n");

                    System.out.println("-------------------clusters overall information---------------: \n"+ clusters.keySet());
                    // System.out.println("number of clusters: "+redistributedClusters.size());


//                    //=========algo used in the app, followed by run k-nearest neighbours for scheduling
//                    //ArrayList<Centroid> centroidkeys = new ArrayList<Centroid>();
//                    ArrayList<ArrayList<Record>> array_array_of_clusters = new ArrayList<ArrayList<Record>>();
//                    clusters.forEach((key, value) -> {
//                        System.out.println(" centroidKeys " + value + " size: "  + value.size());
//                        //centroidkeys.add(key);
//                        array_array_of_clusters.add((ArrayList<Record>) value );
//
//                    });
//
//                    ArrayList<ArrayList<Place>> return_array_of_clusers = new ArrayList<ArrayList<Place>>();
//                    ArrayList<Place> saved_Places = new ArrayList<Place>();
//
//                    System.out.println("array_array_of_clusters" + array_array_of_clusters.size() );
//                    for (int i = 0; i < array_array_of_clusters.size(); i++) {
//                        System.out.println(" ========== array array of clusters each array " + array_array_of_clusters.get(i).toString());
//
//
//                        // ==============================
//
//                        ArrayList<Record> cluster1 = array_array_of_clusters.get(i);
//
//                        for (int a = 0; a < cluster1.size(); a++) {
//                            System.out.println("inside cluster1 arraylist " + cluster1.get(a).address + cluster1.size());
//                        }
//
//
//                        GeoApiContext context = new GeoApiContext.Builder()
//                                .apiKey(BuildConfig.WEB_API_KEY)
//                                .build();
//                        //ArrayList<String> locations = new ArrayList<String>();
//
//
//                        LocalTime startTime = LocalTime.parse("09:00");
//                        LocalTime endTime = LocalTime.parse("22:00");
//                        LocalTime lunchTimestart = LocalTime.parse("11:00");
//                        LocalTime lunchTimeend = LocalTime.parse("14:00");
//                        LocalTime dinnerTimestart = LocalTime.parse("17:30");
//                        LocalTime dinnerTimeend = LocalTime.parse("20:00");
//
//                        String origin = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
//                        String destination = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
//
//                        ArrayList<Place> locations_data = new ArrayList<Place>();
//
//
//                        //add all places and get address, latitude and longitude
//                        for (int d = 0; d < cluster1.size(); d++) {
//                            System.out.println("check if places can be added " + cluster1.get(d).address);
//                            Place new_place = new Place(cluster1.get(d).address);
//                            System.out.println(new_place.getAddress() + " " + new_place.getLongitude() + " lat " + new_place.getLatitude());
//                            new_place.calc_Dist_two_places(origin);
//                            locations_data.add(new_place);
//                            if (!saved_Places.contains(new_place)) {
//                                saved_Places.add(new_place);
//                                System.out.println(new_place.getAddress() + " has been added to the ArrayList.");
//                            } else {
//                                System.out.println(new_place.getAddress() + " is already present in the ArrayList.");
//                            }
//                            System.out.println(" =========== check place: " + new_place.getPlaceId() + " " +
//                                    new_place.getAddress() + " " + new_place.getDurationFromPoint() + " " +
//                                    new_place.getLongitude() + " " + new_place.getDistanceFromPoint() );
//
//                        }
//
//
//                        ArrayList<Place> iternarySchedule = new ArrayList<Place>();
//                        iternarySchedule.add( new Place(origin) );
//                        LocalTime currentTime = startTime;
//                        int locations_data_pointer = 0;
//                        int iternarySchedule_pointer = 0;
//
//
//                        while (currentTime.isBefore(endTime)) {
//
//                            if (locations_data.size() > 0) {
//
//                                System.out.println("inside locations current time " + currentTime + " locations data size "
//                                        + locations_data.size());
//
//                                if (iternarySchedule_pointer < iternarySchedule.size()) {
//                                    for (int x = 0; x < locations_data.size(); x++) {
//                                        locations_data.get(x).calc_Dist_two_places(iternarySchedule.get(iternarySchedule_pointer).getAddress());
//                                    }
//                                }
//                                locations_data.sort(new Comparator<Place>() {
//                                    @Override
//                                    public int compare(Place o1, Place o2) {
//                                        return Integer.compare(o1.getDistanceFromPoint(), o2.getDistanceFromPoint());
//                                    }
//                                });
//                                iternarySchedule.add(locations_data.get(locations_data_pointer));
//                                Duration duration_to_add = Duration.ofSeconds(locations_data.get(locations_data_pointer).getDurationFromPoint());
//                                currentTime = currentTime.plus(duration_to_add);
//                                //TODO: add how long people typically spend here and check opening hours
//
//                                System.out.println("location added " + locations_data.get(locations_data_pointer).getAddress() + " " + duration_to_add + " " + currentTime);
//                                locations_data.remove(locations_data.get(locations_data_pointer));
//
//                                System.out.println("remove locations data " + locations_data.size());
//                                iternarySchedule_pointer += 1;
//                            } else {
//                                System.out.println("Break ");
//                                break;
//                            }
//
//
//                            for (int z = 0; z < iternarySchedule.size(); z++) {
//                                System.out.println("iternary schedule: " + iternarySchedule.get(z).getAddress());
//                            }
//
//
//                            context.shutdown();
//                            //return_array_of_clusers.add(iternarySchedule);
//
//
//                        }
//
//                        return_array_of_clusers.add(iternarySchedule);
//
//                    }
//
//                    for (int i = 0; i < return_array_of_clusers.size(); i++) {
//                        System.out.println(return_array_of_clusers.get(i).toString() + "iteration: " + i);
//                    }
//
//                    System.out.println("saved_places array " + saved_Places.size() + " everything " + saved_Places.toString());
//
//
//                    Trip trip = FileManager.getTrip(IternaryActivity.this, "final.json");
//                    trip.days = return_array_of_clusers;
//                    trip.savedPlaces = saved_Places;
//                    FileManager.saveTrip(IternaryActivity.this, "final.json", trip);













                    //try algo for scheduling based on swap basis
                    // 1. position earliest closing time < lunch time
                    // 2. position latest closing time > dinner time
                    // 3. choose lunch and dinner place
                    // 4. come up with a schedule

                    //this is an arraylist of arraylist of records
                    ArrayList<ArrayList<Record>> array_array_of_clusters = new ArrayList<ArrayList<Record>>();


                    //clusters is a hash map with a key: cluster and value: arraylist of records
                    clusters.forEach((key, value) -> {
                        System.out.println(" centroidKeys " + value + " size: "  + value.size());
                        array_array_of_clusters.add((ArrayList<Record>) value );

                    });

                    //we need to convert an arraylist of records into place objects. same format
                    ArrayList<ArrayList<Place>> return_array_of_clusers = new ArrayList<ArrayList<Place>>();

                    //saved places is for savedplaces page.
                    ArrayList<Place> saved_Places = new ArrayList<Place>();

                    System.out.println("array_array_of_clusters" + array_array_of_clusters.size() );



                    //loop through list of arraylist of clusters
                    for (int i = 0; i < array_array_of_clusters.size(); i++) {
                        System.out.println("iteration " + i + " array of array of clusters.size "+ array_array_of_clusters.size() + " ========== array array of clusters each array per day " + array_array_of_clusters.get(i).toString());



                        ArrayList<Record> cluster1 = array_array_of_clusters.get(i);

                        for (int a = 0; a < cluster1.size(); a++) {
                            System.out.println("XXXXXXXXXXXXXXX inside cluster1 arraylist " + cluster1.get(a).address + cluster1.size());
                        }


                        GeoApiContext context = new GeoApiContext.Builder()
                                .apiKey(BuildConfig.WEB_API_KEY)
                                .build();
                        //ArrayList<String> locations = new ArrayList<String>();


                        LocalTime lunchTimestart = LocalTime.parse("11:00");
                        LocalTime lunchTimeend = LocalTime.parse("14:00");
                        LocalTime dinnerTimestart = LocalTime.parse("17:30");
                        LocalTime dinnerTimeend = LocalTime.parse("20:00");

                        String origin = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
                        String destination = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";

                        ArrayList<Place> locations_data = new ArrayList<Place>();
                        ArrayList<Place> eating_locations_data = new ArrayList<Place>();

                        //boolean stop_running = false;


                        //add all places and get address, latitude and longitude
                        for (int d = 0; d < cluster1.size(); d++) {
                            System.out.println("check if places can be added " + cluster1.get(d).address);
                            Place new_place = new Place(cluster1.get(d).address);
                            System.out.println(new_place.getAddress() + " " + new_place.getLongitude() + " lat " + new_place.getLatitude());
                            //new_place.calc_Dist_two_places(origin);

                            if (cluster1.get(d).getAddress() == "390-71 연남동 Mapo-gu, Seoul, South Korea") {
                                eating_locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("12:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("20:00"));
                                new_place.setEating_place_test(true);
                                new_place.setTime_spent(Duration.ofSeconds(2700));
                                new_place.setName("Oh,range");
                            } else if (cluster1.get(d).getAddress() == "8 Achasan-ro 9-gil, Seongdong-gu, Seoul, South Korea") {
                                eating_locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("08:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("22:00"));
                                new_place.setEating_place_test(true);
                                new_place.setTime_spent(Duration.ofSeconds(2700));
                                new_place.setName("Cafe Onion Seongsu");
                            }  else if (cluster1.get(d).getAddress() == "88 Changgyeonggung-ro, Jongno-gu, Seoul, South Korea") {
                                    eating_locations_data.add(new_place);
                                    new_place.setOpening_hours_test(LocalTime.parse("09:00"));
                                    new_place.setClosing_hours_test(LocalTime.parse("23:00"));
                                new_place.setEating_place_test(true);
                                    new_place.setTime_spent(Duration.ofSeconds(2700));
                                    new_place.setName("Gwangjang Market");
                            } else if (cluster1.get(d).getAddress() == "199 Everland-ro, Pogok-eup, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea") {
                                eating_locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("09:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("23:00"));
                                new_place.setEating_place_test(true);
                                new_place.setTime_spent(Duration.ofSeconds(2700));
                                new_place.setName("Everland");
                            } else if (cluster1.get(d).getAddress() == "Ikseon-dong, Jongno-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("10:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("21:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("Gwangjang Market");
                            } else if (cluster1.get(d).getAddress() == "105 Namsangongwon-gil, Yongsan-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("10:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("23:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("N Seoul Tower");
                            } else if (cluster1.get(d).getAddress() == "263 Jangchungdan-ro, Jung-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("10:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("12:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("Nail Mall");
                            } else if (cluster1.get(d).getAddress() == "161 Sajik-ro, Jongno-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("09:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("18:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("Gyeongbokgung Palace");
                            } else if (cluster1.get(d).getAddress() == "172 Sejong-daero, Sejongno, Jongno-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("09:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("18:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("Statue of Admiral Yi Sun Shin");
                            } else if (cluster1.get(d).getAddress() == "137 Seobinggo-ro, Yongsan-gu, 서울특별시 South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("10:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("18:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("National Museum of Korea");
                            } else if (cluster1.get(d).getAddress() == "18 Jahamun-ro 15-gil, Jongno-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("11:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("21:30"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("Tongin Traditional Market");
                            } else if (cluster1.get(d).getAddress() == "68 Nonhyeon-ro 175-gil, Gangnam-gu, Seoul, South Korea") {
                                locations_data.add(new_place);
                                new_place.setOpening_hours_test(LocalTime.parse("14:00"));
                                new_place.setClosing_hours_test(LocalTime.parse("22:00"));
                                new_place.setEating_place_test(false);
                                new_place.setTime_spent(Duration.ofSeconds(3600));
                                new_place.setName("Hanchu");
                            }

                            System.out.println("set properties inside check if places can be added" + new_place.getName() + new_place.getOpening_hours_test() + new_place.getClosing_hours_test() + new_place.getTime_spent() + new_place.isEating_place_test());

//                            for (int v = 0; v < array_array_of_clusters.size(); v++) {
//                                for (int j = 0; j < array_array_of_clusters.get(v).size(); j++) {
//                                    System.out.println("check if got duplicates break ");
//                                    break;
//                                }
//                            }

                            if (!saved_Places.contains(new_place)) {
                                saved_Places.add(new_place);
                                System.out.println(new_place.getAddress() + " has been added to the ArrayList.");
                            } else {
                                System.out.println(new_place.getAddress() + " is already present in the ArrayList.");
                            }
                            System.out.println(" =========== check place: " + new_place.getPlaceId() + " " +
                                    new_place.getAddress() + " " + new_place.getDurationFromPoint() + " " +
                                    new_place.getLongitude() + " " + new_place.getDistanceFromPoint() );

                        }


                        ArrayList<Place> iternarySchedule = new ArrayList<Place>();
                        iternarySchedule.add( new Place(origin) );
                        int locations_data_pointer = 0;
                        int eating_locations_data_pointer = 0;
                        int iternarySchedule_pointer = 0;


                        //set all opening and closing hours
                        //1. find earliest opening place
                        //2. find latest closing place and nearest eating place that is open

                        //assume one place in the morning then lunch after
                        //assume one place at night user wanna go after

                        //find the earliest opening place put it inside
                        //rmb to remove from locations






                        //greedy algorithm
                        ArrayList<Place> eligible_Places = new ArrayList<Place>();
                        LocalTime earliest_opening_time = LocalTime.parse("23:59");
                        LocalTime earliest_closing_time = LocalTime.parse("23:59");
                        Place first_place_to_visit = null;
                        Place earliest_closing_place = null;
                        LocalTime latest_closing_time = LocalTime.parse("05:00");

                        for (int k = 0; k < eating_locations_data.size(); k++) {
                            if (eating_locations_data.get(k).getOpening_hours_test().isBefore(earliest_opening_time)) {
                                earliest_opening_time = eating_locations_data.get(k).getOpening_hours_test();
                                first_place_to_visit = eating_locations_data.get(k);
                            }

                            if (eating_locations_data.get(k).getClosing_hours_test().isAfter(latest_closing_time)) {
                                latest_closing_time = eating_locations_data.get(k).getClosing_hours_test();
                                earliest_closing_place = locations_data.get(k);
                            }

                            if (eating_locations_data.get(k).getClosing_hours_test().isBefore(earliest_closing_time)) {
                                earliest_closing_time = eating_locations_data.get(k).getClosing_hours_test();
                            }
                        }


                        for (int z = 0; z < locations_data.size(); z++) {
                            if (locations_data.get(z).getOpening_hours_test().isBefore(earliest_opening_time)) {
                                earliest_opening_time = locations_data.get(z).getOpening_hours_test();
                                first_place_to_visit = locations_data.get(z);
                            }

                            if (locations_data.get(z).getClosing_hours_test().isAfter(latest_closing_time)) {
                                latest_closing_time = locations_data.get(z).getClosing_hours_test();
                                earliest_closing_place = locations_data.get(z);
                            }

                            if (locations_data.get(z).getClosing_hours_test().isBefore(earliest_closing_time)) {
                                earliest_closing_time = locations_data.get(z).getClosing_hours_test();
                            }
                        }


                        LocalTime currentTime = earliest_opening_time.minus(Duration.ofSeconds(2700));


                        int lunch_count = 0;
                        int dinner_count = 0;

                        int sametime = 0;

                        int iterations = 0;
                        //boolean stop_running = true;

                        System.out.println("latest closing time before while loop " + latest_closing_time);


                        while (currentTime.isBefore(latest_closing_time)) {





                            System.out.println("inside locations current time " + currentTime + " locations data size "
                                    + locations_data.size() + " eating locations data size " + eating_locations_data.size()
                            + " lunch count " + lunch_count);



//                            if (iternarySchedule_pointer < iternarySchedule.size()) {
//                                System.out.println("iternary schedule pointer " +iternarySchedule_pointer + " iternary schedule size " + iternarySchedule.size());
//                                for (int x = 0; x < locations_data.size(); x++) {
//                                    locations_data.get(x).calc_Dist_two_places(iternarySchedule.get(iternarySchedule_pointer).getAddress());
//                                }
//                            }








                            //greedy algo find eating place that is open within next 45 mins + 1 hour
                            if ( currentTime.plus( Duration.ofSeconds(2700)).isAfter(LocalTime.parse("11:00")) && (lunch_count ==0) && (eating_locations_data.size() > 0) ){
                                //prioritise lunch places
                                //first find eligible eating places.

                                System.out.println("look for lunch places " + lunch_count);
                                for (int h = 0; h < eating_locations_data.size(); h++) {

                                    //find eligible places and check u take 45 mins to travel there and 1 hour to spend there
                                    if ( eating_locations_data.get(h).getOpening_hours_test().isBefore( currentTime.plus(Duration.ofSeconds(2701)) ) && eating_locations_data.get(h).getClosing_hours_test().isAfter( currentTime.plus( Duration.ofSeconds(6300) ) ) ) {
                                        eligible_Places.add( eating_locations_data.get(h) );
                                        eating_locations_data.get(h).setDistanceFromPoint( iternarySchedule.get(iternarySchedule_pointer).getAddress() );

                                    }


                                }

                                //since now all eating places are open and can spend 1 hour there, we take closest distance.
                                eligible_Places.sort(new Comparator<Place>() {
                                    @Override
                                    public int compare(Place o1, Place o2) {
                                        return Integer.compare(o1.getDistanceFromPoint(), o2.getDistanceFromPoint());
                                    }
                                });

                                if (eligible_Places.size() > 0) {
                                    iternarySchedule.add(eligible_Places.get(0));
                                    Duration duration_to_add = Duration.ofSeconds(eligible_Places.get(eating_locations_data_pointer).getDurationFromPoint());
                                    currentTime = currentTime.plus(duration_to_add);
                                    eligible_Places.get(0).setArrival_time(currentTime);


                                    duration_to_add = eligible_Places.get(0).getTime_spent();
                                    currentTime = currentTime.plus(duration_to_add);
                                    eligible_Places.get(0).setDeparture_time(currentTime);
                                    //TODO: add how long people typically spend here and check opening hours
                                    System.out.println("location added " + eating_locations_data.get(eating_locations_data_pointer).getAddress() + " " + duration_to_add + " " + currentTime);
                                    eating_locations_data.remove(eligible_Places.get(0));
                                    eligible_Places.clear();

                                    System.out.println("remove locations data check if eligible places cleared " + eligible_Places.size());
                                    iternarySchedule_pointer += 1;
                                    lunch_count += 1;
                                } else {
                                    LocalTime next_nearest_time = currentTime;
                                    for (int k = 0; k < eating_locations_data.size(); k++) {
                                        if (eating_locations_data.get(k).getOpening_hours_test().isAfter(next_nearest_time)) {
                                            System.out.println("new nearest time " + next_nearest_time);
                                            next_nearest_time = eating_locations_data.get(k).getOpening_hours_test();
                                        }

                                    }


                                    for (int z = 0; z < locations_data.size(); z++) {
                                        if (locations_data.get(z).getOpening_hours_test().isAfter(next_nearest_time)) {
                                            System.out.println("new locations nearest time " + next_nearest_time);
                                            next_nearest_time = locations_data.get(z).getOpening_hours_test();
                                            //first_place_to_visit = locations_data.get(z);
                                        }

//
                                    }

                                    if (next_nearest_time == currentTime) {
                                        //stop_running = false;
                                        currentTime = LocalTime.parse("23:59");
                                        break;
                                    }

                                    else if (next_nearest_time != null) {
                                        currentTime = next_nearest_time.minus(Duration.ofSeconds(900)) ;
                                        System.out.println("set new locations nearest time " + next_nearest_time);

                                    }
                                }

                            } //end of find eating place fn











                            //greedy algo find open a location place that is open within next 45 mins + 1 hour
                            else if ( (locations_data.size() > 0) ) {

                                System.out.println("inside locations size > 0 " + locations_data.size());

                                //first find eligible places.
                                for (int h = 0; h < locations_data.size(); h++) {
                                    //find eligible places and check u take 45 mins to travel there and 1 hour to spend there
                                    if ( locations_data.get(h).getOpening_hours_test().isBefore( currentTime.plus(Duration.ofSeconds(2701)) ) && locations_data.get(h).getClosing_hours_test().isAfter( currentTime.plus( Duration.ofSeconds(6300) ) ) ) {
                                        eligible_Places.add( locations_data.get(h) );
                                        locations_data.get(h).setDistanceFromPoint( iternarySchedule.get(iternarySchedule_pointer).getAddress() );
                                    }
                                }

                                //check if eligible places got size
                                if ( eligible_Places.size() > 0 ) {
                                    //since now all eating places are open and can spend 1 hour there, we take closest distance.
                                    eligible_Places.sort(new Comparator<Place>() {
                                        @Override
                                        public int compare(Place o1, Place o2) {
                                            return Integer.compare(o1.getDistanceFromPoint(), o2.getDistanceFromPoint());
                                        }
                                    });


                                    iternarySchedule.add(eligible_Places.get(0));
                                    Duration duration_to_add = Duration.ofSeconds(eligible_Places.get(locations_data_pointer).getDurationFromPoint());
                                    currentTime = currentTime.plus(duration_to_add);
                                    eligible_Places.get(0).setArrival_time(currentTime);


                                    duration_to_add = eligible_Places.get(0).getTime_spent();
                                    currentTime = currentTime.plus(duration_to_add);
                                    eligible_Places.get(0).setDeparture_time(currentTime);
                                    //TODO: add how long people typically spend here and check opening hours
                                    System.out.println("location added " + locations_data.get(locations_data_pointer).getAddress() + " " + duration_to_add + " " + currentTime);
                                    locations_data.remove(eligible_Places.get(0));
                                    eligible_Places.clear();

                                    System.out.println("remove locations data check if eligible places cleared " + eligible_Places.size());
                                    iternarySchedule_pointer += 1;

                                } else if (eligible_Places.size() == 0) {
                                    //means gotta skip time
                                    LocalTime next_nearest_time = currentTime;
                                    for (int k = 0; k < eating_locations_data.size(); k++) {
                                        if (eating_locations_data.get(k).getOpening_hours_test().isAfter(next_nearest_time)) {
                                            System.out.println("new nearest time " + next_nearest_time + " current time " + currentTime);
                                            next_nearest_time = eating_locations_data.get(k).getOpening_hours_test();
                                        }

                                    }


                                    for (int z = 0; z < locations_data.size(); z++) {
                                        if (locations_data.get(z).getOpening_hours_test().isAfter(next_nearest_time)) {
                                            System.out.println("new locations nearest time " + next_nearest_time + " current time " + currentTime);
                                            next_nearest_time = locations_data.get(z).getOpening_hours_test();
                                            //first_place_to_visit = locations_data.get(z);
                                        }

//
                                    }

                                    if (next_nearest_time == currentTime) {
                                        currentTime = LocalTime.parse("23:59");
                                        //stop_running = false;
                                        break;
                                    }

                                    else if (next_nearest_time != null) {
                                        currentTime = next_nearest_time.minus(Duration.ofSeconds(900)) ;
                                        System.out.println("set new locations nearest time " + next_nearest_time + " set new current " +
                                                " time in locationssize > 0 " + currentTime);

                                    }
                                }

                            } //if locations_data















                            //greedy algo find dinner eating place that is open within next 45 mins + 1 hour
                            else if ( currentTime.plus( Duration.ofSeconds(2700)).isAfter(LocalTime.parse("17:30")) && (dinner_count ==0) && (eating_locations_data.size() > 0) ){
                                //prioritise lunch places
                                //first find eligible eating places.
                                System.out.println("dinner look for places" + dinner_count );
                                for (int h = 0; h < eating_locations_data.size(); h++) {

                                    //find eligible places and check u take 45 mins to travel there and 1 hour to spend there
                                    if ( eating_locations_data.get(h).getOpening_hours_test().isBefore( currentTime.plus(Duration.ofSeconds(2701)) ) && eating_locations_data.get(h).getClosing_hours_test().isAfter( currentTime.plus( Duration.ofSeconds(6300) ) ) ) {
                                        eligible_Places.add( eating_locations_data.get(h) );
                                        eating_locations_data.get(h).setDistanceFromPoint( iternarySchedule.get(iternarySchedule_pointer).getAddress() );
                                    }


                                }

                                if (eating_locations_data.size() > 0) {


                                    //since now all eating places are open and can spend 1 hour there, we take closest distance.
                                    eligible_Places.sort(new Comparator<Place>() {
                                        @Override
                                        public int compare(Place o1, Place o2) {
                                            return Integer.compare(o1.getDistanceFromPoint(), o2.getDistanceFromPoint());
                                        }
                                    });

                                    if (eligible_Places.size() > 0) {
                                        iternarySchedule.add(eligible_Places.get(0));
                                        Duration duration_to_add = Duration.ofSeconds(eligible_Places.get(eating_locations_data_pointer).getDurationFromPoint());
                                        currentTime = currentTime.plus(duration_to_add);
                                        eligible_Places.get(0).setArrival_time(currentTime);


                                        duration_to_add = eligible_Places.get(0).getTime_spent();
                                        currentTime = currentTime.plus(duration_to_add);
                                        eligible_Places.get(0).setDeparture_time(currentTime);
                                        //TODO: add how long people typically spend here and check opening hours
                                        System.out.println("location added " + eating_locations_data.get(eating_locations_data_pointer).getAddress() + " " + duration_to_add + " " + currentTime);
                                        eating_locations_data.remove(eligible_Places.get(0));
                                        eligible_Places.clear();

                                        System.out.println("remove locations data check if eligible places cleared " + eligible_Places.size());
                                        iternarySchedule_pointer += 1;
                                        dinner_count += 1;
                                    } else if (eligible_Places.size() == 0) {
                                        //means gotta skip time
                                        LocalTime next_nearest_time = currentTime;
                                        for (int k = 0; k < eating_locations_data.size(); k++) {
                                            if (eating_locations_data.get(k).getOpening_hours_test().isAfter(next_nearest_time)) {
                                                System.out.println("new nearest time " + next_nearest_time);
                                                next_nearest_time = eating_locations_data.get(k).getOpening_hours_test();
                                            }

                                        }


                                        for (int z = 0; z < locations_data.size(); z++) {
                                            if (locations_data.get(z).getOpening_hours_test().isAfter(next_nearest_time)) {
                                                System.out.println("new locations nearest time " + next_nearest_time);
                                                next_nearest_time = locations_data.get(z).getOpening_hours_test();
                                                //first_place_to_visit = locations_data.get(z);
                                            }

//
                                        }

                                        if (next_nearest_time==currentTime) {
                                            //stop_running = false;
                                            currentTime = LocalTime.parse("23:59");
                                            break;
                                        }

                                        else if (next_nearest_time != null) {
                                            currentTime = next_nearest_time.minus(Duration.ofSeconds(900)) ;
                                            System.out.println("set new locations nearest time " + next_nearest_time);

                                        }
                                    }



                                }

                            } //end of find dinner eating place fn

                            else if (eating_locations_data.size() == 0 && locations_data.size() ==0) {
                                System.out.println("all places done ");
                                //return_array_of_clusers.add(iternarySchedule);
                                currentTime = LocalTime.parse("23:59");
                                break;




                            }













                            for (int z = 0; z < iternarySchedule.size(); z++) {
                                System.out.println("iternary schedule: " + iternarySchedule.get(z).getAddress());
                            }


                            context.shutdown();
                            //return_array_of_clusers.add(iternarySchedule);
                            System.out.println("context shutdown ");

                        }

                        System.out.println("out of while loop " + currentTime);



                        return_array_of_clusers.add(iternarySchedule);
                        //break;


                    }

                    for (int i = 0; i < return_array_of_clusers.size(); i++) {
                        System.out.println(" size array " + return_array_of_clusers.get(i).size()
                                + " array of array clusters "+ return_array_of_clusers.get(i).toString() + "iteration: " + i);
                        for (int j = 0; j < return_array_of_clusers.get(i).size(); j++) {
                            System.out.println("inside " + return_array_of_clusers.get(i).get(j).getAddress() );
                        }
                    }

                    System.out.println("array of arrays clusters size " + return_array_of_clusers.size() + " saved_places array " + saved_Places.size() + " everything " + saved_Places.toString() );


                    Trip trip = FileManager.getTrip(IternaryActivity.this, "final.json");
                    trip.days = return_array_of_clusers;
                    trip.savedPlaces = saved_Places;
                    FileManager.saveTrip(IternaryActivity.this, "final.json", trip);









                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getPlacesapi.start();







        //add 2 distance
        //Double dist = CalculationByDistance(lat, lat2);
        //Log.d("IternaryActivity ", "DIST ============================="+dist);












        //to go to add places view
        btn_toaddplaces = (Button) findViewById(R.id.btn_to_searchforplaces);
        btn_toaddplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlacesActivity();
            }
        });

        //link listview to xml file
        listView = (ListView) findViewById(R.id.placesListView);
        PlacesBaseAdapter customBaseAdapter = new PlacesBaseAdapter(getApplicationContext(), savedPlaceslist);
        listView.setAdapter(customBaseAdapter);

        //code to navigate bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.itinerary);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.addLocation:
//                        startActivity(new Intent(getApplicationContext(), AddPlaces.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.itinerary:
                        return true;
//                    case R.id.addPlacesWorking:
//                        startActivity(new Intent(getApplicationContext(), AddPlacesWorking.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        //code to navigate bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.itinerary);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.addLocation:
//                        startActivity(new Intent(getApplicationContext(), AddPlaces.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.itinerary:
                        return true;
//                    case R.id.addPlacesWorking:
//                        startActivity(new Intent(getApplicationContext(), AddPlacesWorking.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    } //end of oncreate function

    //go to search places page
    public void AddPlacesActivity() {
        Intent intent = new Intent(this, AddPlaces.class);
        startActivity(intent);
    }



    public static <T> List<List<T>> getAllCombinations(List<T> inputList) {
        List<List<T>> result = new ArrayList<>();
        generateCombinations(new ArrayList<>(), inputList, 0, result);
        return result;
    }

    private static <T> void generateCombinations(List<T> current, List<T> inputList, int index, List<List<T>> result) {
        if (index == inputList.size()) {
            result.add(new ArrayList<>(current));
        } else {
            // Exclude the current element and move to the next
            generateCombinations(current, inputList, index + 1, result);

            // Include the current element and move to the next
            current.add(inputList.get(index));
            generateCombinations(current, inputList, index + 1, result);
            current.remove(current.size() - 1);
        }
    }









} //end of iternary activity class













