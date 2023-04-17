package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;

public class  PlanTrip extends AppCompatActivity {
    private DatePickerDialog startDatePickerDialog; // A DatePickerDialog object for selecting the start date of the trip
    private DatePickerDialog endDatePickerDialog; // A DatePickerDialog object for selecting the end date of the trip
    private Button startDateButton; // The button that displays the selected start date
    private Button endDateButton; // The button that displays the selected end date
    private PlacesClient placesClient; // A PlacesClient object to make calls to the Places API
    private AutocompleteSessionToken sessionToken; // A session token to be used when making requests to the Places API
    private AutoCompleteTextView edtCitySearch; // An AutoCompleteTextView for searching cities
    private Button startplanningbutton; //button to go to add new locations page
    private TextInputLayout txtInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);

        initDatePicker(); // Initialize the DatePickerDialog objects
        startDateButton = findViewById(R.id.startDateButton); // Get a reference to the button for selecting the start date
        endDateButton = findViewById(R.id.endDateButton); // Get a reference to the button for selecting the end date
        startDateButton.setText(getTodaysDate()); // Set the text of the start date button to today's date
        endDateButton.setText(getTodaysDate()); // Set the text of the end date button to today's date
        startplanningbutton=findViewById(R.id.startplanningbutton); //Get a reference to the button for
        txtInputField = findViewById(R.id.txtInputField);

        startplanningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = txtInputField.getEditText().getText().toString();
                String startDate = startDateButton.getText().toString();
                String endDate = endDateButton.getText().toString();
                long k = 0; // difference in number of days

                DateTimeFormatter formatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                    LocalDate endDateObj = null;
                    endDateObj = LocalDate.parse(endDate, formatter);

                    LocalDate startDateObj = null;
                    startDateObj = LocalDate.parse(startDate, formatter);
                    k = ChronoUnit.DAYS.between(startDateObj, endDateObj);
                }

                // TODO: set dynamic filename
                String filename = "final.json";
                Trip trip = new Trip((int) k, cityName, startDate, endDate);
                FileManager.saveTrip(PlanTrip.this, filename, trip);

                SharedPreferences sharedPref = getSharedPreferences("fileNameSaver", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // set current file we are looking at
                editor.putString("currentFile", filename);

                // save filename to a set of existing filename
                Set<String> set = sharedPref.getStringSet("key", null);
                if (set == null) {
                    set = new HashSet<String>();
                }
                set.add(filename);
                editor.putStringSet("existingFileSet", set);
                editor.commit();
                editor.apply();

//                SharedPreferenceUtil.savePlaces(PlanTrip.this, new ArrayList<>());

                Intent intent = new Intent(PlanTrip.this, newLocations.class);
                startActivity(intent);
            }
        });


        // Initialize the Places API client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.WEB_API_KEY);
        }
        placesClient = Places.createClient(this);
        sessionToken = AutocompleteSessionToken.newInstance();

        edtCitySearch = findViewById(R.id.edtCitySearch); // Get a reference to the AutoCompleteTextView for searching cities
        edtCitySearch.setThreshold(1); // Set the minimum number of characters required to trigger suggestions to 1
        edtCitySearch.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            edtCitySearch.setText(selectedItem);
        });
        edtCitySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getAutocompletePredictions(s.toString()); // Fetch autocomplete predictions when the text in the AutoCompleteTextView changes
                // TODO Get the text from the AutoCompleteTextView and the dates from the  this page once the " Start Planning " button has been pushed
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    // Get today's date as a string
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    // Initialize the start and end date picker dialogs
    private void initDatePicker() {
        // Create the listener for the start date picker
        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                startDateButton.setText(date);
            }
        };

        // Create the listener for the end date picker
        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                endDateButton.setText(date);
            }
        };

        // Get the current date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Create the start and end date picker dialogs
        startDatePickerDialog = new DatePickerDialog(this, startDateSetListener, year, month, day);
        endDatePickerDialog = new DatePickerDialog(this, endDateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        // Map the month integer to the corresponding month abbreviation
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "Jan";
        }
    }

    public void openStartDatePicker(View view) {
        // Display the start date picker dialog
        startDatePickerDialog.show();
    }

    public void openEndDatePicker(View view) {
        // Display the end date picker dialog
        endDatePickerDialog.show();
    }

    private void getAutocompletePredictions(String query) {
        // Create the autocomplete request
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(sessionToken)
                .setQuery(query)
                .setTypeFilter(TypeFilter.CITIES)
                .build();

        // Send the request to the Places API
        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            List<String> cityNames = new ArrayList<>();
            // Extract the city names from the response predictions
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                cityNames.add(prediction.getPrimaryText(null).toString());
            }
            updateCitySearchAdapter(cityNames);
        }).addOnFailureListener((exception) -> {
            Log.e("PlanTrip", "Autocomplete prediction fetch failed", exception);
        });
    }

    private void updateCitySearchAdapter(List<String> cityNames) {
        // Create an adapter for the city search text view
        ArrayAdapter<String> citySearchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cityNames);
        edtCitySearch.setAdapter(citySearchAdapter);
    }
}