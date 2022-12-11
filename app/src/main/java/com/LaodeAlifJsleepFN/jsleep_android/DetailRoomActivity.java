package com.LaodeAlifJsleepFN.jsleep_android;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.Room;

import java.util.Calendar;

public class DetailRoomActivity extends AppCompatActivity {
    TextView name, bed, size, price, address, city, fromText, toText;
    CheckBox ac, bathup, wifi, balcony, refrigerator, restaurant, fitnessCenter, swimmingPool;
    private DatePickerDialog datePickerDialog;
    private Button fromDateSpinner, toDateSpinner, makeBook, makeBook2;
    public static String fromDate, toDate;
    public static Room selectedRoom;
    public static int totalDays;
    public static int roomListSelected;
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        name = findViewById(R.id.nameDetail);
        size = findViewById(R.id.sizeDetail);
        bed = findViewById(R.id.bedDetail);
        price = findViewById(R.id.priceDetail);
        address = findViewById(R.id.addressDetail);
        city = findViewById(R.id.cityDetail);
        ac = findViewById(R.id.acDetail);
        bathup = findViewById(R.id.bathtubDetail);
        wifi = findViewById(R.id.wifiDetail);
        balcony = findViewById(R.id.balconyDetail);
        refrigerator = findViewById(R.id.refrigeratorDetail);
        restaurant = findViewById(R.id.restaurantDetail);
        fitnessCenter = findViewById(R.id.fitnessCenterDetail);
        swimmingPool = findViewById(R.id.swimming_poolDetail);
        makeBook = findViewById(R.id.bookButton);
        makeBook2 = findViewById(R.id.bookButton2);
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        roomListSelected = MainActivity.roomSelected - (MainActivity.currpage*MainActivity.pagesize);
        selectedRoom = MainActivity.roomList.get(roomListSelected);
        System.out.println("Selected room: " + selectedRoom.id);
        name.setText("Name: " + selectedRoom.name);
        size.setText("Size: " + String.valueOf(selectedRoom.size) + "m^2");
        bed.setText("Bed Type: " + selectedRoom.bedType.toString());
        price.setText("Price: Rp. " + Double.toString(selectedRoom.price.price));
        address.setText("Address: " + selectedRoom.address);
        city.setText("City: " + selectedRoom.city.toString());
        for(int i = 0; i < selectedRoom.facility.size(); i++){
            System.out.println(selectedRoom.facility.get(i));
            switch (selectedRoom.facility.get(i)){
                case AC:
                    ac.setChecked(true);
                    break;
                case WiFi:
                    wifi.setChecked(true);
                    break;
                case Balcony:
                    balcony.setChecked(true);
                    break;
                case Bathtub:
                    bathup.setChecked(true);
                    break;
                case Restaurant:
                    restaurant.setChecked(true);
                    break;
                case Refrigerator:
                    refrigerator.setChecked(true);
                    break;
                case SwimmingPool:
                    swimmingPool.setChecked(true);
                    break;
                case FitnessCenter:
                    fitnessCenter.setChecked(true);
                    break;
            }
        }
        fromDateSpinner = findViewById(R.id.fromDate);
        toDateSpinner = findViewById(R.id.toDate);
        fromDateSpinner.setText(getTodaysDate());
        toDateSpinner.setText(getTodaysDate());

        initDatePicker(fromDateSpinner);
        //initDatePicker(toDateSpinner);
        makeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeBook.setVisibility(View.INVISIBLE);
                fromText.setVisibility(View.VISIBLE);
                fromDateSpinner.setVisibility(View.VISIBLE);
                makeBook2.setVisibility(View.VISIBLE);
                toText.setVisibility(View.VISIBLE);
                toDateSpinner.setVisibility(View.VISIBLE);
            }
        });
        makeBook2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("From Date picked: " + fromDate);
                System.out.println("To Date Picked: " + toDate);
                Intent toconfirm = new Intent(DetailRoomActivity.this, ConfirmRoomOrderActivity.class);
                startActivity(toconfirm);
            }
        });

    }
    //Spinner tanggal
    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(Button DateSpinner) {
        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                if(DateSpinner == fromDateSpinner){
                    fromDate = makeDateString(day, month, year);
                    fromDateSpinner.setText(fromDate);
                    initDatePicker(toDateSpinner);
                }
                if(DateSpinner == toDateSpinner){
                    toDate = makeDateString(day, month, year);
                    toDateSpinner.setText(toDate);
                }
            }

        };
        getTodaysDate();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year){
        return year + "-" + month + "-" + day;
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }


}