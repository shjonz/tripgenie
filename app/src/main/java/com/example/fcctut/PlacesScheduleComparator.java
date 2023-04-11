package com.example.fcctut;

import java.util.Comparator;

public class PlacesScheduleComparator implements Comparator<PlacesSchedule> {
    @Override
    public int compare(PlacesSchedule origin, PlacesSchedule dest) {
        return Integer.compare(origin.getDistance_From_point(), dest.getDistance_From_point());
    }
}
