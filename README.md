# Tripgenie
<img src="https://github.com/shjonz/tripgenie/assets/81726240/8f061139-c949-4392-a7ff-eb0873cc6117" width="1944" height="564">

50.001 Information Systems & Programming Project

## Video

https://github.com/shjonz/tripgenie/assets/81726240/870b02fd-2193-4378-a64b-d6a9f4c34092

## Poster
<img src="https://github.com/shjonz/tripgenie/assets/81726240/ad176ce5-428e-4510-bd7a-4d5f4306c8f3" width="421" height="596">

## Description
TripGenie is a user-friendly mobile app designed to simplify and optimise your travel planning experience. By leveraging the power of Google Places API and powerful algorithms, TripGenie provides personalised suggestions for popular destinations, be it restaurants, sightseeing sights or attractions, based on your preferences and current location.
 
With an intuitive interface, users can effortlessly browse through a list of recommendations, save their favourite places, and then with a simple click of a button obtain an optimised itinerary for their trip. The algorithm organises destinations into clusters using an augmented K++ means algorithm based on proximity between destinations. An optimal route is then computed using a modified greedy Dijkstra algorithm which takes into account proximity between locations, opening hours of places, time spent at these places, and prioritises eating places during meal hours. This is so users spend minimal time between locations and more time at them.

Designed with robust and maintainable software principles such as favouring composition over inheritance, encapsulation and polymorphism, TripGenie ensures a maintainable code base. Whether you're planning a weekend getaway, a well-organised business trip, or discovering hidden gems in your hometown, TripGenie is your ultimate travel companion.


## Features 

Optimised scheduling itinerary which consists of
- Opening/Closing hours
- Travelling time
- Time spent
- Prioritising eating places when it is during conventional meal times (eg lunch time 1200 and dinner time 1800)

Main Idea
- K++ clustering will create 4 clusters (if the user enters 4 days). Each cluster will contain relatively same amount of places and places are chosen based on how near centroids are. 
Custom algorithm that considers the 4 factors. 
- Given a cluster of places, the algorithm will separate the places into eating places and tourist places. Choosing the place that opens the earliest will be the starting place. Similar to what a greedy algorithm does, this place will start from the place that opens the earliest and checks for places which are open now and places that will be open if we include 45 minutes of travel time. The schedule will add 

Other Potential Idea #1:
- Use K++ clustering to cluster places together
- Use shortest path Dijkstra Algorithm to find the next nearest place to go to

Other Potential Idea #2:
- Find all combinations of routes 
- Scan through all routes and find the plan with shortest distance that fits the schedule


## Implementation of Optimization algorithm
- Similar to Travelling Salesman Problem (https://en.wikipedia.org/wiki/Travelling_salesman_problem) but with additional factors other than shortest distance
- K++ clustering algorithm following 


## Installation

1. Android studios - https://developer.android.com/studio
2. Google maps, places API - https://developers.google.com/maps/documentation/android-sdk/cloud-setup
3. firebase API - https://firebase.google.com/docs/android/setup

Clone the git repository [git](https://git-scm.com/book/en/v2/Git-Basics-Getting-a-Git-Repository) to install TripGenie.

```bash
git clone 'https://github.com/shjonz/tripgenie.git'
```

## Usage

```python
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

## References

More about K-Means Algorithm - https://towardsdatascience.com/k-means-clustering-algorithm-applications-evaluation-methods-and-drawbacks-aa03e644b48a

## Libraries

- [Google Directions API](https://developers.google.com/maps/documentation/directions/overview)
- [Google Places API](https://developers.google.com/maps/documentation/places/web-service/overview)
- [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk/overview)
- [Firebase](https://firebase.google.com/)
- Android studios Java

## Future Improvements
- Fix bugs
- Add more 
