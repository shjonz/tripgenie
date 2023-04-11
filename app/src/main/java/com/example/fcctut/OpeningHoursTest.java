package com.example.fcctut;

import java.util.List;

public class OpeningHoursTest {

    public static void main(String[] args) {
        String sampleJson = "{" +
                "\"weekday_text\": [" +
                "\"Monday: 9:00 AM – 5:00 PM\"," +
                "\"Tuesday: 9:00 AM – 5:00 PM\"," +
                "\"Wednesday: 9:00 AM – 5:00 PM\"," +
                "\"Thursday: 9:00 AM – 5:00 PM\"," +
                "\"Friday: 9:00 AM – 5:00 PM\"," +
                "\"Saturday: 10:00 AM – 4:00 PM\"," +
                "\"Sunday: Closed\"" +
                "]" +
                "}";

        Open openingHours = new Open(sampleJson);
        List<String> weekdayText = openingHours.getWeekdayText();
        for (String text : weekdayText) {
            System.out.println(text);
        }
    }
}
