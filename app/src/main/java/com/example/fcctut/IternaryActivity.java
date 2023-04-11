package com.example.fcctut;

import static java.util.stream.Collectors.toSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;




import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PendingResult;
import com.google.maps.internal.ApiConfig;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;

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
//                    OkHttpClient client = new OkHttpClient().newBuilder()
//                            .build();
//                    MediaType mediaType = MediaType.parse("text/plain");
//                    RequestBody body = RequestBody.create("", mediaType);
//                    Request request = new Request.Builder()
//                            .url("https://maps.googleapis.com/maps/api/directions/json?origin=Adelaide%2C%20SA&destination=Adelaide%2C%20SA&waypoints=optimize%3Atrue%7CBarossa%20Valley%2C%20SA%7CClare%2C%20SA%7CConnawarra%2C%20SA%7CMcLaren%20Vale%2C%20SA&key="+BuildConfig.WEB_API_KEY)
//                            .method("GET", null)
//                            .build();
//                    Response response = client.newCall( (Request) request  ).execute();
//                    JSONObject jsonObject = new JSONObject(response.body().string());
                    //jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("waypoint_order");

//                    GeoApiContext geoApiContext = new GeoApiContext.Builder(new GaeRequestHandler.Builder())
//                            .baseUrlOverride("https://maps.googleapis.com")
//                            .apiKey(BuildConfig.WEB_API_KEY)
//                            .build();
//                    DirectionsApiRequest directionsApiRequest = new DirectionsApiRequest(geoApiContext);
//
//                    directionsApiRequest.setCallback(new PendingResult.Callback<DirectionsResult>() {
//                        @Override
//                        public void onResult(DirectionsResult result) {
//                            DirectionsRoute[] routes = result.routes;
//                            Log.d("calldirectionsapi", routes.toString());
//                        }
//
//                        @Override
//                        public void onFailure(Throwable e) {
//                            e.printStackTrace();
//                        }
//                    });


                    //Log.d("calldirectionsapi", directionsApiRequest.getResult());



                    //for (int i = 0; i < waypoint_order.length(); i++) {
                    //    Log.d( "iternarycallforloop", jsonObject.getJSONArray("routes").getJSONObject(0).toString() );
                    //}

                    //for (int i = 1; i < jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("waypoint_order").length(); i++) {
                    //    destinations.add(jsonObject.getJSONArray("routes").getJSONObject(i).start_location);
                    //}
//                        var marker = new google.maps.Marker({
//                                position: route.legs[i].start_location,
//                                map: map,
//                                label: i.toString() });

                    //Log.d("iternarycall", jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("waypoint_order").toString());


//                    JSONObject route = new JSONObject(response);
//                    Route routeReturn = new Route();
//
//                    JSONArray legs = route.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
//                    for (int j = 0; j < legs.length(); j++) {
//                        JSONObject leg = legs.getJSONObject(j);
//                        Leg leg1 = new Leg();
//
//                        int distance = leg.getJSONObject("distance").getInt("value");
//                        leg1.setDistance(distance);
//                        routeReturn.addLeg(leg1);
//
//                        JSONArray steps = route.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(j).getJSONArray("steps");
//                        for (int k = 0; k < steps.length(); k++) {
//                            JSONObject step = steps.getJSONObject(k);
//                            String polyline = step.getJSONObject("polyline").getString("points");
//
//                            List<LatLng> latLngs = PolylineDecoder.decodePoly(polyline);
//
//                            leg1.addAllPoints(latLngs);
//                        }

//                    GeoApiContext geoApiContext = new GeoApiContext.Builder(new GaeRequestHandler.Builder())
//                            .baseUrlOverride("https://maps.googleapis.com")
//                            .apiKey(BuildConfig.WEB_API_KEY)
//                            .build();
//                    String[] stringWaypoints = new String[4];
//                    //stringWaypoints[0] = ("-34.92641,138.59439"); //adelaide
//                    stringWaypoints[0] = ("-34.51710,138.94736"); //barossa valley, SA
//                    stringWaypoints[1] = ("-33.83414,138.61440"); // Clare SA
//                    stringWaypoints[2] = ("-37.28301,140.83203"); //Connawarra SA
//                    stringWaypoints[3] = ("-35.21477,138.59439"); // Mcclaren Vale SA
//                    //DirectionsRoute [] googleRoutes = new DirectionsRoute[0];
//                    DirectionsResult googleResult = new DirectionsResult();
//                    googleResult = DirectionsApi.newRequest(geoApiContext).origin("-34.92641,138.59439").destination("-34.92641,138.59439").waypoints(stringWaypoints).optimizeWaypoints(true).await();
//                    Log.d("googleapicall", googleResult.routes.toString());
                    //for (DirectionsRoute googleRoute : googleRoutes) {
                    //    result.add(createRoute(allWaypoints, googleRoute));
                    //}
                    //cachedRoutes.put(cachingRouteKey, result);
                    //return result;





////                    GeocodingResult[] results =  GeocodingApi.geocode(context,
//                            "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
//                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                    System.out.println("google geoapicontext " + gson.toJson(results[0].addressComponents)+" next item " + gson.toJson(results) + " original link " + context.toString());
//                    context.shutdown();


                    //List<LatLng> waypoints = new ArrayList<>();
                    //waypoints.add(new LatLng(19.431676, -99.133999));
                    //waypoints.add(new LatLng(19.427915, -99.138939));
                    //waypoints.add();
                    //waypoints.add();
                    //waypoints.add(new LatLng(19.427705, -99.198858));
                    //waypoints.add(new LatLng(19.425869, -99.160716));
                    //LatLng origin = waypoints.get(0);
                    //System.out.println("==============================" + waypoints.get(0).toString() + "--------- " + waypoints.get(0));
                    //LatLng destination = waypoints.get(1);




                    //DirectionsRoute[] route = result.routes;
                    //EncodedPolyline polyline = route[0].overviewPolyline;
                    //List<LatLng> overviewPath = polyline.decodePath();
                    //System.out.println(" ========================== " + overviewPath.toString() );
                    //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    //System.out.println("+++++++++++++++++++++++ " + gson.toJson(result) );
                    //int[] waypoints = result.routes[0].waypointOrder;
                    //for (int i = 0; i < result.routes[0].waypointOrder.length; i++) {
                    //    System.out.println(result.routes[0].waypointOrder[0]);;
                    //}
                    //System.out.println("============= routes: " + gson.toJson(result.routes) +
                    //" +++++++++++++++++++++++++++++ waypoints: " + gson.toJson(result.routes[0].waypointOrder)
                      //      " //////// " );
                            //" //////route[0]: " + gson.toJson(result.routes[0]));


//this prints out array of waypoints
//                    int[] waypointOrder = result.routes[0].waypointOrder;
//
//                    String[] waypointNames = new String[waypointOrder.length];
//                    for (int i = 0; i < waypointOrder.length; i++) {
//                        int index = waypointOrder[i];
//                        if (index == -1) {
//                            // The corresponding waypoint is the destination.
//                            waypointNames[i] = "Destination";
//                        } else {
//                            waypointNames[i] = "Waypoint " + (index + 1);
//
//                        }
//                    }
//                    System.out.println("================== " + Arrays.toString(waypointNames) );



                    //output of this code is
//                  [4 Scott St, Clare SA 5453, Australia to 73 Presser Rd, Tanunda SA 5352, Australia,
//
//                  135 Waymouth St, Adelaide SA 5000, Australia to 4 Scott St, Clare SA 5453, Australia,
//
//                  73 Presser Rd, Tanunda SA 5352, Australia to 100 Alexander Rd, Coonawarra SA 5263, Australia,
//
//                  100 Alexander Rd, Coonawarra SA 5263, Australia to 102 Oakley Rd, McLaren Flat SA 5171, Australia]
//                    DirectionsRoute[] routes = result.routes;
//                    if (routes != null && routes.length > 0) {
//                        DirectionsLeg[] legs = routes[0].legs;
//                        if (legs != null && legs.length > 0) {
//                            int[] waypointOrder = routes[0].waypointOrder;
//                            String[] waypointNames = new String[waypointOrder.length];
//                            for (int i = 0; i < waypointOrder.length; i++) {
//                                int index = waypointOrder[i];
//                                if (index == -1) {
//                                    // The corresponding waypoint is the destination.
//                                    waypointNames[i] = "Destination";
//                                } else {
//                                    DirectionsLeg leg = legs[index];
//                                    String startLocationName = leg.startAddress;
//                                    String endLocationName = leg.endAddress;
//                                    waypointNames[i] = startLocationName + " to " + endLocationName;
//                                }
//                            }
//                            // Now you can use the waypointNames array as needed
//                            System.out.println(" ===================== " + Arrays.toString(waypointNames));
//                        }
//                    }



//                    I/System.out: ++++++++++++++++++ [1, 0, 2, 3]
//                    I/System.out: ========== Origin: 135 Waymouth St, Adelaide SA 5000, Australia
//                    I/System.out: ============= Waypoint 1: 4 Scott St, Clare SA 5453, Australia
//                    I/System.out: ============= Waypoint 2: 73 Presser Rd, Tanunda SA 5352, Australia
//                    I/System.out: ============= Waypoint 3: 100 Alexander Rd, Coonawarra SA 5263, Australia
//                    I/System.out: ============= Waypoint 4: 102 Oakley Rd, McLaren Flat SA 5171, Australia
//                    I/System.out: ================= Destination: 135 Waymouth St, Adelaide SA 5000, Australia
//                    DirectionsRoute route = result.routes[0];
//                    System.out.println("++++++++++++++++++ "+ Arrays.toString(route.waypointOrder));
//                    String origin = route.legs[0].startAddress;
//                    String destination = route.legs[route.legs.length - 1].endAddress;
//
//                    List<String> waypoints = new ArrayList<>();
//                    for (int i = 0; i < route.legs.length - 1; i++) {
//                        String waypoint = route.legs[i].endAddress;
//                        waypoints.add(waypoint);
//                    }
//
//                    System.out.println("========== Origin: " + origin);
//                    for (int i = 0; i < waypoints.size(); i++) {
//                        System.out.println("============= Waypoint " + (i + 1) + ": " + waypoints.get(i));
//                    }
//                    System.out.println("================= Destination: " + destination);


//this returns all html routes.
//                    DirectionsRoute[] routes = result.routes;
//                    if (routes != null && routes.length > 0) {
//                        DirectionsLeg[] legs = routes[0].legs;
//                        if (legs != null) {
//                            for (int i = 0; i < legs.length; i++) {
//                                DirectionsStep[] steps = legs[i].steps;
//                                if (steps != null) {
//                                    for (int j = 0; j < steps.length; j++) {
//                                        String instructions = steps[j].htmlInstructions;
//                                        System.out.println("Step " + (j+1) + ": " + instructions);
//                                    }
//                                }
//                            }
//                        }
//                    }




//                    DirectionsRoute route = result.routes[0];
//                    LatLng origin = new LatLng(route.legs[0].startLocation.lat, route.legs[0].startLocation.lng);
//                    LatLng destination = new LatLng(route.legs[route.legs.length-1].endLocation.lat, route.legs[route.legs.length-1].endLocation.lng);
//
//                    LatLng[] waypoints = new LatLng[route.waypointOrder.length];
//                    for (int i = 0; i < route.waypointOrder.length; i++) {
//                        int index = route.waypointOrder[i];
//                        waypoints[i] = new LatLng(route.legs[index+1].startLocation.lat, route.legs[index+1].startLocation.lng);
//                    }
//                    System.out.println("===========" + Arrays.toString(waypoints));







                    //******************************************************************************
                    //======================================== FINALISED FOR OPTIMISED WAYPOINTS
//                    >>>>>>>>>>>>>>>>>>>>>>> waypoints order [2, 3, 0, 1]
//                    I/System.out: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 2 -------------- -34.53254080,138.95110220 //mclaren vale
//                    I/System.out: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 3 -------------- -33.83596370,138.61431260 //conarrawa
//                    I/System.out: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 0 -------------- -35.21924080,138.54488720 // barossa valley
//                    I/System.out: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 1 -------------- -37.29463140,140.82097690 //st clare
//                    I/System.out: ================ Origin: -34.92853670,138.60070830  //adelaide
//                    I/System.out: Waypoint 1: -34.53254080,138.95110220 //mcclaren vale
//                    I/System.out: Waypoint 2: -33.83596370,138.61431260 //conarrawa
//                    I/System.out: Waypoint 3: -35.21924080,138.54488720 // barossa
//                    I/System.out: Waypoint 4: -37.29463140,140.82097690 // st clare
//                    I/System.out: Destination: -34.92853670,138.60070830 //adelaide

//                    GeoApiContext context = new GeoApiContext.Builder()
//                            .apiKey(BuildConfig.WEB_API_KEY)
//                            .build();
//                    DirectionsResult result =
//                            DirectionsApi.newRequest(context)
//                                    .mode(TravelMode.DRIVING)
//                                    .units(Unit.METRIC)
//                                    .origin("Adelaide SA, Australia")
//                                    .destination("Adelaide SA, Australia")
//                                    .waypoints("Barossa Valley, Tanunda SA 5352, Australia","Clare SA 5453, Australia","McLaren Vale SA 5171, Australia", "Coonawarra SA 5263, Australia") //[2,3, 0, 1] waypoint_order
//                                    .optimizeWaypoints(true)
//                                    .await();
                    //.origin("-34.92641,138.59439")
                    //.destination("-34.92641,138.59439")
                    //.departureTime(Instant.now())
                    //.waypoints( "-34.51710,138.94736", "-33.83414,138.61440", "-37.28301,140.83203", "-35.21477,138.59439" )
                    //barossa is first, clare sa, connawarra sa, mcclaren vale sa

                    //.waypoints("Barossa Valley, Tanunda SA 5352, Australia","Clare SA 5453, Australia","Coonawarra SA 5263, Australia","McLaren Vale SA 5171, Australia") //[3, 2, 0, 1] waypoint_order

                    //Ginling Chinese Restaurant "609 Marion Rd, South Plympton SA 5038, Australia"
                    //Patch Kitchen & Garden "143 Mount Barker Rd, Stirling SA 5152, Australia"

//                    DirectionsRoute route = result.routes[0];
//                    //System.out.println(":::::::::::::::::::::::: " + );
//                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> waypoints order " + Arrays.toString(route.waypointOrder));
//
//                    LatLng origin = new LatLng(route.legs[0].startLocation.lat, route.legs[0].startLocation.lng);
//                    LatLng destination = new LatLng(route.legs[route.legs.length-1].endLocation.lat, route.legs[route.legs.length-1].endLocation.lng);
//
//                    LatLng[] waypoints = new LatLng[route.waypointOrder.length+2];
//                    waypoints[0] = origin;
//                    for (int i = 1; i < route.waypointOrder.length - 1; i++) {
//                        //System.out.println(route.waypointOrder[i]);
//                        int index = route.waypointOrder[i];
//                        waypoints[i] = new LatLng(route.legs[index+1].startLocation.lat, route.legs[index+1].startLocation.lng);
//                        //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX " + index + " -------------- " + new LatLng(route.legs[index+1].startLocation.lat, route.legs[index+1].startLocation.lng));
//                    }
//                    waypoints[waypoints.length - 1] = destination;
//
//                    //System.out.println("================ Origin: " + origin.toString());
//
//                    for (int i = 0; i < waypoints.length; i++) {
//                        System.out.println("Waypoint: " + waypoints[i].toString());
//                    }
                    //System.out.println("Destination: " + destination.toString());


                    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx first algo try XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    //1st array waypoints array which is [origin, waypoints, dest ]
                    //2nd array eating places array which is just eating places.

//                    String[] eatingPlaces = {"143 Mount Barker Rd, Stirling SA 5152, Australia", "609 Marion Rd, South Plympton SA 5038, Australia"};
//
//                    LocalTime startTime = LocalTime.parse("09:00");
//                    LocalTime endTime = LocalTime.parse("2200");
//                    ArrayList<LatLng> iternarySchedule = new ArrayList<>();
//                    Map<String, ArrayList> iternaryScheduleMap = new HashMap<>();
//                    Map<Integer, ArrayList> iternaryScheduleDistMap = new HashMap<>();
//                    LocalTime lunchTimestart = LocalTime.parse("12:00");
//                    LocalTime lunchTimeend = LocalTime.parse("14:00");
//                    LocalTime dinnerTimestart = LocalTime.parse("18:00");
//                    LocalTime dinnerTimeend = LocalTime.parse("20:00");
//
//                    LocalTime currentTime = startTime;
//
//
//                    System.out.println("==================== waypoints wif origin n dest n waypoints " + Arrays.toString(waypoints));
//                    iternarySchedule.add(waypoints[0]);
//                    getPlaceInfo(waypoints[0]);
//                    int waypoints_pointer = 1;
//                    while (currentTime.isBefore(endTime)) {
//
//                        if (currentTime.isAfter(lunchTimestart) && currentTime.isBefore(lunchTimeend)) {
//                            //prioritise eating place at lunch time
//
//
//                        } else if (currentTime.isAfter(dinnerTimestart) && currentTime.isBefore(dinnerTimeend)) {
//                            //prioritise eating place at dinner time
//
//                        } else {
//                            //add first place of the waypoints map
//
//                            //check if waypoint before is alr in the map
//                            if (iternaryScheduleMap.containsKey(waypoints[waypoints_pointer-1].toUrlValue() ) ) {
//                                iternaryScheduleMap.put( waypoints[waypoints_pointer-1].toUrlValue(), getPlaceInfo(  waypoints[waypoints_pointer - 1]  ) );
//
//                            }
//                            //check if waypoint is alr in the map
//                            if (iternaryScheduleMap.containsKey(waypoints[waypoints_pointer]) ) {
//                                iternaryScheduleMap.put( waypoints[waypoints_pointer].toUrlValue(), getPlaceInfo(  waypoints[waypoints_pointer]  ) );
//                            }
//                            //iternaryScheduleMap.get( waypoints[waypoints_pointer].toUrlValue() ).get(0);
//                            ArrayList<Long> dist_map_obj = getDist_two_places( iternaryScheduleMap.get(waypoints[waypoints_pointer-1].toUrlValue()).get(2).toString() , iternaryScheduleMap.get( waypoints[waypoints_pointer].toUrlValue() ).get(2).toString() );
//                            iternaryScheduleDistMap.put(waypoints_pointer, dist_map_obj);
//
//                            //add time to current time
//                            Long duration = (Long) iternaryScheduleDistMap.get(waypoints_pointer).get(0);
//                            LocalTime time = LocalTime.ofSecondOfDay( duration );
//                            currentTime.plus(time.toSecondOfDay(), ChronoUnit.SECONDS);
//                            System.out.println("XXXXXXXXXXXXXXXXXX added time: " + currentTime.toString());
//
//                            //add the places to iternary
//                            if (iternarySchedule.contains( waypoints[waypoints_pointer] )) {
//                                iternarySchedule.add( waypoints[waypoints_pointer]);
//                            }
//
//                            System.out.println("added place to iternary: check size " + iternarySchedule.size() );
//                        }
//                    }
//                    context.shutdown();





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
                    Record address10 = new Record("4 Jahamun-ro 11-gil, Jongno-gu, Seoul, South Korea", location10);

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


                    //ArrayList<Centroid> centroidkeys = new ArrayList<Centroid>();
                    ArrayList<ArrayList<Record>> array_array_of_clusters = new ArrayList<ArrayList<Record>>();
                    clusters.forEach((key, value) -> {
                        System.out.println(" centroidKeys " + value + " size: "  + value.size());
                        //centroidkeys.add(key);
                        array_array_of_clusters.add((ArrayList<Record>) value );

                    });

                    ArrayList<ArrayList<PlacesSchedule>> return_array_of_clusers = new ArrayList<ArrayList<PlacesSchedule>>();

                    System.out.println("array_array_of_clusters" + array_array_of_clusters.size() );
                    for (int i = 0; i < array_array_of_clusters.size(); i++) {
                        System.out.println(" ========== array array of clusters each array " + array_array_of_clusters.get(i).toString());


                        // ==============================

                        ArrayList<Record> cluster1 = array_array_of_clusters.get(i);

                        for (int a = 0; a < cluster1.size(); a++) {
                            System.out.println("inside cluster1 arraylist " + cluster1.get(a).address + cluster1.size());
                        }


                        GeoApiContext context = new GeoApiContext.Builder()
                                .apiKey(BuildConfig.WEB_API_KEY)
                                .build();
                        //ArrayList<String> locations = new ArrayList<String>();


                        LocalTime startTime = LocalTime.parse("09:00");
                        LocalTime endTime = LocalTime.parse("22:00");
                        LocalTime lunchTimestart = LocalTime.parse("11:00");
                        LocalTime lunchTimeend = LocalTime.parse("14:00");
                        LocalTime dinnerTimestart = LocalTime.parse("17:30");
                        LocalTime dinnerTimeend = LocalTime.parse("20:00");

                        String origin = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";
                        String destination = "16 Myeongdong 9-gil, Jung-gu, Seoul, South Korea";

                        ArrayList<PlacesSchedule> locations_data = new ArrayList<PlacesSchedule>();


                        //add all places and get address, latitude and longitude
                        for (int d = 0; d < cluster1.size(); d++) {
                            System.out.println("check if places can be added " + cluster1.get(d).address);
                            PlacesSchedule new_place = new PlacesSchedule(cluster1.get(d).address);
                            System.out.println(new_place.getAddress() + " " + new_place.getLongitude() + " lat " + new_place.getLatitude());
                            new_place.calc_Dist_two_places(origin);
                            locations_data.add(new_place);
                            System.out.println(" =========== check place: " + new_place.place_Id + " " +
                                    new_place.address + " " + new_place.duration_From_point.toString() + " " +
                                    new_place.longitude + " " + new_place.distance_From_point.toString());

                        }


                        ArrayList<PlacesSchedule> iternarySchedule = new ArrayList<PlacesSchedule>();
                        iternarySchedule.add(new PlacesSchedule(origin));
                        LocalTime currentTime = startTime;
                        int locations_data_pointer = 0;
                        int iternarySchedule_pointer = 0;


                        while (currentTime.isBefore(endTime)) {

                            if (locations_data.size() > 0) {

                                System.out.println("inside locations current time " + currentTime + " locations data size "
                                        + locations_data.size());

                                if (iternarySchedule_pointer < iternarySchedule.size()) {
                                    for (int x = 0; x < locations_data.size(); x++) {
                                        locations_data.get(x).calc_Dist_two_places(iternarySchedule.get(iternarySchedule_pointer).address);
                                    }
                                }
                                locations_data.sort(new Comparator<PlacesSchedule>() {
                                    @Override
                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
                                        return Integer.compare(o1.getDistance_From_point(), o2.getDistance_From_point());
                                    }
                                });
                                iternarySchedule.add(locations_data.get(locations_data_pointer));
                                Duration duration_to_add = Duration.ofSeconds(locations_data.get(locations_data_pointer).duration_From_point);
                                currentTime = currentTime.plus(duration_to_add);
                                //TODO: add how long people typically spend here and check opening hours

                                System.out.println("location added " + locations_data.get(locations_data_pointer).address + " " + duration_to_add + " " + currentTime);
                                locations_data.remove(locations_data.get(locations_data_pointer));

                                System.out.println("remove locations data " + locations_data.size());
                                iternarySchedule_pointer += 1;
                            } else {
                                System.out.println("Break ");
                                break;
                            }


                            for (int z = 0; z < iternarySchedule.size(); z++) {
                                System.out.println("iternary schedule: " + iternarySchedule.get(z).address);
                            }


                            context.shutdown();
                            return_array_of_clusers.add(iternarySchedule);


                        }

                    }






















                    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxxx 2nd try greedy algo =====================


//                    //step 1. start out with all way points
//                    GeoApiContext context = new GeoApiContext.Builder()
//                            .apiKey(BuildConfig.WEB_API_KEY)
//                            .build();
////                    DirectionsResult result =
////                            DirectionsApi.newRequest(context)
////                                    .mode(TravelMode.DRIVING)
////                                    .units(Unit.METRIC)
////                                    .origin("Adelaide SA, Australia")
////                                    .destination("Adelaide SA, Australia")
////                                    .waypoints("Barossa Valley, Tanunda SA 5352, Australia","Clare SA 5453, Australia","McLaren Vale SA 5171, Australia", "Coonawarra SA 5263, Australia", ) //[2,3, 0, 1] waypoint_order
////                                    .await();
//                    ArrayList<String> locations = new ArrayList<String>();
//                    locations.add("Barossa Valley, Tanunda SA 5352, Australia");
//                    locations.add("Clare SA 5453, Australia");
//                    locations.add("McLaren Vale SA 5171, Australia");
//                    locations.add("Coonawarra SA 5263, Australia");
//
//                    ArrayList<String> eating_locations = new ArrayList<String>();
//                    eating_locations.add("609 Marion Rd, South Plympton SA 5038, Australia"); //eating places
//                    eating_locations.add("143 Mount Barker Rd, Stirling SA 5152, Australia"); //eating place patch kitechen
//                    String origin = "Adelaide SA, Australia";
//                    String destination = "Adelaide, Australia";
//
//
//                    LocalTime startTime = LocalTime.parse("09:00");
//                    LocalTime endTime = LocalTime.parse("22:00");
//                    LocalTime lunchTimestart = LocalTime.parse("12:00");
//                    LocalTime lunchTimeend = LocalTime.parse("14:00");
//                    LocalTime dinnerTimestart = LocalTime.parse("18:00");
//                    LocalTime dinnerTimeend = LocalTime.parse("20:00");
//
//
//
//                    ArrayList<PlacesSchedule> locations_data = new ArrayList<PlacesSchedule>();
//                    ArrayList<PlacesSchedule> eating_locations_data = new ArrayList<PlacesSchedule>();
//
//
////                     =========== check place: ChIJR4qQ8UXvuWoRcGNhpmmoPq0 Barossa Valley, Tanunda SA 5352, Australia3314 138.95 74959
////                    I/System.out:  =========== check place: ChIJN5GY8-7Qu2oR4PuOYlQ2AwU Clare SA 5453, Australia6388 138.6142379 147198
////                    I/System.out:  =========== check place: ChIJbbI3M-UlsWoRsLeOYlQ2AwU McLaren Vale SA 5171, Australia2440 138.5448927 39789
////                    I/System.out:  =========== check place: ChIJPTJwAtwIy2oRMBNRo1c2Axw Coonawarra SA 5263, Australia14439 140.8248263 374722
////                    I/System.out:  =========== check place: ChIJXZ6y1afasGoRAWncLnLoVsY 609 Marion Rd, South Plympton SA 5038, Australia892 138.5564615 8322
////                    I/System.out:  =========== check place: ChIJI5guj9Yyt2oRPg0pV3o-QGY 143 Mount Barker Rd, Stirling SA 5152, Australia1322 138.7249857 16772
//                    for (int i = 0; i < locations.size(); i++) {
//                        PlacesSchedule new_place = new PlacesSchedule(locations.get(i));
//                        new_place.calc_Dist_two_places(origin);
//                        locations_data.add(new_place);
//                        System.out.println(" =========== check place: " + new_place.place_Id +" " +
//                                new_place.address +" "+ new_place.duration_From_point.toString() +" " +
//                                new_place.longitude + " "+ new_place.distance_From_point.toString() );
//                    }
//                    locations_data.sort(new Comparator<PlacesSchedule>() {
//                        @Override
//                        public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                            return Integer.compare(o1.getDistance_From_point(), o2.getDistance_From_point());
//                        }
//                    });
//
//
////                    check if sorted by location 39789
////                    I/System.out: check if sorted by location 74959
////                    I/System.out: check if sorted by location 147198
////                    I/System.out: check if sorted by location 374722
//                    for (int i = 0; i < locations_data.size(); i++) {
//                        System.out.println("check if sorted by location " + locations_data.get(i).distance_From_point.toString() );
//                    }
//
//
//                    for (int i = 0; i < eating_locations.size(); i++) {
//                        PlacesSchedule new_place = new PlacesSchedule(eating_locations.get(i));
//                        new_place.calc_Dist_two_places(origin);
//                        eating_locations_data.add(new_place);
//                        System.out.println(" =========== check eating place: " + new_place.place_Id +" " +
//                                new_place.address +" "+ new_place.duration_From_point.toString() +" " +
//                                new_place.longitude + " "+ new_place.distance_From_point.toString() );
//                    }
//
//
//                    //by now address, latitude, longtitude, distance_from_origin and duration_from_point is updated.
//
//                    ArrayList<PlacesSchedule> iternarySchedule = new ArrayList<PlacesSchedule>();
//                    iternarySchedule.add(new PlacesSchedule(origin) );
//                    LocalTime currentTime = startTime;
//                    int locations_data_pointer = 0;
//                    int eating_locations_data_pointer = 0;
//                    int iternarySchedule_pointer = 0;





//
//
////                    Adelaide SA, Australia
////I/System.out: iternary schedule: McLaren Vale SA 5171, Australia
////I/System.out: iternary schedule: Barossa Valley, Tanunda SA 5352, Australia
////I/System.out: iternary schedule: Clare SA 5453, Australia
////I/System.out: iternary schedule: 609 Marion Rd, South Plympton SA 5038, Australia
////I/System.out: iternary schedule: Coonawarra SA 5263, Australia
////I/System.out: iternary schedule: 143 Mount Barker Rd, Stirling SA 5152, Australia
//                    for (int i = 0; i < iternarySchedule.size(); i++) {
//                        System.out.println("iternary schedule: " + iternarySchedule.get(i).address);
//                    }


                        // ##############################################3rd try algo
                        //each day split into 2 segments,
                        // first segment based on earliest opening time and earliest ending time
                        //second segment based on latest opening time and latest ending time.
                        // within each segment look at the places available choose nearest distance.

//                    //step 1. start out with all way points
//                    GeoApiContext context = new GeoApiContext.Builder()
//                            .apiKey(BuildConfig.WEB_API_KEY)
//                            .build();
//                    ArrayList<String> locations = new ArrayList<String>();
//                    locations.add("Barossa Valley, Tanunda SA 5352, Australia"); //1030am - 430pm
//                    locations.add("Clare SA 5453, Australia"); //10am - 430pm
//                    locations.add("McLaren Vale SA 5171, Australia"); //11am - 5pm
//                    locations.add("Coonawarra SA 5263, Australia"); //9am - 430pm
//
//                    ArrayList<String> eating_locations = new ArrayList<String>();
//                    eating_locations.add("609 Marion Rd, South Plympton SA 5038, Australia"); //eating places 5-9.40pm
//                    eating_locations.add("143 Mount Barker Rd, Stirling SA 5152, Australia"); //eating place patch kitechen 8am - 12pm
//                    String origin = "Adelaide SA, Australia"; //9am leave
//                    String destination = "Adelaide, Australia"; //back home by 10pm
//
//                    LocalTime startTime = LocalTime.parse("09:00");
//                    LocalTime endTime = LocalTime.parse("22:00");
//                    LocalTime lunchTimestart = LocalTime.parse("11:00");
//                    LocalTime lunchTimeend = LocalTime.parse("14:00");
//                    LocalTime dinnerTimestart = LocalTime.parse("17:30");
//                    LocalTime dinnerTimeend = LocalTime.parse("20:00");
//
//                    ArrayList<PlacesSchedule> locations_data = new ArrayList<PlacesSchedule>();
//                    ArrayList<PlacesSchedule> eating_locations_data = new ArrayList<PlacesSchedule>();
//
//                    //add all places and get address, latitude and longitude
//                    for (int i = 0; i < locations.size(); i++) {
//                        PlacesSchedule new_place = new PlacesSchedule(locations.get(i));
//                        new_place.calc_Dist_two_places(origin);
//                        locations_data.add(new_place);
//                        System.out.println(" =========== check place: " + new_place.place_Id +" " +
//                                new_place.address +" "+ new_place.duration_From_point.toString() +" " +
//                                new_place.longitude + " "+ new_place.distance_From_point.toString() );
//
//                    }
//                    locations_data.get(0).setOpeningHours(LocalTime.parse("10:30"));
//                    locations_data.get(0).setClosingHours(LocalTime.parse("16:30"));
//                    locations_data.get(1).setOpeningHours(LocalTime.parse("10:00"));
//                    locations_data.get(1).setClosingHours(LocalTime.parse("16:30"));
//                    locations_data.get(2).setOpeningHours(LocalTime.parse("11:00"));
//                    locations_data.get(2).setClosingHours(LocalTime.parse("17:00"));
//                    locations_data.get(3).setOpeningHours(LocalTime.parse("09:00"));
//                    locations_data.get(3).setClosingHours(LocalTime.parse("16:30"));
//
//
//                    locations_data.get(0).setTime_spent(Duration.ofSeconds(7200));
//                    locations_data.get(1).setTime_spent(Duration.ofSeconds(5400));
//                    locations_data.get(2).setTime_spent(Duration.ofSeconds(4500));
//                    locations_data.get(3).setTime_spent(Duration.ofSeconds(3600));
//
//
//                    locations_data.get(0).setEating_place(false);
//                    locations_data.get(1).setEating_place(false);
//                    locations_data.get(2).setEating_place(false);
//                    locations_data.get(3).setEating_place(false);
//
//
//                    for (int i = 0; i < eating_locations.size(); i++) {
//                        PlacesSchedule new_place = new PlacesSchedule(eating_locations.get(i));
//                        new_place.calc_Dist_two_places(origin);
//                        eating_locations_data.add(new_place);
//                        System.out.println(" =========== check eating place: " + new_place.place_Id +" " +
//                                new_place.address +" "+ new_place.duration_From_point.toString() +" " +
//                                new_place.longitude + " "+ new_place.distance_From_point.toString() );
//
//                    }
//
//                    eating_locations_data.get(0).setOpeningHours(LocalTime.parse("17:00"));
//                    eating_locations_data.get(0).setClosingHours(LocalTime.parse("21:40"));
//                    eating_locations_data.get(1).setOpeningHours(LocalTime.parse("08:00"));
//                    eating_locations_data.get(1).setClosingHours(LocalTime.parse("14:00"));
//
//                    eating_locations_data.get(0).setTime_spent(Duration.ofSeconds(3600));
//                    eating_locations_data.get(1).setTime_spent(Duration.ofSeconds(4500));
//
//                    eating_locations_data.get(0).setEating_place(true);
//                    eating_locations_data.get(1).setEating_place(true);
//
//
//                    LocalTime earliestOpening = LocalTime.parse("08:00");
//                    LocalTime earliestClosing = LocalTime.parse("14:00");
//                    LocalTime latestOpening = LocalTime.parse("17:00");
//                    LocalTime latestClosing = LocalTime.parse("21:40");
//
//                    //  if (earliestClosing.isBefore(latestOpening) ) {
//                    //      LocalTime between_start_time = earliestClosing;
//                    //      LocalTime between_end_time = latestOpening;
//                    //   }
//
//                    //by now address, latitude, longtitude, distance_from_origin and duration_from_point is updated.
//
//                    ArrayList<PlacesSchedule> iternarySchedule = new ArrayList<PlacesSchedule>();
//                    iternarySchedule.add(new PlacesSchedule(origin) );
//                    LocalTime currentTime = startTime;
//                    int locations_data_pointer = 0;
//                    int iternarySchedule_pointer = 0;
//                    int eating_locations_counter = 0;
//                    int eating_locations_data_pointer = 0;
//
//                    ArrayList<PlacesSchedule> eligiblePlaces = new ArrayList<PlacesSchedule>();
//                    int eligiblePlace_pointer = 0;


//                    while (currentTime.isBefore(endTime) ) {
//
//                        if (  (currentTime.isAfter(lunchTimestart) && currentTime.isBefore(lunchTimeend) )
//                        )   {
//
//
//                            LocalTime finalCurrentTime = currentTime;
//                            System.out.println("inside lunch eating places time: " + currentTime + " final current " + finalCurrentTime);
//                            //loop thru check for all eligible places
//                            for (int i = 0; i <eating_locations_data.size(); i++) {
//                                System.out.println("for loop to look for eligible places " + eating_locations_data.get(i).address);
//                                eating_locations_data.get(i).setOpen_now(finalCurrentTime);
//                                if ( eating_locations_data.get(i).getOpen_now() ||
//                                        eating_locations_data.get(i).openingHours.isAfter(finalCurrentTime.minus(Duration.ofSeconds(2700))) ) {
//                                    System.out.println("for loop to look for eligible places first if " + eating_locations_data.get(i).address);
//                                    if (eating_locations_data.get(i).getOpeningHours().isBefore(lunchTimeend) ) {
//                                        System.out.println("for loop to look for eligible places second if " + eating_locations_data.get(i).address);
//                                        eligiblePlaces.add(eating_locations_data.get(i) );
//
//                                    }
//                                }
//                            }
//                            eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                @Override
//                                public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                    if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                        return -1;
//                                    } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                        return 1;
//                                    } else {
//                                        return 0;
//                                    }
//                                }
//                            });
//
//                            System.out.println("lunch time eating places: " + eating_locations_data.size() + " eligible places "
//                                    + eligiblePlaces.size());
//
//                            //add to iternary schedule
//                            iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            //set distance from iternary schedule first place
//                            eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                            //duration to travel there
//                            Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                            //duration of time spent there
//                            duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                            //update current time.
//                            currentTime = currentTime.plus(duration_to_add);
//
//                            System.out.println("eating location added " + eating_locations_data.get( eating_locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                            eating_locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            System.out.println("remove eating locations data " + eating_locations_data.size());
//                            iternarySchedule_pointer += 1;
//                            eligiblePlaces.clear();
//
//
//
//
//                        } else if (
//                                (currentTime.isAfter(dinnerTimestart) && currentTime.isBefore(dinnerTimeend) ) ) {
//
//
//
//                            LocalTime finalCurrentTime = currentTime;
//                            System.out.println("inside dinner eating places time: " + currentTime + " final current time " + finalCurrentTime);
//                            //loop thru check for all eligible places
//                            for (int i = 0; i <eating_locations_data.size(); i++) {
//                                System.out.println("for loop to check thru eligible places " + eating_locations_data.get(i).address);
//                                eating_locations_data.get(i).setOpen_now(finalCurrentTime);
//                                if ( eating_locations_data.get(i).getOpen_now() ||
//                                        eating_locations_data.get(i).openingHours.isAfter(finalCurrentTime.minus(Duration.ofSeconds(2700))) ) {
//                                    System.out.println("for loop to check thru eligible places first if  " + eating_locations_data.get(i).address);
//                                    if (eating_locations_data.get(i).getOpeningHours().isBefore(latestClosing) ) {
//                                        System.out.println("for loop to check thru eligible places second if " + eating_locations_data.get(i).address);
//                                        eligiblePlaces.add(eating_locations_data.get(i) );
//                                    }
//                                }
//                            }
//                            eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                @Override
//                                public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                    if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                        return -1;
//                                    } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                        return 1;
//                                    } else {
//                                        return 0;
//                                    }
//                                }
//                            });
//
//                            //add to iternary schedule
//                            iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            //set distance from iternary schedule first place
//                            eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                            //duration to travel there
//                            Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                            //duration of time spent there
//                            duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                            //update current time.
//                            currentTime = currentTime.plus(duration_to_add);
//
//                            System.out.println("eating location added " + eating_locations_data.get( eating_locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                            eating_locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            System.out.println("remove eating locations data " + eating_locations_data.size());
//                            iternarySchedule_pointer += 1;
//                            eligiblePlaces.clear();
//
//
//
//                        } else if (locations_data.size() > 0 ){
//
//                            System.out.println("inside locations current time " + currentTime );
//
//
//                            //check time now
//
//                            LocalTime finalCurrentTime = currentTime;
//
//                            System.out.println("inside locations current time " + currentTime + " finalcurrenttime: " + finalCurrentTime );
//
//                                //loop thru check for all eligible places
//                            for (int i = 0; i <locations_data.size(); i++) {
//                                locations_data.get(i).setOpen_now(finalCurrentTime);
//                                LocalTime check_ifplaceopen45mins_before = finalCurrentTime.plus(Duration.ofSeconds(2700));
//                                System.out.println(" inside locations current time for loop " + finalCurrentTime + " checkifplacesopen " + check_ifplaceopen45mins_before
//                                );
//                                if ( locations_data.get(i).getOpen_now() ||
//                                        check_ifplaceopen45mins_before.isAfter( locations_data.get(i).getOpeningHours() ) ) {
//                                    System.out.println(" inside locations current time for loop first if statement " +
//                                            locations_data.get(i).address);
//                                    if (locations_data.get(i).getOpeningHours().isBefore(earliestClosing) ) {
//                                        eligiblePlaces.add(locations_data.get(i) );
//                                        System.out.println(" inside locations current time for loop second if statement " +
//                                                locations_data.get(i).address);
//                                    }
//                                }
//                            }
//                            eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                @Override
//                                public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                    if (o1.getDistance_From_point() < o2.getDistance_From_point()) {
//                                        return -1;
//                                    } else if (o2.getDistance_From_point() < o1.getDistance_From_point()) {
//                                        return 1;
//                                    } else {
//                                        return 0;
//                                    }
//                                }
//                            });
//
//
//
//
//
//
//                            System.out.println("eligible Places, " + eligiblePlaces.size() );
//
//                                //add to iternary schedule
//                            iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                //set distance from iternary schedule first place
//                            eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                                //duration to travel there
//                            Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                                //duration of time spent there
//                            duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                                //update current time.
//                            currentTime = currentTime.plus(duration_to_add);
//
//                            System.out.println("location added " + locations_data.get( locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                            locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            System.out.println("remove locations data " + locations_data.size());
//                            iternarySchedule_pointer += 1;
//                            eligiblePlaces.clear();


//===================================== arrange by opening hours  not a good result
                        //split into 3 segments of days not good.
//                    while (currentTime.isBefore(endTime) ) {
//
//                        if (  (currentTime.isAfter(lunchTimestart) && currentTime.isBefore(lunchTimeend) )
//                                 )   {
//
//
//                            LocalTime finalCurrentTime = currentTime;
//                            System.out.println("inside lunch eating places time: " + currentTime + " final current " + finalCurrentTime);
//                            //loop thru check for all eligible places
//                            for (int i = 0; i <eating_locations_data.size(); i++) {
//                                System.out.println("for loop to look for eligible places " + eating_locations_data.get(i).address);
//                                eating_locations_data.get(i).setOpen_now(finalCurrentTime);
//                                if ( eating_locations_data.get(i).getOpen_now() ||
//                                        eating_locations_data.get(i).openingHours.isAfter(finalCurrentTime.minus(Duration.ofSeconds(2700))) ) {
//                                    System.out.println("for loop to look for eligible places first if " + eating_locations_data.get(i).address);
//                                    if (eating_locations_data.get(i).getOpeningHours().isBefore(lunchTimeend) ) {
//                                        System.out.println("for loop to look for eligible places second if " + eating_locations_data.get(i).address);
//                                        eligiblePlaces.add(eating_locations_data.get(i) );
//
//                                    }
//                                }
//                            }
//                            eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                @Override
//                                public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                    if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                        return -1;
//                                    } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                        return 1;
//                                    } else {
//                                        return 0;
//                                    }
//                                }
//                            });
//
//                            System.out.println("lunch time eating places: " + eating_locations_data.size() + " eligible places "
//                            + eligiblePlaces.size());
//
//                                //add to iternary schedule
//                            iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                //set distance from iternary schedule first place
//                            eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                                //duration to travel there
//                            Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                                //duration of time spent there
//                            duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                                //update current time.
//                            currentTime = currentTime.plus(duration_to_add);
//
//                            System.out.println("eating location added " + eating_locations_data.get( eating_locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                            eating_locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            System.out.println("remove eating locations data " + eating_locations_data.size());
//                            iternarySchedule_pointer += 1;
//                            eligiblePlaces.clear();
//
//
//
//
//                        } else if (
//                                (currentTime.isAfter(dinnerTimestart) && currentTime.isBefore(dinnerTimeend) ) ) {
//
//
//
//                            LocalTime finalCurrentTime = currentTime;
//                            System.out.println("inside dinner eating places time: " + currentTime + " final current time " + finalCurrentTime);
//                            //loop thru check for all eligible places
//                            for (int i = 0; i <eating_locations_data.size(); i++) {
//                                System.out.println("for loop to check thru eligible places " + eating_locations_data.get(i).address);
//                                eating_locations_data.get(i).setOpen_now(finalCurrentTime);
//                                if ( eating_locations_data.get(i).getOpen_now() ||
//                                        eating_locations_data.get(i).openingHours.isAfter(finalCurrentTime.minus(Duration.ofSeconds(2700))) ) {
//                                    System.out.println("for loop to check thru eligible places first if  " + eating_locations_data.get(i).address);
//                                    if (eating_locations_data.get(i).getOpeningHours().isBefore(latestClosing) ) {
//                                        System.out.println("for loop to check thru eligible places second if " + eating_locations_data.get(i).address);
//                                        eligiblePlaces.add(eating_locations_data.get(i) );
//                                    }
//                                }
//                            }
//                            eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                @Override
//                                public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                    if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                        return -1;
//                                    } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                        return 1;
//                                    } else {
//                                        return 0;
//                                    }
//                                }
//                            });
//
//                            //add to iternary schedule
//                            iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            //set distance from iternary schedule first place
//                            eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                            //duration to travel there
//                            Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                            //duration of time spent there
//                            duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                            //update current time.
//                            currentTime = currentTime.plus(duration_to_add);
//
//                            System.out.println("eating location added " + eating_locations_data.get( eating_locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                            eating_locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                            System.out.println("remove eating locations data " + eating_locations_data.size());
//                            iternarySchedule_pointer += 1;
//                            eligiblePlaces.clear();
//
//
//
//                        } else if (locations_data.size() > 0 ){
//
//                            System.out.println("inside locations current time " + currentTime );
//
//
//                            //check time now
//                            if (currentTime.isBefore(earliestClosing) ) {
//                                LocalTime finalCurrentTime = currentTime;
//
//                                System.out.println("inside locations current time < earliest closing " + currentTime );
//
//                                //loop thru check for all eligible places
//                                for (int i = 0; i <locations_data.size(); i++) {
//                                    locations_data.get(i).setOpen_now(finalCurrentTime);
//                                    if ( locations_data.get(i).getOpen_now() ||
//                                            finalCurrentTime.plus(Duration.ofSeconds(2700)).isAfter( locations_data.get(i).getOpeningHours() ) ) {
//                                        if (locations_data.get(i).getOpeningHours().isBefore(earliestClosing) ) {
//                                            eligiblePlaces.add(locations_data.get(i) );
//                                        }
//                                    }
//                                }
//                                eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                        if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                            return -1;
//                                        } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                            return 1;
//                                        } else {
//                                            return 0;
//                                        }
//                                    }
//                                });
//                                System.out.println("eligible Places, " + eligiblePlaces.size() );
//
//                                //add to iternary schedule
//                                iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                //set distance from iternary schedule first place
//                                eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                                //duration to travel there
//                                Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                                //duration of time spent there
//                                duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                                //update current time.
//                                currentTime = currentTime.plus(duration_to_add);
//
//                                System.out.println("location added " + locations_data.get( locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                                locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                System.out.println("remove locations data " + locations_data.size());
//                                iternarySchedule_pointer += 1;
//                                eligiblePlaces.clear();
//
//                                } else if ( latestOpening.isAfter(earliestClosing) && currentTime.isAfter(earliestClosing) ) {
//
//                                if (currentTime.isAfter(LocalTime.parse("20:20")) ) {
//                                    System.out.println("Break at 20:20");
//                                    break;
//                                }
//
//                                LocalTime finalCurrentTime = currentTime;
//                                System.out.println("inside locations earliest closing < current time < latest opening " + currentTime +
//                                        " final current time " + finalCurrentTime);
//
//                                //loop thru check for all eligible places
//                                for (int i = 0; i <locations_data.size(); i++) {
//                                    System.out.println(" inside for loop for check all eligible places " + locations_data.size()
//                                    + " current location " + locations_data.get(i).address);
//                                    locations_data.get(i).setOpen_now(finalCurrentTime);
//                                    if ( locations_data.get(i).getOpen_now() ||
//                                            locations_data.get(i).openingHours.isAfter(finalCurrentTime.minus(Duration.ofSeconds(2700))) ) {
//                                        System.out.println(" inside for loop for check all eligible places first if " + locations_data.size()
//                                                + " current location " + locations_data.get(i).address);
//                                        if (locations_data.get(i).getOpeningHours().isBefore(dinnerTimeend) ) {
//                                            System.out.println(" inside for loop for check all eligible places second if " + locations_data.size()
//                                                    + " current location " + locations_data.get(i).address);
//                                            eligiblePlaces.add(locations_data.get(i) );
//                                        }
//                                    }
//                                }
//
//                                eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                        if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                            return -1;
//                                        } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                            return 1;
//                                        } else {
//                                            return 0;
//                                        }
//                                    }
//                                });
//                                System.out.println(" in earliest closing < current time < latest opening size of eligible places " + eligiblePlaces.size());
//
//                                //add to iternary schedule
//                                iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                //set distance from iternary schedule first place
//                                eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                                //duration to travel there
//                                Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                                //duration of time spent there
//                                duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                                //update current time.
//                                currentTime = currentTime.plus(duration_to_add);
//
//                                System.out.println("location added " + locations_data.get( locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                                locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                System.out.println("remove locations data " + locations_data.size());
//                                iternarySchedule_pointer += 1;
//                                eligiblePlaces.clear();
//
//                            } else if ( latestClosing.isAfter(latestOpening) && currentTime.isAfter(latestOpening) ) {
//
//                                System.out.println("inside locations current time < latest closing " + currentTime );
//
//                                LocalTime finalCurrentTime = currentTime;
//
//                                //loop thru check for all eligible places
//                                for (int i = 0; i <locations_data.size(); i++) {
//                                    locations_data.get(i).setOpen_now(finalCurrentTime);
//                                    if ( locations_data.get(i).getOpen_now() ||
//                                            locations_data.get(i).openingHours.isAfter(finalCurrentTime.minus(Duration.ofSeconds(2700))) ) {
//                                        if (locations_data.get(i).getOpeningHours().isBefore(latestClosing) ) {
//                                            eligiblePlaces.add(locations_data.get(i) );
//                                        }
//                                    }
//                                }
//                                eligiblePlaces.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                        if (o1.getClosingHours().isBefore(o2.getClosingHours())) {
//                                            return -1;
//                                        } else if (o2.getOpeningHours().isBefore(o1.getOpeningHours())) {
//                                            return 1;
//                                        } else {
//                                            return 0;
//                                        }
//                                    }
//                                });
//
//                                //add to iternary schedule
//                                iternarySchedule.add( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                //set distance from iternary schedule first place
//                                eligiblePlaces.get(eligiblePlace_pointer).setDistance_From_point( iternarySchedule.get(iternarySchedule_pointer).address );
//
//                                //duration to travel there
//                                Duration duration_to_add = Duration.ofSeconds( eligiblePlaces.get(eligiblePlace_pointer).getDuration_From_point() );
//                                //duration of time spent there
//                                duration_to_add = duration_to_add.plus( eligiblePlaces.get(eligiblePlace_pointer).getTime_spent() );
//                                //update current time.
//                                currentTime = currentTime.plus(duration_to_add);
//
//                                System.out.println("location added " + locations_data.get( locations_data_pointer).address +" " + duration_to_add + " " + currentTime );
//
//                                locations_data.remove( eligiblePlaces.get(eligiblePlace_pointer) );
//
//                                System.out.println("remove locations data " + locations_data.size());
//                                iternarySchedule_pointer += 1;
//                                eligiblePlaces.clear();
//
//                            }


//                                locations_data.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//
//
//                                        if ( (o1.getOpen_now() && !o2.getOpen_now()) || (finalCurrentTime.isAfter(o1.getOpeningHours().minus(Duration.ofSeconds(2699))) )
//                                                ||  (o1.getOpeningHours().isBefore(earliestClosing) )
//                                                 )  {
//                                            return -1;
//                                        } else if ( (!o1.getOpen_now() && o2.getOpen_now()) || (finalCurrentTime.isAfter(o2.getOpeningHours().minus(Duration.ofSeconds(2699))) )
//                                                ||  (o2.getOpeningHours().isBefore(earliestClosing) )  ) {
//                                            return 1;
//                                        } else {
//                                            return 0;
//                                        }
//                                    }
//                                });


                        //locations_data.sort(new Comparator<PlacesSchedule>() {
//                                    @Override
//                                    public int compare(PlacesSchedule o1, PlacesSchedule o2) {
//                                        boolean o1OpenSoon = finalCurrentTime.isAfter(o1.getOpeningHours().minus(Duration.ofSeconds(2700)));
//                                        boolean o2OpenSoon = finalCurrentTime.isAfter(o2.getOpeningHours().minus(Duration.ofSeconds(2700)));
//
//                                        boolean o1ClosesEarly = o1.getOpeningHours().isBefore(earliestClosing);
//                                        boolean o2ClosesEarly = o2.getOpeningHours().isBefore(earliestClosing);
//
//                                        if (o1.getOpen_now() && !o2.getOpen_now())  {
//                                            return -1;
//                                        } else if (!o1.getOpen_now() && o2.getOpen_now()) {
//                                            return 1;
//                                        } else if (o1OpenSoon && !o2OpenSoon) {
//                                            return -1;
//                                        } else if (!o1OpenSoon && o2OpenSoon) {
//                                            return 1;
//                                        } else if (o1ClosesEarly && !o2ClosesEarly) {
//                                            return -1;
//                                        } else if (!o1ClosesEarly && o2ClosesEarly) {
//                                            return 1;
//                                        } else {
//                                            // If both places fulfill conditions 1 and 2, sort them by their earliest closing time
//                                            return o1.getClosingHours().compareTo(o2.getClosingHours());
//                                        }
//                                    }
//                                });

                        //}


//                        } else {
//                            System.out.println("Break ");
//                            break;
//                        }


                        //algo to consider all possible routes =====================================================================
//                    List<List<PlacesSchedule>> combinations = getAllCombinations(locations_data);
//                    for (List<PlacesSchedule> combination : combinations) {
//                        System.out.println(combination);
                        //for (int i = 0; i < combination.size(); i++) {
                        //    if (combination.get(0).openingHours.isBefore( startTime.minus(Duration.ofSeconds(2700) ) ) )  {
                        //        combinations.remove(combination);
                        //    }
                        //}
                        //     }

//                    Iterator<List<PlacesSchedule>> iterator = combinations.iterator();
//
//                    while (iterator.hasNext()) {
//                        List<PlacesSchedule> combination = iterator.next();
//                        System.out.println(combination);
//                        boolean shouldRemove = false;
//                        LocalTime currentTimeCheck = LocalTime.parse("09:00");
//
//                        //first and second check checks first and last element to see if opening hours matches
//                        if (combination.get(0).openingHours.isBefore(startTime) && combination.get(0).openingHours.isAfter( startTime.plus(Duration.ofSeconds(3600)) ) ) {
//                            shouldRemove = true;
//                            break;
//                        }
//                        else if (combination.get(combination.size() - 1).closingHours.isAfter(endTime)) {
//                            shouldRemove = true;
//                        }
//
//                        //3rd check checks if the timing makes sense otherwise remove it.
//                        for (int i = 0; i < combination.size(); i++) {
//
//
//                            if (i == 0) {
//                                combination.get(i).setDistance_From_point(origin);
//                            } else if (i != 0) {
//                                combination.get(i).setDistance_From_point( combination.get(i - 1).address );
//                            }
//                            currentTimeCheck.plus()
//                        }
//
//                        if (shouldRemove) {
//                            iterator.remove();
//                        }
//                    }


 //                   }

//                    Adelaide SA, Australia
//                    I/System.out: iternary schedule: Barossa Valley, Tanunda SA 5352, Australia
//                    I/System.out: iternary schedule: 143 Mount Barker Rd, Stirling SA 5152, Australia
//                    I/System.out: iternary schedule: Clare SA 5453, Australia
//                    I/System.out: iternary schedule: 609 Marion Rd, South Plympton SA 5038, Australia


                    for (int i = 0; i < return_array_of_clusers.size(); i++) {
                        System.out.println(return_array_of_clusers.get(i).toString());
                        //for (int j = 0; j < return_array_of_clusers.get(i).size(); j++) {
                            //System.out.println(return_array_of_clusers.get(i).get(j).address);
                        //}

                    }



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









}



//    public ArrayList<Long> getDist_two_places(String place1, String place2) {
//
//        try {
//            GeoApiContext context = new GeoApiContext.Builder()
//                    .apiKey(BuildConfig.WEB_API_KEY)
//                    .build();
//            DirectionsResult result =
//                    DirectionsApi.newRequest(context)
//                            .mode(TravelMode.DRIVING)
//                            .units(Unit.METRIC)
//                            .origin(place1)
//                            .destination(place2)
//                            .await();
//            DirectionsRoute route = result.routes[0];
//            long durationInSecs = route.legs[0].duration.inSeconds;
//            long distanceInMeters = route.legs[0].distance.inMeters;
//            ArrayList<Long> duration_dist = new ArrayList<Long>();
//            duration_dist.add(durationInSecs);
//            duration_dist.add(distanceInMeters);
//            context.shutdown();
//            System.out.println("============ inside getDist_two_places(): " + duration_dist.size() );
//
//            return duration_dist;
//        } catch (ApiException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


//    public ArrayList<String> getPlaceInfo(String place) {
//        try {
//            GeoApiContext context = new GeoApiContext.Builder()
//                    .apiKey(BuildConfig.WEB_API_KEY)
//                    .build();
//            //GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(place.lat, place.lng)).await();
//            GeocodingResult[] results = GeocodingApi.geocode(context, place).await();
//            if (results.length > 0) {
//                String placeId = results[0].placeId;
//                String address = results[0].formattedAddress;
//                PlaceDetails placeDetails = PlacesApi.placeDetails(context, placeId).await();
//                String openingHours = placeDetails.openingHours.toString();
//                ArrayList<String> place_details = new ArrayList<>();
//                place_details.add(placeId);
//                place_details.add(address);
//                place_details.add(openingHours);
//                System.out.println(" -------------------------- inside getplaceinfo() " + place_details.size() );
//                return place_details;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



    //to calculate distance between 2 points
//    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
//        int Radius = 6371;// radius of earth in Km
//        double lat1 = StartP.latitude;
//        double lat2 = EndP.latitude;
//        double lon1 = StartP.longitude;
//        double lon2 = EndP.longitude;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;
//        DecimalFormat newFormat = new DecimalFormat("####");
//        int kmInDec = Integer.valueOf(newFormat.format(km));
//        double meter = valueResult % 1000;
//        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);
//
//        return Radius * c;
//    } //end of calculations by distance method


    //to get driving distance between 2 points
//    public String getRequestUrl (LatLng origin, LatLng destination) {
//        //value of origin
//        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
//        //value of destination
//        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
//        //value of mode
//        String mode = "mode=driving";
//        //set value enable the sensor
//        String sensor = "sensor=false";
//        //value of key
//        //String str_key = "key=AIzaSyB5626-5911-4686-9111-000000000000";
//        //value of units
//        //String str_units = "units=metric";
//        //output format
//        String output = "json";
//        //value of avoid
//        //String str_avoid = "avoid=tolls";
//        //value of language
//        //String str_language = "language=en";
//        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode;
//        String url = "https://maps.googleapis.com/maps/directions/" + output + "?" + param;
//        return url;

//        //value of departure time
//        String str_departure_time = "departure_time=now";
//        //value of arrival time
//        String str_arrival_time = "arrival_time=now";
//        //value of departure date
//        String str_departure_date = "departure_date=today";
//    } //end of get req url function


//    private String requestDirection(String reqUrl) throws IOException {
//        String responseString = "";
//        InputStream inputStream = null;
//        HttpURLConnection httpURLConnection = null;
//        try {
//            URL url = new URL(reqUrl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.connect();
//
//            //get response result
//            inputStream = httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            StringBuffer stringBuffer = new StringBuffer();
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//
//            responseString = stringBuffer.toString();
//            bufferedReader.close();
//            inputStreamReader.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            httpURLConnection.disconnect();
//        }
//        return responseString;
//    }




//    private void direction(){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        String url = Uri.parse("https://maps.googleapis.com/maps/api/directions/json")
//                .buildUpon()
//                .appendQueryParameter("destination", "-6.9218571, 107.6048254")
//                .appendQueryParameter("origin", "-6.9249233, 107.6345122")
//                .appendQueryParameter("mode", "driving")
//                .appendQueryParameter("key", "YOUR_API_KEY")
//                .toString();
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String status = response.getString("status");
//                    if (status.equals("OK")) {
//                        JSONArray routes = response.getJSONArray("routes");
//
//                        ArrayList<LatLng> points;
//
//
//                        for (int i=0;i<routes.length();i++){  //goes through routes
//                            points = new ArrayList<>();
//
//                            JSONArray legs = routes.getJSONObject(i).getJSONArray("legs");
//
//                            for (int j=0;j<legs.length();j++){ //goes thru legs
//                                JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");
//
//                                for (int k=0;k<steps.length();k++){ //goes thru steps
//                                    String polyline = steps.getJSONObject(k).getJSONObject("polyline").getString("points");
//
//
//                                    for (int l=0;l<list.size();l++){
//                                        LatLng position = new LatLng((list.get(l)).latitude, (list.get(l)).longitude);
//                                        points.add(position);
//                                    }
//                                }
//                            }
//
//                        }
//
//
//                        LatLngBounds bounds = new LatLngBounds.Builder()
//                                .include(new LatLng(-6.9249233, 107.6345122))
//                                .include(new LatLng(-6.9218571, 107.6048254)).build();
//                        Point point = new Point();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        jsonObjectRequest.setRetryPolicy(retryPolicy);
//        requestQueue.add(jsonObjectRequest);
//    }








//} //end of iternrary activity class
} //end of iternrary activity class