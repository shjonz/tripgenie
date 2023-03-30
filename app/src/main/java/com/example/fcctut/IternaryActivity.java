package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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

    //for google org tools library
    //Loader loader = new Loader();

    //test 2 new places
    LatLng lat = new LatLng(40.75943, -73.98459);
    LatLng lat2 = new LatLng(40.64956, -73.77827);


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
                try{
                    Properties properties = new Properties();

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = RequestBody.create("", mediaType);
                    Request request = new Request.Builder()
                            .url("https://maps.googleapis.com/maps/api/directions/json?origin=Adelaide%2C%20SA&destination=Adelaide%2C%20SA&waypoints=optimize%3Atrue%7CBarossa%20Valley%2C%20SA%7CClare%2C%20SA%7CConnawarra%2C%20SA%7CMcLaren%20Vale%2C%20SA&key=${WEB_API_KEY}")
                            .method("GET", null)
                            .build();
                    Response response = client.newCall( (Request) request  ).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());


                    Log.d("iternarycall", jsonObject.toString());
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
    } //end of oncreate function


    //go to search places page
    public void AddPlacesActivity() {
        Intent intent = new Intent(this, AddPlaces.class);
        startActivity(intent);
    }



    //to calculate distance between 2 points
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    } //end of calculations by distance method


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
//
////        //value of departure time
////        String str_departure_time = "departure_time=now";
////        //value of arrival time
////        String str_arrival_time = "arrival_time=now";
////        //value of departure date
////        String str_departure_date = "departure_date=today";
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








} //end of iternrary activity class