package com.example.fcctut;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
//        clusters = centroids.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());

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

//        relocatedClusters = clusters.entrySet().stream()
//                .collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())));


        relocatedClusters.putAll(clusters);
        relocatedClusters = initializeClusters(centroids_copy);

        List<Centroid> relocatedCentroids = new ArrayList<>();
        //relocatedCentroids = List.copyOf(centroids);

//        assignToCluster(relocatedClusters, records.get(0), centroids.get(0) );

        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (Place p_record : place_records) {
                Centroid centroid = nearestCentroid(p_record, centroids, distance);
//                System.out.println("\n--------Nearest Centroid---------");
//                System.out.println(centroid.getCoordinates());
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
//            for (Centroid c : relocatedClusters.keySet()){
//                if ((clusters.get(c).size()) != maxRecordsPerCluster){
//                    System.out.println("Terminated due to to uneven distribution between clusters!");
//                    System.out.println(clusters);
//                    return clusters;
//                }
//            }

            System.out.println("UPDATE NUMBER " + (0+i));
            clusters = relocatedClusters;
            centroids = relocatedCentroids;

            //update laststate if changed, AND if clusters is still size k, AND if (this code limits number of clusters to k)
            System.out.println("laststate: "+lastState);
            System.out.println("clusters: "+clusters);
//            if (lastState.size()==k && clusters.size()==0){
//                //continue updating lastState to newly created clusters
//                System.out.println("Cluster UPDATED " + (i+1) + " time(s)");
//                lastState = clusters;
//
//            } else {
//                //return lastState without updating it with newly created clusters
//                System.out.println("Cluster stops updating to prevent reduction in number of clusters");
//                System.out.println(lastState);
////                lastState = clusters;
//
//                return clusters;
//            }
//                lastState = clusters;
        }
        return lastState;
    } //end of fit

//    public static List<Centroid> fit(List<Record> records, int k, Distance distance, int maxIterations, double tolerance) {
//        // Choose initial centroids using k-means++ algorithm
//        List<Centroid> centroids = KMeansPlusPlusInitializer.initialize(records, k, distance);
//
//        boolean converged;
//        int iteration = 0;
//
//        do {
//            Map<Centroid, List<Record>> clusters = initializeClusters(centroids);
//
//            // Assign records to the nearest centroid
//            for (Record record : records) {
//                Centroid nearest = nearestCentroid(record, centroids, distance);
//                assignToCluster(clusters, record, nearest);
//            }
//
//            List<Centroid> newCentroids = relocateCentroids(clusters);
//
//            // Check for convergence
//            converged = true;
//            for (int i = 0; i < k; i++) {
//                double dist = distance.calculate(centroids.get(i).getCoordinates(), newCentroids.get(i).getCoordinates());
//                if (dist > tolerance) {
//                    converged = false;
//                    break;
//                }
//            }
//
//            centroids = newCentroids;
//            iteration++;
//        } while (!converged && iteration < maxIterations);
//
//        return centroids;
//    }
//
//    public static class KMeansPlusPlusInitializer {
//        public static List<Centroid> initialize(List<Record> records, int k, Distance distance) {
//            List<Centroid> centroids = new ArrayList<>(k);
//            Set<Integer> chosenIndexes = new HashSet<>();
//
//            // Choose the first centroid randomly
//            int firstCentroidIndex = new Random().nextInt(records.size());
//            centroids.add(new Centroid(records.get(firstCentroidIndex).getFeatures()));
//            chosenIndexes.add(firstCentroidIndex);
//
//            while (centroids.size() < k) {
//                List<Double> distances = new ArrayList<>();
//                double totalDistance = 0.0;
//
//                // Calculate distances to the nearest centroid for each record
//                for (int i = 0; i < records.size(); i++) {
//                    if (!chosenIndexes.contains(i)) {
//                        Centroid nearest = nearestCentroid(records.get(i), centroids, distance);
//                        double dist = distance.calculate(records.get(i).getFeatures(), nearest.getCoordinates());
//                        totalDistance += dist;
//                        distances.add(dist);
//                    } else {
//                        distances.add(0.0);
//                    }
//                }
//
//                // Choose the next centroid with probability proportional to the distance
//                double r = new Random().nextDouble() * totalDistance;
//                double cumulativeDistance = 0.0;
//                for (int i = 0; i < records.size(); i++) {
//                    cumulativeDistance += distances.get(i);
//                    if (cumulativeDistance >= r) {
//                        centroids.add(new Centroid(records.get(i).getFeatures()));
//                        chosenIndexes.add(i);
//                        break;
//                    }
//                }
//            }
//            return centroids;
//        }
//    }

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

        max_distance_from_curr = new Centroid( place_max_record.getAddress(), place_max_record.getLatitude(),
                place_max_record.getLongitude() ); //new centroid with furthest coordinates from current created

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
        Centroid currentCentroid = new Centroid( records.get(random.nextInt(records.size())).getAddress(),
                records.get(random.nextInt(records.size())).getLatitude(),
                records.get(random.nextInt(records.size())).getLongitude() );

        // Add the first Centroid into the Centroid List
        centroids.add(currentCentroid);

        for (int i = 0; i < k-1; i++) {
            addCentroid(records, centroids);
        }
        System.out.println("centroids are now......"+centroids);
        return centroids;
    }

//    public static List<Centroid> chooseRandomCentroids(List<Record> records, int k) {
//        List<Centroid> centroids = new ArrayList<>();  // Centroid List
//
//        for (int i=0; i<k; i++){
//            addCentroid();
//        }

//        Map<String, List<Double>> coordinates = new HashMap<>();  //
//        EuclideanDistance euclideanDistance = new EuclideanDistance(); // initialise EuclideanDistance to caluclate distance between Centroid and Record
//        Double distance;
//        Double max_distance = 0.0;
//        Centroid max_distance_from_curr = new Centroid(coordinates);
//        Map<Centroid, List<Record>> clusters = new HashMap<>();
//
//        // Choose first Centroid randomly from the Records List
//        System.out.println(records.get(random.nextInt(records.size())));
//        Centroid currentCentroid = new Centroid(records.get(random.nextInt(records.size())).getFeatures());
//
//
//        // Add the first Centroid into the Centroid List
//        centroids.add(currentCentroid);
//
//        // TODO: Solve the outlier issue (Everland too far away)
//        // Iterate k times (Number of Cluster) to get k clusters
//        for (int i = 0; i < k; i++) {
//            // For each record, Find a centroid from the centroid List nearest to it and assign them to its cluster
//            for (Record record: records) { //all records per centroid(??)
//                Centroid centroid = nearestCentroid(record, centroids, new EuclideanDistance());
//                assignToCluster(clusters, record, centroid); //figure out how
//            }
//
//            // Since we use a record as one of the centroid
//            // We find the next centroid from the 1st cluster of records
//            // the record that is the furthest away from the current centroid will be the next centroid
//            for (Record record: clusters.get(currentCentroid)) { //calculate distance of each record from 1st centroid, replace max_distance if greater
//                distance = euclideanDistance.calculate(record.getFeatures(), currentCentroid.getCoordinates());
//                if (distance > max_distance) {
//                    max_distance = distance;
//                    max_distance_from_curr = new Centroid(record.getFeatures());
//                }
//            }
//
//            // Add the created into the Centroids List
//            centroids.add(max_distance_from_curr);
//
//            // The newly created Centroid will be the next current centroid
//            // to find the next centroid
//            currentCentroid = max_distance_from_curr;
//
//            // Re-Initialise clusters to empty it for the next iteration  (why?)
////            clusters = new HashMap<>();
//        }

//        return centroids;  // Return the List of Centroids.
//    }

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

//    private static Centroid average(Centroid centroid, List<Place> records) {
//        if (records == null || records.isEmpty()) {
//            return centroid;
//        }
//
//        Map<String, List<Double>> average = new HashMap<String, List<Double>>();
//        List<Double> coords = new ArrayList<Double>();
//        coords.add(centroid.getLatitude());
//        coords.add(centroid.getLongitude());
//        average.put( centroid.getAddressName(), coords );
//        List<Double> avg_coord = new ArrayList<>();
//        avg_coord.add(0.0);
//        avg_coord.add(0.0);
//        //create map called "average" of each feature key (e.g distance) to have (0,0) tuple as value
//        records.stream().flatMap(e -> e.getFeatures().keySet().stream())
//                //replace each key-vale pair ??
//                .forEach(k -> average.put(k, avg_coord));
//
//        for (Place record : records) { // Compute average distance between Centroid and all of the location in the clusters, assign to distance key in "average" map
//            record.getFeatures().forEach(
//                    (k, v) -> {
//                        //add sum of lat, lng to average hashmap
//                        average.compute(k, (k1, currentValue) -> {
//                            List<Double> avg_coord2 = new ArrayList<>();
//                            Double lat = v.get(0) + currentValue.get(0);
//                            Double longitude = v.get(1) + currentValue.get(1);
//                            avg_coord2.add(lat);
//                            avg_coord2.add(longitude);
//                            return avg_coord2;
//                        });
//                    });
//        }
//
//        //calculate average values for all keys, then use for loop to replace values in average hashmap
//        average.forEach((k, v) -> {
//            avg_coord.clear();
//            Double lat = v.get(0);
//            Double longitude = v.get(1);
//            avg_coord.add(lat / records.size());
//            avg_coord.add(longitude / records.size());
//            //update average hashmap with avg_coord list
//            //DOUBT: why list not tuple
//            average.put(k, avg_coord);
//        });
//
//        //return Centroid average in all features
//        return new Centroid(average);
//    }



    private static Centroid average(Centroid centroid, List<Place> places) {
        if (places == null || places.isEmpty()) {
            return centroid;
        }

        double sumLatitude = 0.0;
        double sumLongitude = 0.0;

        // Calculate the sum of latitudes and longitudes of all places
        for (Place place : places) {
            sumLatitude += place.getLatitude();
            sumLongitude += place.getLongitude();
        }

        // Calculate the average latitude and longitude
        double avgLatitude = sumLatitude / places.size();
        double avgLongitude = sumLongitude / places.size();

        // Create a new centroid with the average latitude and longitude
        Centroid newCentroid = new Centroid("Average Centroid", avgLatitude, avgLongitude);

        return newCentroid;
    }





    private static Map<Centroid, List<Record>> redistributeClusters(Map<Centroid, List<Record>> clusters, int targetClusterSize) {
        Map<Centroid, List<Record>> redistributedClusters = new HashMap<>(clusters);
        List<Record> overflowRecords = new ArrayList<>();

        for (Map.Entry<Centroid, List<Record>> entry : redistributedClusters.entrySet()) {
            List<Record> records = entry.getValue();
            if (records.size() > targetClusterSize) {
                overflowRecords.addAll(records.subList(targetClusterSize, records.size()));
                records.subList(targetClusterSize, records.size()).clear();
            }
        }

        if (!overflowRecords.isEmpty()) {
            for (Record record : overflowRecords) {
                Centroid nearestCentroid = null;
                int minSize = Integer.MAX_VALUE;
                for (Map.Entry<Centroid, List<Record>> entry : redistributedClusters.entrySet()) {
                    List<Record> records = entry.getValue();
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

    //varun
//    private static Centroid average(Centroid centroid, List<Record> records) {
//        if (records == null || records.isEmpty()) {
//            return centroid;
//        }
//
//        Map<String, List<Double>> average = new HashMap<>(centroid.getCoordinates());
//
//        // Set the values to (0,0) lists
//        for (String key : average.keySet()) {
//            average.put(key, Arrays.asList(0.0, 0.0));
//        }
//
//        for (Record record : records) {
//            record.getFeatures().forEach(
//                    (k, v) -> {
//                        average.compute(k, (k1, currentValue) -> {
//                            List<Double> avg_coord2 = new ArrayList<>();
//                            Double lat = v.get(0) + currentValue.get(0);
//                            Double longitude = v.get(1) + currentValue.get(1);
//                            avg_coord2.add(lat);
//                            avg_coord2.add(longitude);
//                            return avg_coord2;
//                        });
//                    });
//        }
//
//        average.forEach((k, v) -> {
//            List<Double> avg_coord = new ArrayList<>();
//            Double lat = v.get(0) / records.size();
//            Double longitude = v.get(1) / records.size();
//            avg_coord.add(lat);
//            avg_coord.add(longitude);
//            average.put(k, avg_coord);
//        });
//
//        return new Centroid(average);
//    }
    private static List<Centroid> relocateCentroids(Map<Centroid, List<Place>> clusters) {
        int cluster_size = clusters.size();
        System.out.println("initial cluster size is: "+cluster_size);
        System.out.println(clusters);
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());

    }

    public static void main(String[] args) {
//        System.out.println("---------------Test GetFeatures---------------");
//        List<Place> records = new ArrayList<>();
//
//        Place place = new Place("263 Jangchungdan-ro, Jung-gu, Seoul, South Korea");
//        place.setLatitude();
//
//
//
//        //Start adding Records
//        List<Double> coord1 = new ArrayList<>();
//        coord1.add(37.56819053958451);
//        coord1.add(127.00847759788303);
//        //location1 is a point with supposedly multiple features
//        Map<String, List<Double>> location1 = new HashMap<>();
//        //add distance feature
//        location1.put("Location", coord1);
//        //assign location1 as location of Record object address1
//        Record address1 = new Record("263 Jangchungdan-ro, Jung-gu, Seoul, South Korea", location1);
////        System.out.println(address1
////                .getFeatures());
//
//        List<Double> coord2 = new ArrayList<>();
//        coord2.add(37.57020985495065);
//        coord2.add(126.99959286887606);
//        Map<String, List<Double>> location2 = new HashMap<>();
//        location2.put("Location", coord2);
//        Record address2 = new Record("88 Changgyeonggung-ro, Jongno-gu, Seoul, South Korea", location2);
//
//        List<Double> coord3 = new ArrayList<>();
//        coord3.add(37.57982948171247);
//        coord3.add(126.97709462440277);
//        Map<String, List<Double>> location3 = new HashMap<>();
//        location3.put("Location", coord3);
//        Record address3 = new Record("161 Sajik-ro, Jongno-gu, Seoul, South Korea", location3);
//
//        List<Double> coord4 = new ArrayList<>();
//        coord4.add(37.573806731717866);
//        coord4.add(126.99016465032621);
//        Map<String, List<Double>> location4 = new HashMap<>();
//        location4.put("Location", coord4);
//        Record address4 = new Record("28 Samil-daero 30-gil, Ikseon-dong, Jongno-gu, Seoul, South Korea", location4);
//
//        List<Double> coord5 = new ArrayList<>();
//        coord5.add(37.57793303286747);
//        coord5.add(126.98647139635473);
//        Map<String, List<Double>> location5 = new HashMap<>();
//        location5.put("Location", coord5);
//        Record address5 = new Record("8 Achasan-ro 9-gil, Seongsu-dong 2(i)-ga, Seongdong-gu, Seoul, South Korea", location5);
//
//        List<Double> coord6 = new ArrayList<>();
//        coord6.add(37.55130547441948);
//        coord6.add(126.98820514003933);
//        Map<String, List<Double>> location6 = new HashMap<>();
//        location6.put("Location", coord6);
//        Record address6 = new Record("105 Namsangongwon-gil, Yongsan-gu, Seoul, South Korea", location6);
//
//        List<Double> coord7 = new ArrayList<>();
//        coord7.add(37.56214305462524);
//        coord7.add(126.92501691305641);
//        Map<String, List<Double>> location7 = new HashMap<>();
//        location7.put("Location", coord7);
//        Record address7 = new Record("South Korea, Seoul, Mapo-gu, 연남동 390-71", location7);
//
//        List<Double> coord8 = new ArrayList<>();
//        coord8.add(37.29391);
//        coord8.add(127.20256);
////        coord8.add(37.56314305462524);
////        coord8.add(126.93501691305641);
//        Map<String, List<Double>> location8 = new HashMap<>();
//        location8.put("Location", coord8);
//        Record address8 = new Record("199 Everland-ro, Pogok-eup, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea", location8);
//
//        List<Double> coord9 = new ArrayList<>();
//        coord9.add(37.52403);
//        coord9.add(127.02343);
//        Map<String, List<Double>> location9 = new HashMap<>();
//        location9.put("Location", coord9);
//        Record address9 = new Record("68 Nonhyeon-ro 175-gil, Gangnam-gu, Seoul, South Korea", location9);
//
//        List<Double> coord10 = new ArrayList<>();
//        coord10.add(37.57962);
//        coord10.add(126.97096);
//        Map<String, List<Double>> location10 = new HashMap<>();
//        location10.put("Location", coord10);
//        Record address10 = new Record("4 Jahamun-ro 11-gil, Jongno-gu, Seoul, South Korea", location10);
//
//        List<Double> coord11 = new ArrayList<>();
//        coord11.add(37.52385);
//        coord11.add(126.98047);
//        Map<String, List<Double>> location11 = new HashMap<>();
//        location11.put("Location", coord11);
//        Record address11 = new Record("137 Seobinggo-ro, Yongsan-gu, 서울특별시 South Korea", location11);
//
//        List<Double> coord12 = new ArrayList<>();
//        coord12.add(37.571);
//        coord12.add(126.97694);
//        Map<String, List<Double>> location12 = new HashMap<>();
//        location12.put("Location", coord12);
//        Record address12 = new Record("172 Sejong-daero, Sejongno, Jongno-gu, Seoul, South Korea", location12);
//
////        List<Double> coord13 = new ArrayList<>();
////        coord12.add(37.56775);
////        coord12.add(126.88568);
////        Map<String, List<Double>> location13 = new HashMap<>();
////        location13.put("Location", coord13);
////        Record address13 = new Record("13 Hanul Park", location12);
//        records.add(address1);
//        records.add(address2);
//        records.add(address3);
//        records.add(address4);
//        records.add(address5);
//        records.add(address6);
//        records.add(address7);
//        records.add(address8);
//        records.add(address9);
//        records.add(address10);
//        records.add(address11);
//        records.add(address12);
////        records.add(address13);
//
//        System.out.println("~~~~~~~~~~~~~~~~Printing each record~~~~~~~~~~~~~~~");
//        System.out.println(records.get(0).getAddress());
//        System.out.println(records.get(0).getFeatures());
//        System.out.println(records.get(1).getAddress());
//        System.out.println(records.get(1).getFeatures());
//        System.out.println(records.get(2).getAddress());
//        System.out.println(records.get(2).getFeatures());
//        System.out.println(records.get(3).getAddress());
//        System.out.println(records.get(3).getFeatures());
//        System.out.println(records.get(4).getAddress());
//        System.out.println(records.get(4).getFeatures());
//        System.out.println(records.get(5).getAddress());
//        System.out.println(records.get(5).getFeatures());
//        System.out.println(records.get(6).getAddress());
//        System.out.println(records.get(6).getFeatures());
//        System.out.println(records.get(7).getAddress());
//        System.out.println(records.get(7).getFeatures());
//        System.out.println(records.get(8).getAddress());
//        System.out.println(records.get(8).getFeatures());
//        System.out.println(records.get(9).getAddress());
//        System.out.println(records.get(9).getFeatures());
//        System.out.println(records.get(10).getFeatures());
//        System.out.println(records.get(10).getFeatures());
//        System.out.println(records.get(11).getFeatures());
//        System.out.println(records.get(11).getFeatures());
////        System.out.println(records.get(12).getFeatures());
////        System.out.println(records.get(12).getFeatures());
//        System.out.println("~~~~~~~~~~~~~~~~End of Printing each record~~~~~~~~~~~~~~~");
//
//        System.out.println("\n");
//        System.out.println("\n");
//
//
//        Map<Centroid, List<Record>> clusters = KMeans.fit(records, 4, new EuclideanDistance(), 10);
//        int targetClusterSize = records.size() / 4; // Replace 4 with the desired number of clusters
//        Map<Centroid, List<Record>> redistributedClusters = redistributeClusters(clusters, targetClusterSize);
//        System.out.println("\n");
//        System.out.println("\n");
//
//        System.out.println(clusters);
//        // Printing the cluster configuration
//        clusters.forEach((key, value) -> {
//            System.out.println("-------------------------- CLUSTER ----------------------------");
//
//            // Sorting the coordinates to see the most significant tags first.
//            System.out.println(key);
////            Record recordtoremove = null;
////            ArrayList latlng = new ArrayList<>();
////            latlng = (ArrayList) key.getCoordinates().get("Location");
////            for (Record record : records){
////                if (record.location == latlng){
////                    recordtoremove = record;
////                }
////            }
////
////            System.out.println(recordtoremove);
//            System.out.println(value);
//            String members = String.join(", ", value.stream().map(Record::getAddress).collect(toSet()));
//            System.out.print("members: "+ members + "\n");
//
//
//        });
//
//        System.out.println("\n");
//        System.out.println("\n");
//
//        System.out.println("-------------------clusters overall information---------------: \n"+ clusters.keySet());
//        System.out.println("number of clusters: "+clusters.size());
//        Centroid cluster1 = clusters.keySet().iterator().next();
//        Centroid cluster2 = clusters.keySet().iterator().next();
//        Centroid cluster3 = clusters.keySet().iterator().next();
//        Centroid cluster4 = clusters.keySet().iterator().next();
//        System.out.println(cluster1); //get centroid object
//        System.out.println(clusters.get(cluster1)); //get list of record addresses
//        System.out.println(clusters.keySet().iterator().next());
//
//        Set<Centroid> clusterKeySet = clusters.keySet();
//        //convert set to list
//        List<Centroid> clustersKeys = new ArrayList<>(clusterKeySet);
//        Centroid firstCentroid = clustersKeys.get(0);
//        Centroid secondCentroid = clustersKeys.get(0);
//        Centroid thirdCentroid = clustersKeys.get(0);
//        Centroid fourthCentroid = clustersKeys.get(0);
//
//        System.out.println(clusters.get(firstCentroid));
//        System.out.println(clusters.get(secondCentroid));
//        System.out.println(clusters.get(thirdCentroid));
//        System.out.println(clusters.get(fourthCentroid));



//        System.out.println("---------------Test RandomCentroids---------------");
//        List<Centroid> centroids = chooseRandomCentroids(records, 3);
//
//        System.out.println(centroids.get(0).getCoordinates());
//        System.out.println(centroids.get(1).getCoordinates());
//        System.out.println(centroids.get(2).getCoordinates());


//
//        System.out.println("---------------Test NearestCentroid---------------");
//        EuclideanDistance distance = new EuclideanDistance();
//
//        Centroid nearest = nearestCentroid(records.get(0), centroids, distance);
//
//        System.out.println(nearest.getCoordinates());
//
//
//        System.out.println("---------------Test AssignToCluster---------------");
//
//
//        System.out.println("---------------Test AverageCentroid---------------");
//
//
//        System.out.println("---------------Test RelocateCentroid---------------");
//
//
//        System.out.println("---------------Test Fit---------------");
    }

}
















//
//public class KMeans {
//
//    public static final Random random = new Random();
//
//    //Background Information on objects
//    //cluster: {com.example.kmeansclus.Centroid@67117f44=[com.example.kmeansclus.Record@67f89fa3, com.example.kmeansclus.Record@4ac68d3e, com.example.kmeansclus.Record@277c0f21]}
//    //centroids: [com.example.kmeansclus.Centroid@6f79caec, com.example.kmeansclus.Centroid@67117f44]
//
//    //FIT function: returns Map of key->centroid objects (each corresponds to a cluster), value-> List of Record objects
//    //Record object: feature vector (e.g. Ax: distance, Ay: opening hour, Az:cost)
//    public static Map<Centroid, List<Record>> fit(List<Record> records,
//                                                  int k,
//                                                  Distance distance,
//                                                  int maxIterations) {
//
//        // Get the List of Centroids of size n from the function
//        List<Centroid> centroids = chooseRandomCentroids(records, k);
//        System.out.println("centroids in fit method....."+centroids);
//
//        // Clusters Hashmap created to map location to its cluster
//        Map<Centroid, List<Record>> clusters = new HashMap<>();
//
//        for (int i=0; i<centroids.size(); i++){
//            clusters.put(centroids.get(i), new ArrayList<>());
//        }
//
//
//        System.out.println("BEFORE INITIALISATION: "+ clusters);
//        //replace value of each centroid (key) with centroid itself
//        ArrayList<Record> centroidRecords = new ArrayList<Record>();
//
//        for (Centroid c : centroids){
//            clusters.put(c, new ArrayList<>());
//        }
//
//        System.out.println("clusters after initilisation: "+ clusters);
//
//        //Testing: Assign fixed record to each centroid
//        int records_per_centroid = records.size() / k;
//        //TODO: tackle uneven distribution
//        int remainder = records.size()%clusters.size();
//        System.out.println("Records per centroid: " + records_per_centroid);
//
//        List<Centroid> centroids_copy = new ArrayList<>();
//        centroids_copy.addAll(centroids);
//
//        System.out.println("checking if records presemt BEFORE adding: " + clusters);
//        for (Record record : records) {
//            Centroid centroid = nearestCentroid(record, centroids_copy, distance);
//
//            //if there are already 12/k records assigned to a centroid, stop considering that centroid
//            if (clusters.get(centroid).size() == records_per_centroid){
//                centroids_copy.remove(centroid);
//                System.out.println(centroid + " is removed from centroid_copy");
//                centroid = nearestCentroid(record, centroids_copy, distance);
//            }
//
//            System.out.println(record + " is assigned to centroid " + centroid); //
//            if (centroidRecords.contains(record)){
//                System.out.println("NOT ASSIGNED DUE TO REPEAT");
//                break;
//            } else {
//                centroidRecords.add(record);
//                System.out.println("added records so far: " + centroidRecords);
//                assignToCluster(clusters, record, centroid);
//                System.out.println("CLUSTER: " + clusters);
//            }
//
//        }
//
//        System.out.println("\n");
//        System.out.println("\n");
//        System.out.println("\n");
//        System.out.println("CLUSTERS IS NOW "+ clusters);
////        clusters = centroids.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());
//
//        // Laststate is used to check from previous state of cluster
//        // If the current state is the same as the last state,
//        // The clusters is finalized
//
//
//        Map<Centroid, List<Record>> lastState = new HashMap<>();
//        //create shallow copy of clusters and assign to lastState
//        lastState.putAll(clusters);
//
//        System.out.println("INITIAL CLUSTER SIZE: "+ clusters.size());
//        System.out.println("INITIAL LASTSTATE SIZE: "+ lastState.size());
//        System.out.println("INITIAL CENTROIDS SIZE: "+ centroids.size());
//        System.out.println("clusters:"+clusters);
//        System.out.println("centroids: "+centroids);
//        System.out.println("\n");
//        System.out.println("\n");
//        System.out.println("\n");
//
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~START RELOCATING CENTROIDS~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        // iterate for a pre-defined number of times
//        // maxIterations for centroid to relocate until the clusters have no change
//
//        //testbed: create deep copy of cluster so it is not changed if even distribution is ruined
//        Map<Centroid, List<Record>> relocatedClusters = new HashMap<>();
//
////        relocatedClusters = clusters.entrySet().stream()
////                .collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())));
//
//
//        relocatedClusters.putAll(clusters);
//        relocatedClusters = initializeClusters(centroids_copy);
//
//        List<Centroid> relocatedCentroids = new ArrayList<>();
//        relocatedCentroids = List.copyOf(centroids);
//
////        assignToCluster(relocatedClusters, records.get(0), centroids.get(0) );
//
//        for (int i = 0; i < maxIterations; i++) {
//            boolean isLastIteration = i == maxIterations - 1;
//
//            // in each iteration we should find the nearest centroid for each record
//            for (Record record : records) {
//                Centroid centroid = nearestCentroid(record, centroids, distance);
////                System.out.println("\n--------Nearest Centroid---------");
////                System.out.println(centroid.getCoordinates());
//                //assign record to cluster(centroid)
//                assignToCluster(relocatedClusters, record, centroid);
//                System.out.println(clusters);
//                System.out.println(clusters);
//            }
//
//            // create boolean for if the assignments/clusters do not change, then the algorithm terminates
//            boolean shouldTerminate = isLastIteration || (clusters.equals(lastState) && (i!=0));
//
//            if (shouldTerminate) {
//                break;
//            }
//
//            // at the end of each iteration we should relocate the centroids, based on updated clusters hashmap
//            System.out.println("cluster before relocation: " + clusters);
////            relocatedCentroids = relocateCentroids(relocatedClusters);
//            centroids = relocateCentroids(clusters);
//            System.out.println("cluster after relocation: " + clusters);
//
//            System.out.println(clusters); //shdnt havr chnged
//            //check if all centroids have max_records assigned to it
//            int maxRecordsPerCluster = records.size() ;
//            System.out.println(maxRecordsPerCluster);
////            for (Centroid c : relocatedClusters.keySet()){
////                if ((clusters.get(c).size()) != maxRecordsPerCluster){
////                    System.out.println("Terminated due to to uneven distribution between clusters!");
////                    System.out.println(clusters);
////                    return clusters;
////                }
////            }
//
//            System.out.println("UPDATE NUMBER " + (0+i));
//            clusters = relocatedClusters;
//            centroids = relocatedCentroids;
//
//            //update laststate if changed, AND if clusters is still size k, AND if (this code limits number of clusters to k)
//            System.out.println("laststate: "+lastState);
//            System.out.println("clusters: "+clusters);
////            if (lastState.size()==k && clusters.size()==0){
////                //continue updating lastState to newly created clusters
////                System.out.println("Cluster UPDATED " + (i+1) + " time(s)");
////                lastState = clusters;
////
////            } else {
////                //return lastState without updating it with newly created clusters
////                System.out.println("Cluster stops updating to prevent reduction in number of clusters");
////                System.out.println(lastState);
//////                lastState = clusters;
////
////                return clusters;
////            }
////                lastState = clusters;
//        }
//        return lastState;
//    } //end of fit
//
////    public static List<Centroid> fit(List<Record> records, int k, Distance distance, int maxIterations, double tolerance) {
////        // Choose initial centroids using k-means++ algorithm
////        List<Centroid> centroids = KMeansPlusPlusInitializer.initialize(records, k, distance);
////
////        boolean converged;
////        int iteration = 0;
////
////        do {
////            Map<Centroid, List<Record>> clusters = initializeClusters(centroids);
////
////            // Assign records to the nearest centroid
////            for (Record record : records) {
////                Centroid nearest = nearestCentroid(record, centroids, distance);
////                assignToCluster(clusters, record, nearest);
////            }
////
////            List<Centroid> newCentroids = relocateCentroids(clusters);
////
////            // Check for convergence
////            converged = true;
////            for (int i = 0; i < k; i++) {
////                double dist = distance.calculate(centroids.get(i).getCoordinates(), newCentroids.get(i).getCoordinates());
////                if (dist > tolerance) {
////                    converged = false;
////                    break;
////                }
////            }
////
////            centroids = newCentroids;
////            iteration++;
////        } while (!converged && iteration < maxIterations);
////
////        return centroids;
////    }
////
////    public static class KMeansPlusPlusInitializer {
////        public static List<Centroid> initialize(List<Record> records, int k, Distance distance) {
////            List<Centroid> centroids = new ArrayList<>(k);
////            Set<Integer> chosenIndexes = new HashSet<>();
////
////            // Choose the first centroid randomly
////            int firstCentroidIndex = new Random().nextInt(records.size());
////            centroids.add(new Centroid(records.get(firstCentroidIndex).getFeatures()));
////            chosenIndexes.add(firstCentroidIndex);
////
////            while (centroids.size() < k) {
////                List<Double> distances = new ArrayList<>();
////                double totalDistance = 0.0;
////
////                // Calculate distances to the nearest centroid for each record
////                for (int i = 0; i < records.size(); i++) {
////                    if (!chosenIndexes.contains(i)) {
////                        Centroid nearest = nearestCentroid(records.get(i), centroids, distance);
////                        double dist = distance.calculate(records.get(i).getFeatures(), nearest.getCoordinates());
////                        totalDistance += dist;
////                        distances.add(dist);
////                    } else {
////                        distances.add(0.0);
////                    }
////                }
////
////                // Choose the next centroid with probability proportional to the distance
////                double r = new Random().nextDouble() * totalDistance;
////                double cumulativeDistance = 0.0;
////                for (int i = 0; i < records.size(); i++) {
////                    cumulativeDistance += distances.get(i);
////                    if (cumulativeDistance >= r) {
////                        centroids.add(new Centroid(records.get(i).getFeatures()));
////                        chosenIndexes.add(i);
////                        break;
////                    }
////                }
////            }
////            return centroids;
////        }
////    }
//
//    public static void addCentroid(List<Record> records, List<Centroid> centroids) {
//        Map<String, List<Double>> coordinates = new HashMap<>();  //
//        EuclideanDistance euclideanDistance = new EuclideanDistance(); // initialise EuclideanDistance to caluclate distance between Centroid and Record
//        Double distance;
//        Double max_distance = 0.0;
//        Centroid max_distance_from_curr = new Centroid(coordinates);
//        Map<Centroid, List<Record>> clusters = new HashMap<>();
//        Map<String, List<Double>> max_record = null;
//        for (Centroid centroid : centroids) {
//
//            for (Record record : records) {
//                distance = euclideanDistance.calculate(record.getFeatures(), centroid.getCoordinates());
//                if (distance > max_distance) {
//                    max_distance = distance;
//                    max_record = record.getFeatures();
//                }
//            }
//
//            System.out.println("for" + centroid + " max distance is " + max_distance);
//            System.out.println(max_distance_from_curr);
//        }
//
//        max_distance_from_curr = new Centroid(max_record); //new centroid with furthest coordinates from current created
//
//        // Add the created into the Centroids List
//        centroids.add(max_distance_from_curr);
//    }
//
//    public static List<Centroid> chooseRandomCentroids(List<Record> records, int k) {
//
//        //Steps:
//        //add first centroid to centroids arrayList
//        //for k-1 other clusters, call addCentroid while passing in centroids list
//        //for each centroid in centroid list, calculate euclidean distance between it and all records. Keep track of max distance.
//        //add centroid with max_distance coordinates to centroid list
//        List<Centroid> centroids = new ArrayList<>();  // Centroid List
//
//        // Choose first Centroid randomly from the Records List
//        System.out.println(records.get(random.nextInt(records.size())));
//        Centroid currentCentroid = new Centroid(records.get(random.nextInt(records.size())).getFeatures());
//
//        // Add the first Centroid into the Centroid List
//        centroids.add(currentCentroid);
//
//        for (int i = 0; i < k-1; i++) {
//            addCentroid(records, centroids);
//        }
//        System.out.println("centroids are now......"+centroids);
//        return centroids;
//    }
//
////    public static List<Centroid> chooseRandomCentroids(List<Record> records, int k) {
////        List<Centroid> centroids = new ArrayList<>();  // Centroid List
////
////        for (int i=0; i<k; i++){
////            addCentroid();
////        }
//
////        Map<String, List<Double>> coordinates = new HashMap<>();  //
////        EuclideanDistance euclideanDistance = new EuclideanDistance(); // initialise EuclideanDistance to caluclate distance between Centroid and Record
////        Double distance;
////        Double max_distance = 0.0;
////        Centroid max_distance_from_curr = new Centroid(coordinates);
////        Map<Centroid, List<Record>> clusters = new HashMap<>();
////
////        // Choose first Centroid randomly from the Records List
////        System.out.println(records.get(random.nextInt(records.size())));
////        Centroid currentCentroid = new Centroid(records.get(random.nextInt(records.size())).getFeatures());
////
////
////        // Add the first Centroid into the Centroid List
////        centroids.add(currentCentroid);
////
////        // TODO: Solve the outlier issue (Everland too far away)
////        // Iterate k times (Number of Cluster) to get k clusters
////        for (int i = 0; i < k; i++) {
////            // For each record, Find a centroid from the centroid List nearest to it and assign them to its cluster
////            for (Record record: records) { //all records per centroid(??)
////                Centroid centroid = nearestCentroid(record, centroids, new EuclideanDistance());
////                assignToCluster(clusters, record, centroid); //figure out how
////            }
////
////            // Since we use a record as one of the centroid
////            // We find the next centroid from the 1st cluster of records
////            // the record that is the furthest away from the current centroid will be the next centroid
////            for (Record record: clusters.get(currentCentroid)) { //calculate distance of each record from 1st centroid, replace max_distance if greater
////                distance = euclideanDistance.calculate(record.getFeatures(), currentCentroid.getCoordinates());
////                if (distance > max_distance) {
////                    max_distance = distance;
////                    max_distance_from_curr = new Centroid(record.getFeatures());
////                }
////            }
////
////            // Add the created into the Centroids List
////            centroids.add(max_distance_from_curr);
////
////            // The newly created Centroid will be the next current centroid
////            // to find the next centroid
////            currentCentroid = max_distance_from_curr;
////
////            // Re-Initialise clusters to empty it for the next iteration  (why?)
//////            clusters = new HashMap<>();
////        }
//
////        return centroids;  // Return the List of Centroids.
////    }
//
//    private static Centroid nearestCentroid(Record record, List<Centroid> centroids, Distance distance) {
//        double minimumDistance = Double.MAX_VALUE;
//        Centroid nearest = null;
//
//        // Iterate all the centroid and return the centroid which is nearer to the record given
//        for (Centroid centroid : centroids) {
//            double currentDistance = distance.calculate(record.getFeatures(), centroid.getCoordinates());
//
////            System.out.println(currentDistance);
//
//            if (currentDistance < minimumDistance) {
//                minimumDistance = currentDistance;
//                nearest = centroid;
//            }
//        }
//
//        return nearest;
//    }
//
//    private static void assignToCluster(Map<Centroid, List<Record>> clusters,
//                                        Record record,
//                                        Centroid centroid) {
//
//        // Assign the given input record to the cluster
//        clusters.compute(centroid, (key, list) -> {
//            //if centroid has no list as value
//            if (list == null) {
//                list = new ArrayList<>();
//            }
//
//            list.add(record);
//
//            return list;
//        });
//    }
//
//    private static Map<Centroid, List<Record>> initializeClusters(List<Centroid> centroids) {
//        Map<Centroid, List<Record>> clusters = new HashMap<>();
//        for (Centroid centroid : centroids) {
//            // Replace Collections.emptyList() with new ArrayList<>()
//            clusters.put(centroid, new ArrayList<>());
//        }
//        return clusters;
//    }
//
//    private static Centroid average(Centroid centroid, List<Record> records) {
//        if (records == null || records.isEmpty()) {
//            return centroid;
//        }
//
//        Map<String, List<Double>> average = centroid.getCoordinates();
//        List<Double> avg_coord = new ArrayList<>();
//        avg_coord.add(0.0);
//        avg_coord.add(0.0);
//        //create map called "average" of each feature key (e.g distance) to have (0,0) tuple as value
//        records.stream().flatMap(e -> e.getFeatures().keySet().stream())
//                //replace each key-vale pair ??
//                .forEach(k -> average.put(k, avg_coord));
//
//        for (Record record : records) { // Compute average distance between Centroid and all of the location in the clusters, assign to distance key in "average" map
//            record.getFeatures().forEach(
//                    (k, v) -> {
//                        //add sum of lat, lng to average hashmap
//                        average.compute(k, (k1, currentValue) -> {
//                            List<Double> avg_coord2 = new ArrayList<>();
//                            Double lat = v.get(0) + currentValue.get(0);
//                            Double longitude = v.get(1) + currentValue.get(1);
//                            avg_coord2.add(lat);
//                            avg_coord2.add(longitude);
//                            return avg_coord2;
//                        });
//                    });
//        }
//
//        //calculate average values for all keys, then use for loop to replace values in average hashmap
//        average.forEach((k, v) -> {
//            avg_coord.clear();
//            Double lat = v.get(0);
//            Double longitude = v.get(1);
//            avg_coord.add(lat / records.size());
//            avg_coord.add(longitude / records.size());
//            //update average hashmap with avg_coord list
//            //DOUBT: why list not tuple
//            average.put(k, avg_coord);
//        });
//
//        //return Centroid average in all features
//        return new Centroid(average);
//    }
//
//    private static Map<Centroid, List<Record>> redistributeClusters(Map<Centroid, List<Record>> clusters, int targetClusterSize) {
//        Map<Centroid, List<Record>> redistributedClusters = new HashMap<>(clusters);
//        List<Record> overflowRecords = new ArrayList<>();
//
//        for (Map.Entry<Centroid, List<Record>> entry : redistributedClusters.entrySet()) {
//            List<Record> records = entry.getValue();
//            if (records.size() > targetClusterSize) {
//                overflowRecords.addAll(records.subList(targetClusterSize, records.size()));
//                records.subList(targetClusterSize, records.size()).clear();
//            }
//        }
//
//        if (!overflowRecords.isEmpty()) {
//            for (Record record : overflowRecords) {
//                Centroid nearestCentroid = null;
//                int minSize = Integer.MAX_VALUE;
//                for (Map.Entry<Centroid, List<Record>> entry : redistributedClusters.entrySet()) {
//                    List<Record> records = entry.getValue();
//                    if (records.size() < targetClusterSize) {
//                        if (records.size() < minSize) {
//                            minSize = records.size();
//                            nearestCentroid = entry.getKey();
//                        }
//                    }
//                }
//                if (nearestCentroid != null) {
//                    redistributedClusters.get(nearestCentroid).add(record);
//                }
//            }
//        }
//
//        return redistributedClusters;
//    }
//
//    //varun
////    private static Centroid average(Centroid centroid, List<Record> records) {
////        if (records == null || records.isEmpty()) {
////            return centroid;
////        }
////
////        Map<String, List<Double>> average = new HashMap<>(centroid.getCoordinates());
////
////        // Set the values to (0,0) lists
////        for (String key : average.keySet()) {
////            average.put(key, Arrays.asList(0.0, 0.0));
////        }
////
////        for (Record record : records) {
////            record.getFeatures().forEach(
////                    (k, v) -> {
////                        average.compute(k, (k1, currentValue) -> {
////                            List<Double> avg_coord2 = new ArrayList<>();
////                            Double lat = v.get(0) + currentValue.get(0);
////                            Double longitude = v.get(1) + currentValue.get(1);
////                            avg_coord2.add(lat);
////                            avg_coord2.add(longitude);
////                            return avg_coord2;
////                        });
////                    });
////        }
////
////        average.forEach((k, v) -> {
////            List<Double> avg_coord = new ArrayList<>();
////            Double lat = v.get(0) / records.size();
////            Double longitude = v.get(1) / records.size();
////            avg_coord.add(lat);
////            avg_coord.add(longitude);
////            average.put(k, avg_coord);
////        });
////
////        return new Centroid(average);
////    }
//    private static List<Centroid> relocateCentroids(Map<Centroid, List<Record>> clusters) {
//        int cluster_size = clusters.size();
//        System.out.println("initial cluster size is: "+cluster_size);
//        System.out.println(clusters);
//        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());
//
//    }
//
//    public static void main(String[] args) {
////        System.out.println("---------------Test GetFeatures---------------");
//        List<Record> records = new ArrayList<>();
//
//
//
//        //Start adding Records
//        List<Double> coord1 = new ArrayList<>();
//        coord1.add(37.56819053958451);
//        coord1.add(127.00847759788303);
//        //location1 is a point with supposedly multiple features
//        Map<String, List<Double>> location1 = new HashMap<>();
//        //add distance feature
//        location1.put("Location", coord1);
//        //assign location1 as location of Record object address1
//        Record address1 = new Record("263 Jangchungdan-ro, Jung-gu, Seoul, South Korea", location1);
////        System.out.println(address1
////                .getFeatures());
//
//        List<Double> coord2 = new ArrayList<>();
//        coord2.add(37.57020985495065);
//        coord2.add(126.99959286887606);
//        Map<String, List<Double>> location2 = new HashMap<>();
//        location2.put("Location", coord2);
//        Record address2 = new Record("88 Changgyeonggung-ro, Jongno-gu, Seoul, South Korea", location2);
//
//        List<Double> coord3 = new ArrayList<>();
//        coord3.add(37.57982948171247);
//        coord3.add(126.97709462440277);
//        Map<String, List<Double>> location3 = new HashMap<>();
//        location3.put("Location", coord3);
//        Record address3 = new Record("161 Sajik-ro, Jongno-gu, Seoul, South Korea", location3);
//
//        List<Double> coord4 = new ArrayList<>();
//        coord4.add(37.573806731717866);
//        coord4.add(126.99016465032621);
//        Map<String, List<Double>> location4 = new HashMap<>();
//        location4.put("Location", coord4);
//        Record address4 = new Record("28 Samil-daero 30-gil, Ikseon-dong, Jongno-gu, Seoul, South Korea", location4);
//
//        List<Double> coord5 = new ArrayList<>();
//        coord5.add(37.57793303286747);
//        coord5.add(126.98647139635473);
//        Map<String, List<Double>> location5 = new HashMap<>();
//        location5.put("Location", coord5);
//        Record address5 = new Record("8 Achasan-ro 9-gil, Seongsu-dong 2(i)-ga, Seongdong-gu, Seoul, South Korea", location5);
//
//        List<Double> coord6 = new ArrayList<>();
//        coord6.add(37.55130547441948);
//        coord6.add(126.98820514003933);
//        Map<String, List<Double>> location6 = new HashMap<>();
//        location6.put("Location", coord6);
//        Record address6 = new Record("105 Namsangongwon-gil, Yongsan-gu, Seoul, South Korea", location6);
//
//        List<Double> coord7 = new ArrayList<>();
//        coord7.add(37.56214305462524);
//        coord7.add(126.92501691305641);
//        Map<String, List<Double>> location7 = new HashMap<>();
//        location7.put("Location", coord7);
//        Record address7 = new Record("South Korea, Seoul, Mapo-gu, 연남동 390-71", location7);
//
//        List<Double> coord8 = new ArrayList<>();
//        coord8.add(37.29391);
//        coord8.add(127.20256);
////        coord8.add(37.56314305462524);
////        coord8.add(126.93501691305641);
//        Map<String, List<Double>> location8 = new HashMap<>();
//        location8.put("Location", coord8);
//        Record address8 = new Record("199 Everland-ro, Pogok-eup, Cheoin-gu, Yongin-si, Gyeonggi-do, South Korea", location8);
//
//        List<Double> coord9 = new ArrayList<>();
//        coord9.add(37.52403);
//        coord9.add(127.02343);
//        Map<String, List<Double>> location9 = new HashMap<>();
//        location9.put("Location", coord9);
//        Record address9 = new Record("68 Nonhyeon-ro 175-gil, Gangnam-gu, Seoul, South Korea", location9);
//
//        List<Double> coord10 = new ArrayList<>();
//        coord10.add(37.57962);
//        coord10.add(126.97096);
//        Map<String, List<Double>> location10 = new HashMap<>();
//        location10.put("Location", coord10);
//        Record address10 = new Record("4 Jahamun-ro 11-gil, Jongno-gu, Seoul, South Korea", location10);
//
//        List<Double> coord11 = new ArrayList<>();
//        coord11.add(37.52385);
//        coord11.add(126.98047);
//        Map<String, List<Double>> location11 = new HashMap<>();
//        location11.put("Location", coord11);
//        Record address11 = new Record("137 Seobinggo-ro, Yongsan-gu, 서울특별시 South Korea", location11);
//
//        List<Double> coord12 = new ArrayList<>();
//        coord12.add(37.571);
//        coord12.add(126.97694);
//        Map<String, List<Double>> location12 = new HashMap<>();
//        location12.put("Location", coord12);
//        Record address12 = new Record("172 Sejong-daero, Sejongno, Jongno-gu, Seoul, South Korea", location12);
//
////        List<Double> coord13 = new ArrayList<>();
////        coord12.add(37.56775);
////        coord12.add(126.88568);
////        Map<String, List<Double>> location13 = new HashMap<>();
////        location13.put("Location", coord13);
////        Record address13 = new Record("13 Hanul Park", location12);
//        records.add(address1);
//        records.add(address2);
//        records.add(address3);
//        records.add(address4);
//        records.add(address5);
//        records.add(address6);
//        records.add(address7);
//        records.add(address8);
//        records.add(address9);
//        records.add(address10);
//        records.add(address11);
//        records.add(address12);
////        records.add(address13);
//
//        System.out.println("~~~~~~~~~~~~~~~~Printing each record~~~~~~~~~~~~~~~");
//        System.out.println(records.get(0).getAddress());
//        System.out.println(records.get(0).getFeatures());
//        System.out.println(records.get(1).getAddress());
//        System.out.println(records.get(1).getFeatures());
//        System.out.println(records.get(2).getAddress());
//        System.out.println(records.get(2).getFeatures());
//        System.out.println(records.get(3).getAddress());
//        System.out.println(records.get(3).getFeatures());
//        System.out.println(records.get(4).getAddress());
//        System.out.println(records.get(4).getFeatures());
//        System.out.println(records.get(5).getAddress());
//        System.out.println(records.get(5).getFeatures());
//        System.out.println(records.get(6).getAddress());
//        System.out.println(records.get(6).getFeatures());
//        System.out.println(records.get(7).getAddress());
//        System.out.println(records.get(7).getFeatures());
//        System.out.println(records.get(8).getAddress());
//        System.out.println(records.get(8).getFeatures());
//        System.out.println(records.get(9).getAddress());
//        System.out.println(records.get(9).getFeatures());
//        System.out.println(records.get(10).getFeatures());
//        System.out.println(records.get(10).getFeatures());
//        System.out.println(records.get(11).getFeatures());
//        System.out.println(records.get(11).getFeatures());
////        System.out.println(records.get(12).getFeatures());
////        System.out.println(records.get(12).getFeatures());
//        System.out.println("~~~~~~~~~~~~~~~~End of Printing each record~~~~~~~~~~~~~~~");
//
//        System.out.println("\n");
//        System.out.println("\n");
//
//
//        Map<Centroid, List<Record>> clusters = KMeans.fit(records, 4, new EuclideanDistance(), 10);
//        int targetClusterSize = records.size() / 4; // Replace 4 with the desired number of clusters
//        Map<Centroid, List<Record>> redistributedClusters = redistributeClusters(clusters, targetClusterSize);
//        System.out.println("\n");
//        System.out.println("\n");
//
//        System.out.println(clusters);
//        // Printing the cluster configuration
//        clusters.forEach((key, value) -> {
//            System.out.println("-------------------------- CLUSTER ----------------------------");
//
//            // Sorting the coordinates to see the most significant tags first.
//            System.out.println(key);
////            Record recordtoremove = null;
////            ArrayList latlng = new ArrayList<>();
////            latlng = (ArrayList) key.getCoordinates().get("Location");
////            for (Record record : records){
////                if (record.location == latlng){
////                    recordtoremove = record;
////                }
////            }
////
////            System.out.println(recordtoremove);
//            System.out.println(value);
//            String members = String.join(", ", value.stream().map(Record::getAddress).collect(toSet()));
//            System.out.print("members: "+ members + "\n");
//
//
//        });
//
//        System.out.println("\n");
//        System.out.println("\n");
//
//        System.out.println("-------------------clusters overall information---------------: \n"+ clusters.keySet());
//        System.out.println("number of clusters: "+clusters.size());
//        Centroid cluster1 = clusters.keySet().iterator().next();
//        Centroid cluster2 = clusters.keySet().iterator().next();
//        Centroid cluster3 = clusters.keySet().iterator().next();
//        Centroid cluster4 = clusters.keySet().iterator().next();
//        System.out.println(cluster1); //get centroid object
//        System.out.println(clusters.get(cluster1)); //get list of record addresses
//        System.out.println(clusters.keySet().iterator().next());
//
//        Set<Centroid> clusterKeySet = clusters.keySet();
//        //convert set to list
//        List<Centroid> clustersKeys = new ArrayList<>(clusterKeySet);
//        Centroid firstCentroid = clustersKeys.get(0);
//        Centroid secondCentroid = clustersKeys.get(0);
//        Centroid thirdCentroid = clustersKeys.get(0);
//        Centroid fourthCentroid = clustersKeys.get(0);
//
//        System.out.println(clusters.get(firstCentroid));
//        System.out.println(clusters.get(secondCentroid));
//        System.out.println(clusters.get(thirdCentroid));
//        System.out.println(clusters.get(fourthCentroid));
//
//
//
////        System.out.println("---------------Test RandomCentroids---------------");
////        List<Centroid> centroids = chooseRandomCentroids(records, 3);
////
////        System.out.println(centroids.get(0).getCoordinates());
////        System.out.println(centroids.get(1).getCoordinates());
////        System.out.println(centroids.get(2).getCoordinates());
//
//
////
////        System.out.println("---------------Test NearestCentroid---------------");
////        EuclideanDistance distance = new EuclideanDistance();
////
////        Centroid nearest = nearestCentroid(records.get(0), centroids, distance);
////
////        System.out.println(nearest.getCoordinates());
////
////
////        System.out.println("---------------Test AssignToCluster---------------");
////
////
////        System.out.println("---------------Test AverageCentroid---------------");
////
////
////        System.out.println("---------------Test RelocateCentroid---------------");
////
////
////        System.out.println("---------------Test Fit---------------");
//    }
//
//}
