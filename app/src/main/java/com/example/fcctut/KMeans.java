package com.example.fcctut;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.google.android.gms.fido.fido2.api.common.Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;



public class KMeans {

    public static final Random random = new Random();

    //Background Information on objects
    //cluster: {com.example.kmeansclus.Centroid@67117f44=[com.example.kmeansclus.Record@67f89fa3, com.example.kmeansclus.Record@4ac68d3e, com.example.kmeansclus.Record@277c0f21]}
    //centroids: [com.example.kmeansclus.Centroid@6f79caec, com.example.kmeansclus.Centroid@67117f44]

    //FIT function: returns Map of key->centroid objects (each corresponds to a cluster), value-> List of Record objects
    //Record object: feature vector (e.g. Ax: distance, Ay: opening hour, Az:cost)
    public static Map<Centroid, List<Place>> fit(List<Place> place_records,
                                                  int k,
                                                  Distance distance,
                                                  int maxIterations) {

        // Get the List of Centroids of size n from the function
        List<Centroid> centroids = chooseRandomCentroids(place_records, k);
        System.out.println("centroids in fit method....."+centroids);

        // Clusters Hashmap created to map location to its cluster
        Map<Centroid, List<Place>> clusters = new HashMap<>();

        for (int i=0; i<centroids.size(); i++){
            clusters.put(centroids.get(i), new ArrayList<>());
        }


        System.out.println("BEFORE INITIALISATION: "+ clusters);
        //replace value of each centroid (key) with centroid itself
        ArrayList<Place> centroidRecords = new ArrayList<Place>();

        for (Centroid c : centroids){
            clusters.put(c, new ArrayList<>());
        }

        System.out.println("clusters after initilisation: "+ clusters);

        //Testing: Assign fixed record to each centroid
        int records_per_centroid = place_records.size() / k;
        //TODO: tackle uneven distribution
        int remainder = place_records.size()%clusters.size();
        System.out.println("Records per centroid: " + records_per_centroid);

        List<Centroid> centroids_copy = new ArrayList<>();
        centroids_copy.addAll(centroids);

        System.out.println("checking if records presemt BEFORE adding: " + clusters);
        for (Place place_record : place_records) {
            Centroid centroid = nearestCentroid(place_record, centroids_copy, distance);

            //if there are already 12/k records assigned to a centroid, stop considering that centroid
            if (clusters.get(centroid).size() == records_per_centroid){
                centroids_copy.remove(centroid);
                System.out.println(centroid + " is removed from centroid_copy");
                centroid = nearestCentroid(place_record, centroids_copy, distance);
            }

            System.out.println(place_record + " is assigned to centroid " + centroid); //
            if (centroidRecords.contains(place_record)){
                System.out.println("NOT ASSIGNED DUE TO REPEAT");
                break;
            } else {
                centroidRecords.add(place_record);
                System.out.println("added records so far: " + centroidRecords);
                assignToCluster(clusters , place_record, centroid);
                System.out.println("CLUSTER: " + clusters);
            }

        }

        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("CLUSTERS IS NOW "+ clusters);

        // Laststate is used to check from previous state of cluster
        // If the current state is the same as the last state,
        // The clusters is finalized


        Map<Centroid, List<Place>> lastState = new HashMap<>();
        //create shallow copy of clusters and assign to lastState
        lastState.putAll(clusters);

        System.out.println("INITIAL CLUSTER SIZE: "+ clusters.size());
        System.out.println("INITIAL LASTSTATE SIZE: "+ lastState.size());
        System.out.println("INITIAL CENTROIDS SIZE: "+ centroids.size());
        System.out.println("clusters:"+clusters);
        System.out.println("centroids: "+centroids);
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~START RELOCATING CENTROIDS~~~~~~~~~~~~~~~~~~~~~~~~~");

        // iterate for a pre-defined number of times
        // maxIterations for centroid to relocate until the clusters have no change

        //testbed: create deep copy of cluster so it is not changed if even distribution is ruined
        Map<Centroid, List<Place>> relocatedClusters = new HashMap<>();

        relocatedClusters.putAll(clusters);
        relocatedClusters = initializeClusters(centroids_copy);

        List<Centroid> relocatedCentroids = new ArrayList<>();

        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (Place p_record : place_records) {
                Centroid centroid = nearestCentroid(p_record, centroids, distance);
                //assign record to cluster(centroid)
                assignToCluster(relocatedClusters, p_record, centroid);
                System.out.println(clusters);
                System.out.println(clusters);
            }

            // create boolean for if the assignments/clusters do not change, then the algorithm terminates
            boolean shouldTerminate = isLastIteration || (clusters.equals(lastState) && (i!=0));

            if (shouldTerminate) {
                break;
            }

            // at the end of each iteration we should relocate the centroids, based on updated clusters hashmap
            System.out.println("cluster before relocation: " + clusters);
//            relocatedCentroids = relocateCentroids(relocatedClusters);
            centroids = relocateCentroids(clusters);
            System.out.println("cluster after relocation: " + clusters);

            System.out.println(clusters); //shdnt havr chnged
            //check if all centroids have max_records assigned to it
            int maxRecordsPerCluster = place_records.size() ;
            System.out.println(maxRecordsPerCluster);

            System.out.println("UPDATE NUMBER " + (0+i));
            clusters = relocatedClusters;
            centroids = relocatedCentroids;

            //update laststate if changed, AND if clusters is still size k, AND if (this code limits number of clusters to k)
            System.out.println("laststate: "+lastState);
            System.out.println("clusters: "+clusters);

        }
        return lastState;
    } //end of fit



    public static void addCentroid(List<Place> records, List<Centroid> centroids) {
        //Map<String, List<Double>> coordinates = new HashMap<>();  //
        EuclideanDistance euclideanDistance = new EuclideanDistance(); // initialise EuclideanDistance to caluclate distance between Centroid and Record
        Double distance;
        Double max_distance = 0.0;
        Centroid max_distance_from_curr;
        //centroid
        Map<Centroid, List<Place>> clusters = new HashMap<>();
        //Map<String, List<Double>> max_record = null;
        Place place_max_record = null;
        for (Centroid centroid : centroids) {

            for (Place place_record : records) {
                distance = euclideanDistance.calculate(place_record , centroid );
                if (distance > max_distance) {
                    max_distance = distance;
                    //max_record = place_record;
                    place_max_record = place_record;
                }
            }

            System.out.println("for" + centroid + " max distance is " + max_distance);
            //System.out.println(max_distance_from_curr);
        }

        max_distance_from_curr = new Centroid( place_max_record.getName(), place_max_record.getGeometry().getLocation().getLatitude(),
                place_max_record.getGeometry().getLocation().getLongitude() ); //new centroid with furthest coordinates from current created

        // Add the created into the Centroids List
        centroids.add(max_distance_from_curr);
    }

    public static List<Centroid> chooseRandomCentroids(List<Place> records, int k) {

        //Steps:
        //add first centroid to centroids arrayList
        //for k-1 other clusters, call addCentroid while passing in centroids list
        //for each centroid in centroid list, calculate euclidean distance between it and all records. Keep track of max distance.
        //add centroid with max_distance coordinates to centroid list
        List<Centroid> centroids = new ArrayList<>();  // Centroid List

        // Choose first Centroid randomly from the Records List
        System.out.println("choosing first random centroid: "+records.get( random.nextInt(records.size())));
        //Centroid currentCentroid = new Centroid(records.get(random.nextInt(records.size())).getFeatures());
        Centroid currentCentroid = new Centroid( records.get(random.nextInt(records.size())).getName(),
                records.get(random.nextInt(records.size())).getGeometry().getLocation().getLatitude(),
                records.get(random.nextInt(records.size())).getGeometry().getLocation().getLongitude() );

        // Add the first Centroid into the Centroid List
        centroids.add(currentCentroid);

        for (int i = 0; i < k-1; i++) {
            addCentroid(records, centroids);
        }
        System.out.println("centroids are now......"+centroids);
        return centroids;
    }


    private static Centroid nearestCentroid(Place record, List<Centroid> centroids, Distance distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;

        // Iterate all the centroid and return the centroid which is nearer to the record given
        for (Centroid centroid : centroids) {
            double currentDistance = distance.calculate(record, centroid);

//            System.out.println(currentDistance);

            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    private static void assignToCluster(Map<Centroid, List<Place>> clusters,
                                        Place p_record,
                                        Centroid centroid) {

        // Assign the given input record to the cluster
        clusters.compute(centroid, (key, list) -> {
            //if centroid has no list as value
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(p_record);

            return list;
        });
    }

    private static Map<Centroid, List<Place>> initializeClusters(List<Centroid> centroids) {
        Map<Centroid, List<Place>> clusters = new HashMap<>();
        for (Centroid centroid : centroids) {
            // Replace Collections.emptyList() with new ArrayList<>()
            clusters.put(centroid, new ArrayList<>());
        }
        return clusters;
    }



    private static Centroid average(Centroid centroid, List<Place> places) {
        if (places == null || places.isEmpty()) {
            return centroid;
        }

        double sumLatitude = 0.0;
        double sumLongitude = 0.0;

        // Calculate the sum of latitudes and longitudes of all places
        for (Place place : places) {
            sumLatitude += place.getGeometry().getLocation().getLatitude();
            sumLongitude += place.getGeometry().getLocation().getLongitude();
        }

        // Calculate the average latitude and longitude
        double avgLatitude = sumLatitude / places.size();
        double avgLongitude = sumLongitude / places.size();

        // Create a new centroid with the average latitude and longitude
        Centroid newCentroid = new Centroid("Average Centroid", avgLatitude, avgLongitude);

        return newCentroid;
    }





    private static Map<Centroid, List<Place>> redistributeClusters(Map<Centroid, List<Place>> clusters, int targetClusterSize) {
        Map<Centroid, List<Place>> redistributedClusters = new HashMap<>(clusters);
        List<Place> overflowRecords = new ArrayList<>();

        for (Map.Entry<Centroid, List<Place>> entry : redistributedClusters.entrySet()) {
            List<Place> records = entry.getValue();
            if (records.size() > targetClusterSize) {
                overflowRecords.addAll(records.subList(targetClusterSize, records.size()));
                records.subList(targetClusterSize, records.size()).clear();
            }
        }

        if (!overflowRecords.isEmpty()) {
            for (Place record : overflowRecords) {
                Centroid nearestCentroid = null;
                int minSize = Integer.MAX_VALUE;
                for (Map.Entry<Centroid, List<Place>> entry : redistributedClusters.entrySet()) {
                    List<Place> records = entry.getValue();
                    if (records.size() < targetClusterSize) {
                        if (records.size() < minSize) {
                            minSize = records.size();
                            nearestCentroid = entry.getKey();
                        }
                    }
                }
                if (nearestCentroid != null) {
                    redistributedClusters.get(nearestCentroid).add(record);
                }
            }
        }

        return redistributedClusters;
    }


    private static List<Centroid> relocateCentroids(Map<Centroid, List<Place>> clusters) {
        int cluster_size = clusters.size();
        System.out.println("initial cluster size is: "+cluster_size);
        System.out.println(clusters);
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());

    }



} //end of public class k means