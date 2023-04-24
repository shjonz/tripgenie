package com.example.fcctut;

import com.google.maps.GeoApiContext;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CustomAlgorithm extends Algorithm{

    @Override
    Map<Centroid, List<Place>> Clustering(ArrayList<Place> savedPlaces) {
        Map<Centroid, List<Place>> clusters = KMeans.fit(savedPlaces, 2, new EuclideanDistance(), 10);
        return clusters;
    }

    @Override
    ArrayList<ArrayList<Place>> GreedyAlgorithm(ArrayList<ArrayList<Place>> array_array_of_clusters) {
        ArrayList<ArrayList<Place>> return_array_of_clusers = new ArrayList<ArrayList<Place>>();

        //loop through list of arraylist of clusters
        for (int i = 0; i < array_array_of_clusters.size(); i++) {
            System.out.println("iteration " + i + " array of array of clusters.size " + array_array_of_clusters.size() + " ========== array array of clusters each array per day " + array_array_of_clusters.get(i).toString());


            //ArrayList<Record> cluster1 = array_array_of_clusters.get(i);
            ArrayList<Place> cluster1 = array_array_of_clusters.get(i);

            for (int a = 0; a < cluster1.size(); a++) {
                System.out.println("XXXXXXXXXXXXXXX inside cluster1 arraylist " + cluster1.get(a).getName() + cluster1.size() );
                //+ " lat lng " + cluster1.get(a).getGeometry().getLocation().getLatitude() + cluster1.get(a).getGeometry().getLocation().getLongitude() + cluster1.get(a).getOpeningHours().getOpeningHours().values());

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
                cluster1.get(d).setEatingPlace();
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxx check if set eating place " + cluster1.get(d).getEatingPlace() );
                if (cluster1.get(d).getEatingPlace() == true ) {
                    eating_locations_data.add(cluster1.get(d));
                    cluster1.get(d).setTime_spent(Duration.ofSeconds(3600));
                    if (cluster1.get(d).getOpeningHours() == null) {
                        cluster1.get(d).setOpening_hours_test(LocalTime.parse("09:00"));
                        cluster1.get(d).setClosing_hours_test(LocalTime.parse("17:00"));
                        System.out.println(" eating closing hours null so set in opening_hours_test " + cluster1.get(d).getName());
                    }


                } else {
                    locations_data.add(cluster1.get(d));
                    cluster1.get(d).setTime_spent(Duration.ofSeconds(7200));
                    if (cluster1.get(d).getOpeningHours() == null) {
                        cluster1.get(d).setOpening_hours_test(LocalTime.parse("09:00"));
                        cluster1.get(d).setClosing_hours_test(LocalTime.parse("17:00"));
                        System.out.println(" normal locations closing hours null so set in opening_hours_test " + cluster1.get(d).getName());
                    }
                }


            }

            System.out.println("check size of locations_data and eating_locations_data "+ locations_data.size() + " eating size " + eating_locations_data.size());


            ArrayList<Place> iternarySchedule = new ArrayList<Place>();
            Place origin_place = new Place((origin));
            origin_place.setEating_place_test(false);
            origin_place.setName("Hotel Skypark Central MyeongDong");

            iternarySchedule.add(origin_place);

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
                if (eating_locations_data.get(k).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours()).isBefore(earliest_opening_time) ) {
                    earliest_opening_time = LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours());
                    first_place_to_visit = eating_locations_data.get(k);
                } else if ( eating_locations_data.get(k).getOpeningHours() == null && eating_locations_data.get(k).getOpening_hours_test().isBefore(earliest_opening_time)) {
                    earliest_opening_time = eating_locations_data.get(k).getOpening_hours_test();
                    first_place_to_visit = eating_locations_data.get(k);
                }

                if (eating_locations_data.get(k).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getClosingHours()).isAfter(latest_closing_time) ) {
                    latest_closing_time = LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getClosingHours() );
                    earliest_closing_place = eating_locations_data.get(k);
                } else if ( eating_locations_data.get(k).getOpeningHours() == null && eating_locations_data.get(k).getClosing_hours_test().isAfter(latest_closing_time)) {
                    latest_closing_time = eating_locations_data.get(k).getClosing_hours_test();
                    earliest_closing_place = eating_locations_data.get(k);
                }

                if ( eating_locations_data.get(k).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getClosingHours()).isBefore(earliest_closing_time)) {
                    earliest_closing_time = eating_locations_data.get(k).getClosing_hours_test();
                } else if ( eating_locations_data.get(k).getOpeningHours() == null && eating_locations_data.get(k).getClosing_hours_test().isBefore(earliest_closing_time)) {
                    earliest_closing_time = eating_locations_data.get(k).getClosing_hours_test();
                }
            }


            for (int z = 0; z < locations_data.size(); z++) {
                if (locations_data.get(z).getOpeningHours() != null && LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours()).isBefore(earliest_opening_time) ) {
                    earliest_opening_time = LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours());
                    first_place_to_visit = locations_data.get(z);
                } else if ( locations_data.get(z).getOpeningHours() == null && locations_data.get(z).getOpening_hours_test().isBefore(earliest_opening_time)) {
                    earliest_opening_time = locations_data.get(z).getOpening_hours_test();
                    first_place_to_visit = locations_data.get(z);
                }

                if (locations_data.get(z).getOpeningHours() != null && LocalTime.parse(locations_data.get(z).getOpeningHours().getClosingHours()).isAfter(latest_closing_time) ) {
                    latest_closing_time = LocalTime.parse(locations_data.get(z).getOpeningHours().getClosingHours() );
                    earliest_closing_place = locations_data.get(z);
                } else if (locations_data.get(z).getOpeningHours() == null && locations_data.get(z).getClosing_hours_test().isAfter(latest_closing_time)) {
                    latest_closing_time = locations_data.get(z).getClosing_hours_test();
                    earliest_closing_place = locations_data.get(z);
                }

                if ( locations_data.get(z).getOpeningHours() != null && LocalTime.parse(locations_data.get(z).getOpeningHours().getClosingHours()).isBefore(earliest_closing_time)) {
                    earliest_closing_time = LocalTime.parse(locations_data.get(z).getOpeningHours().getClosingHours());
                } else if ( locations_data.get(z).getOpeningHours() == null && locations_data.get(z).getClosing_hours_test().isBefore(earliest_closing_time)) {
                    earliest_closing_time = locations_data.get(z).getClosing_hours_test();
                }
            }


            LocalTime currentTime = earliest_opening_time.minus(Duration.ofSeconds(2700));


            int lunch_count = 0;
            int dinner_count = 0;

            int sametime = 0;

            int iterations = 0;

            System.out.println("latest closing time before while loop " + latest_closing_time);


            while (currentTime.isBefore(latest_closing_time)) {


                System.out.println("inside locations current time " + currentTime + " locations data size "
                        + locations_data.size() + " eating locations data size " + eating_locations_data.size()
                        + " lunch count " + lunch_count);


                //greedy algo find eating place that is open within next 45 mins + 1 hour
                if (currentTime.plus(Duration.ofSeconds(2700)).isAfter(LocalTime.parse("11:00")) && (lunch_count == 0) && (eating_locations_data.size() > 0)) {
                    //prioritise lunch places
                    //first find eligible eating places.

                    System.out.println("look for lunch places " + lunch_count);
                    for (int h = 0; h < eating_locations_data.size(); h++) {

                        //find eligible places and check u take 45 mins to travel there and 1 hour to spend there
                        if (eating_locations_data.get(h).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(h).getOpeningHours().getOpeningHours()).isBefore(currentTime.plus(Duration.ofSeconds(2701))) && LocalTime.parse(eating_locations_data.get(h).getOpeningHours().getClosingHours()).isAfter(currentTime.plus(Duration.ofSeconds(6300)))) {
                            eligible_Places.add(eating_locations_data.get(h));
                            eating_locations_data.get(h).setDistanceFromPoint(iternarySchedule.get(iternarySchedule_pointer).getAddress());
                        } else if (eating_locations_data.get(h).getOpeningHours() == null && eating_locations_data.get(h).getOpening_hours_test().isBefore(currentTime.plus(Duration.ofSeconds(2701))) && eating_locations_data.get(h).getClosing_hours_test().isAfter(currentTime.plus(Duration.ofSeconds(6300)))) {
                            eligible_Places.add(eating_locations_data.get(h));
                            eating_locations_data.get(h).setDistanceFromPoint(iternarySchedule.get(iternarySchedule_pointer).getAddress());

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



                        Duration duration_to_add = Duration.ofSeconds(eligible_Places.get(eating_locations_data_pointer).getDurationFromPoint());

                        if (origin_place.getArrival_time() == null) {
                            origin_place.setArrival_time(currentTime);
                        }

                        currentTime = currentTime.plus(duration_to_add);
                        eligible_Places.get(0).setArrival_time(currentTime);
                        System.out.println("eating locations -------------------------- set arrival time " + eligible_Places.get(0).getArrival_time());



                        duration_to_add = eligible_Places.get(0).getTime_spent();
                        currentTime = currentTime.plus(duration_to_add);
                        System.out.println("inside lunch set time spent " + currentTime);
                        eligible_Places.get(0).setDeparture_time(currentTime);
                        //TODO: add how long people typically spend here and check opening hours
                        System.out.println("location added " + eating_locations_data.get(eating_locations_data_pointer).getAddress() + " " + duration_to_add + " " + currentTime);
                        eating_locations_data.remove(eligible_Places.get(0));
                        iternarySchedule.add(eligible_Places.get(0));
                        eligible_Places.clear();

                        System.out.println("remove locations data check if eligible places cleared " + eligible_Places.size());
                        iternarySchedule_pointer += 1;
                        lunch_count += 1;
                    } else {
                        LocalTime next_nearest_time = currentTime;
                        for (int k = 0; k < eating_locations_data.size(); k++) {

                            if (eating_locations_data.get(k).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours()).isAfter(next_nearest_time)) {
                                System.out.println("new nearest time " + next_nearest_time);
                                next_nearest_time = LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours());
                            } else if ( eating_locations_data.get(k).getOpeningHours() == null && eating_locations_data.get(k).getOpening_hours_test().isAfter(next_nearest_time)) {
                                System.out.println("new nearest time " + next_nearest_time);
                                next_nearest_time = eating_locations_data.get(k).getOpening_hours_test();
                            }

                        }


                        for (int z = 0; z < locations_data.size(); z++) {
                            if ( locations_data.get(z).getOpeningHours() != null && LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours()).isAfter(next_nearest_time)) {
                                System.out.println("new locations nearest time " + next_nearest_time);
                                next_nearest_time = LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours());
                                //first_place_to_visit = locations_data.get(z);
                            }
                            else if (locations_data.get(z).getOpeningHours() == null && locations_data.get(z).getOpening_hours_test().isAfter(next_nearest_time)) {
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
                        } else if (next_nearest_time != null) {
                            currentTime = next_nearest_time.minus(Duration.ofSeconds(900));
                            System.out.println("set new locations nearest time " + next_nearest_time);

                        }
                    }

                } //end of find eating place fn


                //greedy algo find open a location place that is open within next 45 mins + 1 hour
                else if ((locations_data.size() > 0)) {

                    System.out.println("inside locations size > 0 " + locations_data.size());

                    //first find eligible places.
                    for (int h = 0; h < locations_data.size(); h++) {
                        //find eligible places and check u take 45 mins to travel there and 1 hour to spend there

                        if ( locations_data.get(h).getOpeningHours() != null && LocalTime.parse(locations_data.get(h).getOpeningHours().getOpeningHours()).isBefore(currentTime.plus(Duration.ofSeconds(2701))) && LocalTime.parse(locations_data.get(h).getOpeningHours().getClosingHours()).isAfter(currentTime.plus(Duration.ofSeconds(6300)))) {
                            eligible_Places.add(locations_data.get(h));
                            locations_data.get(h).setDistanceFromPoint(iternarySchedule.get(iternarySchedule_pointer).getAddress());
                        } else if ( locations_data.get(h).getOpeningHours() == null && locations_data.get(h).getOpening_hours_test().isBefore(currentTime.plus(Duration.ofSeconds(2701))) && locations_data.get(h).getClosing_hours_test().isAfter(currentTime.plus(Duration.ofSeconds(6300)))) {
                            eligible_Places.add(locations_data.get(h));
                            locations_data.get(h).setDistanceFromPoint(iternarySchedule.get(iternarySchedule_pointer).getAddress());
                        }
                    }

                    //check if eligible places got size
                    if (eligible_Places.size() > 0) {
                        //since now all eating places are open and can spend 1 hour there, we take closest distance.
                        eligible_Places.sort(new Comparator<Place>() {
                            @Override
                            public int compare(Place o1, Place o2) {
                                return Integer.compare(o1.getDistanceFromPoint(), o2.getDistanceFromPoint());
                            }
                        });


                        iternarySchedule.add(eligible_Places.get(0));
                        Duration duration_to_add = Duration.ofSeconds(eligible_Places.get(locations_data_pointer).getDurationFromPoint());

                        if (origin_place.getArrival_time() == null) {
                            origin_place.setArrival_time(currentTime);
                        }

                        currentTime = currentTime.plus(duration_to_add);
                        eligible_Places.get(0).setArrival_time(currentTime);
                        System.out.println("inside locations time set arrival time " + currentTime);


                        duration_to_add = eligible_Places.get(0).getTime_spent();
                        currentTime = currentTime.plus(duration_to_add);
                        System.out.println("inside locations  set time spent " + currentTime + " check how long " + eligible_Places.get(0).getTime_spent());
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

                            if (eating_locations_data.get(k).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours()).isAfter(next_nearest_time)) {
                                System.out.println("new nearest time " + next_nearest_time);
                                next_nearest_time = LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours());
                            } else if ( eating_locations_data.get(k).getOpeningHours() == null && eating_locations_data.get(k).getOpening_hours_test().isAfter(next_nearest_time)) {
                                System.out.println("new nearest time " + next_nearest_time + " current time " + currentTime);
                                next_nearest_time = eating_locations_data.get(k).getOpening_hours_test();
                            }

                        }


                        for (int z = 0; z < locations_data.size(); z++) {

                            if ( locations_data.get(z).getOpeningHours() != null && LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours()).isAfter(next_nearest_time)) {
                                System.out.println("new locations nearest time " + next_nearest_time);
                                next_nearest_time = LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours());
                                //first_place_to_visit = locations_data.get(z);
                            } else if ( locations_data.get(z).getOpeningHours() == null && locations_data.get(z).getOpening_hours_test().isAfter(next_nearest_time)) {
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
                        } else if (next_nearest_time != null) {
                            currentTime = next_nearest_time.minus(Duration.ofSeconds(900));
                            System.out.println("set new locations nearest time " + next_nearest_time + " set new current " +
                                    " time in locationssize > 0 " + currentTime);

                        }
                    }

                } //if locations_data


                //greedy algo find dinner eating place that is open within next 45 mins + 1 hour
                else if (currentTime.plus(Duration.ofSeconds(2700)).isAfter(LocalTime.parse("17:30")) && (dinner_count == 0) && (eating_locations_data.size() > 0)) {
                    //prioritise lunch places
                    //first find eligible eating places.
                    System.out.println("dinner look for places" + dinner_count);
                    for (int h = 0; h < eating_locations_data.size(); h++) {

                        //find eligible places and check u take 45 mins to travel there and 1 hour to spend there
                        if (eating_locations_data.get(h).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(h).getOpeningHours().getOpeningHours()).isBefore(currentTime.plus(Duration.ofSeconds(2701))) && LocalTime.parse(eating_locations_data.get(h).getOpeningHours().getClosingHours()).isAfter(currentTime.plus(Duration.ofSeconds(6300)))) {
                            eligible_Places.add(eating_locations_data.get(h));
                            eating_locations_data.get(h).setDistanceFromPoint(iternarySchedule.get(iternarySchedule_pointer).getAddress());
                        } else if (eating_locations_data.get(h).getOpeningHours() == null && eating_locations_data.get(h).getOpening_hours_test().isBefore(currentTime.plus(Duration.ofSeconds(2701))) && eating_locations_data.get(h).getClosing_hours_test().isAfter(currentTime.plus(Duration.ofSeconds(6300)))) {
                            eligible_Places.add(eating_locations_data.get(h));
                            eating_locations_data.get(h).setDistanceFromPoint(iternarySchedule.get(iternarySchedule_pointer).getAddress());
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

                            if (origin_place.getArrival_time() == null) {
                                origin_place.setArrival_time(currentTime);
                            }

                            Duration duration_to_add = Duration.ofSeconds(eligible_Places.get(eating_locations_data_pointer).getDurationFromPoint());
                            currentTime = currentTime.plus(duration_to_add);
                            eligible_Places.get(0).setArrival_time(currentTime);
                            System.out.println("inside dinner time set arrival time " + currentTime);


                            duration_to_add = eligible_Places.get(0).getTime_spent();
                            currentTime = currentTime.plus(duration_to_add);
                            System.out.println("inside dinner set time spent " + currentTime);
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

                                if (eating_locations_data.get(k).getOpeningHours() != null && LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours()).isAfter(next_nearest_time)) {
                                    System.out.println("new nearest time " + next_nearest_time);
                                    next_nearest_time = LocalTime.parse(eating_locations_data.get(k).getOpeningHours().getOpeningHours());
                                } else if ( eating_locations_data.get(k).getOpeningHours() == null && eating_locations_data.get(k).getOpening_hours_test().isAfter(next_nearest_time)) {
                                    System.out.println("new nearest time " + next_nearest_time);
                                    next_nearest_time = eating_locations_data.get(k).getOpening_hours_test();
                                }

                            }


                            for (int z = 0; z < locations_data.size(); z++) {

                                if ( locations_data.get(z).getOpeningHours() != null && LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours()).isAfter(next_nearest_time)) {
                                    System.out.println("new locations nearest time " + next_nearest_time);
                                    next_nearest_time = LocalTime.parse(locations_data.get(z).getOpeningHours().getOpeningHours());
                                    //first_place_to_visit = locations_data.get(z);
                                } else if ( locations_data.get(z).getOpeningHours() == null && locations_data.get(z).getOpening_hours_test().isAfter(next_nearest_time)) {
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
                            } else if (next_nearest_time != null) {
                                currentTime = next_nearest_time.minus(Duration.ofSeconds(900));
                                System.out.println("set new locations nearest time " + next_nearest_time);

                            }
                        }


                    }

                } //end of find dinner eating place fn

                else if (eating_locations_data.size() == 0 && locations_data.size() == 0) {
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
                    + " array of array clusters " + return_array_of_clusers.get(i).toString() + "iteration: " + i);
            for (int j = 0; j < return_array_of_clusers.get(i).size(); j++) {
                System.out.println("inside " + return_array_of_clusers.get(i).get(j).getAddress() +" arrival time "+ return_array_of_clusers.get(i).get(j).getArrival_time());
            }
        }

        System.out.println("array of arrays clusters size " + return_array_of_clusers.size() );
        return return_array_of_clusers;
    }
}
