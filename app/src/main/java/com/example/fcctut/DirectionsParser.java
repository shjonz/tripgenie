package com.example.fcctut;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsParser {
//    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
//        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
//        JSONArray jRoutes = null;
//        JSONArray jLegs = null;
//        JSONArray jSteps = null;
//
//        try {
//            jRoutes = jObject.getJSONArray("routes");
//
//            //loop for all routes
//            for (int i = 0; i < jRoutes.length(); i++) {
//                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
//                List path = new ArrayList<HashMap<String, String>>();
//
//                //loop for all legs
//                for (int j = 0; j < jLegs.length(); j++) {
//                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
//
//                    //loop for all steps
//                    for (int k = 0; k < jSteps.length(); k++) {
//                        String polyline = "";
//
//                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
//                        List list = String.decodePolyline(polyline);
//
//                        //loop for all points
//                        for (int l = 0; l < list.size(); l++) {
//                            HashMap<String, String> hm = new HashMap<String, String>();
//                            hm.put("lat", Double.toString( (LatLng) list.get(l)).latitude);
//                            hm.put("lon", Double.toString( (LatLng) list.get(l)).longitude);
//                            path.add(hm);
//                        } //end of points loop
//                    } //end of steps loop
//                    routes.add(path);
//                } //end of legs loop
//            } //end of routes loop
//
//        } catch(JSONException e) { //end of try loop
//            e.printStackTrace();
//        } catch (Exception e) {
//
//        }
//        return routes;
//    } //end of list converted from JSON.




} //end of directionsParser class
