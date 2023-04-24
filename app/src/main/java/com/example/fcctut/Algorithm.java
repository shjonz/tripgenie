package com.example.fcctut;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Algorithm {

    //final void prepareIternary(ArrayList<Place> savedPlaces);


    abstract Map<Centroid, List<Place>> Clustering(ArrayList<Place> savedPlaces);

    abstract ArrayList<ArrayList<Place>> GreedyAlgorithm( ArrayList<ArrayList<Place>> return_array_of_clusers );



}
