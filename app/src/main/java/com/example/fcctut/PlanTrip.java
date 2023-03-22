package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
//dylans page
public class PlanTrip extends AppCompatActivity {
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private Button startDateButton;
    private Button endDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);
        initDatePicker();
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        startDateButton.setText(getTodaysDate());
        endDateButton.setText(getTodaysDate());
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                startDateButton.setText(date);
            }
        };
        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                endDateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        startDatePickerDialog = new DatePickerDialog(this, startDateSetListener, year, month, day);
        endDatePickerDialog = new DatePickerDialog(this, endDateSetListener, year, month, day);
    } //end of initdatepicker function

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month){
        if(month == 1) {
            return "Jan";
        }
        if(month == 2){
            return "Feb";
        }
        if(month == 3) {
            return "Mar";
        }
        if(month == 4){
            return "Apr";
        }
        if(month == 5) {
            return "May";
        }
        if(month == 6){
            return "Jun";
        }
        if(month == 7) {
            return "Jul";
        }
        if(month == 8){
            return "Aug";
        }
        if(month == 9) {
            return "Sep";
        }
        if(month == 10){
            return "Oct";
        }
        if(month == 11) {
            return "Nov";
        }
        if(month == 12){
            return "Dec";
        }
        return "Jan";
    }

    public void openStartDatePicker(View view){
        startDatePickerDialog.show();
    }

    public void openEndDatePicker(View view){
        endDatePickerDialog.show();
    }

} //end of class PlanTrip
