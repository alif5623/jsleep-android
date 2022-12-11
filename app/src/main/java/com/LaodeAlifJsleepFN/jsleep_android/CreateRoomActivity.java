package com.LaodeAlifJsleepFN.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.BedType;
import com.LaodeAlifJsleepFN.City;
import com.LaodeAlifJsleepFN.Facility;
import com.LaodeAlifJsleepFN.Room;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity implements View.OnClickListener {
   public Room room;
   private BedType typeBed;
   private City city;
   EditText nameCreate, priceCreate, addressCreate, sizeCreate;
   CheckBox ac, bathup, balcony, wifi, refrigerator, restaurant, fitnessCenter, swimmingPool;
   BaseApiService mApiService;
   Context mContext;
   Button roomCreateButton, cancelButton;
   ArrayList<Facility> facility = new ArrayList<Facility>();
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        nameCreate = findViewById(R.id.nameDetail);
        priceCreate = findViewById(R.id.priceDetail);
        addressCreate = findViewById(R.id.addressDetail);
        sizeCreate = findViewById(R.id.sizeDetail);
        ac = findViewById(R.id.acDetail);
        ac.setOnClickListener(this::onClick);
        bathup = findViewById(R.id.bathtubDetail);
        bathup.setOnClickListener(this::onClick);
        balcony = findViewById(R.id.balconyDetail);
        balcony.setOnClickListener(this::onClick);
        wifi = findViewById(R.id.wifiDetail);
        wifi.setOnClickListener(this::onClick);
        refrigerator = findViewById(R.id.refrigeratorDetail);
        refrigerator.setOnClickListener(this::onClick);
        restaurant = findViewById(R.id.restaurantDetail);
        restaurant.setOnClickListener(this::onClick);
        fitnessCenter = findViewById(R.id.fitnessCenterDetail);
        fitnessCenter.setOnClickListener(this::onClick);
        swimmingPool = findViewById(R.id.swimming_poolDetail);
        swimmingPool.setOnClickListener(this::onClick);
        Spinner citySpinner = (Spinner) findViewById(R.id.citySpinner);
        Spinner bedSpinner = (Spinner) findViewById(R.id.bedSpinner);
        ArrayAdapter<BedType> adapterBed = new ArrayAdapter<BedType>(getApplicationContext(), R.layout.spinner_item, BedType.values());
        bedSpinner.setAdapter(adapterBed);
        bedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBed = adapterView.getItemAtPosition(i).toString();
                typeBed = BedType.valueOf(selectedBed);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.spinner_item, City.values());
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCity = adapterView.getItemAtPosition(i).toString();
                city = City.valueOf(selectedCity);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        roomCreateButton = findViewById(R.id.createRoomButton);
        roomCreateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                List<Room> room = createRoom();
            }
        });
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoomActivity.this, MeActivity.class);
                startActivity(move);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acDetail:
                if (ac.isChecked())
                    facility.add(Facility.AC);
                break;
            case R.id.bathtubDetail:
                if (bathup.isChecked())
                    facility.add(Facility.Bathtub);
                break;
            case R.id.balconyDetail:
                if (balcony.isChecked())
                    facility.add(Facility.Balcony);
                break;
            case R.id.wifiDetail:
                if (wifi.isChecked())
                    facility.add(Facility.WiFi);
                break;
            case R.id.refrigeratorDetail:
                if (refrigerator.isChecked())
                    facility.add(Facility.Refrigerator);
                break;
            case R.id.restaurantDetail:
                if(restaurant.isChecked())
                    facility.add(Facility.Restaurant);
                break;
            case R.id.fitnessCenterDetail:
                if(fitnessCenter.isChecked())
                    facility.add(Facility.FitnessCenter);
                break;
            case R.id.swimming_poolDetail:
                if(swimmingPool.isChecked())
                    facility.add(Facility.SwimmingPool);
                break;
        }
    }



    protected List<Room> createRoom(){
        mApiService.createRoom(MainActivity.idLog, nameCreate.getText().toString(), Integer.parseInt(sizeCreate.getText().toString()),
                Integer.parseInt(priceCreate.getText().toString()), facility, city, addressCreate.getText().toString(), typeBed).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    Room room;
                    room = response.body();
                    Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Room already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}