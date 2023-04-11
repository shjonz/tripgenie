package com.example.fcctut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Distance {
    double calculate(Map<String, List<Double>> f1, Map<String, List<Double>> f2);
}
