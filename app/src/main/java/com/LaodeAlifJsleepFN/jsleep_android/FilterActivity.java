package com.LaodeAlifJsleepFN.jsleep_android;

import static com.LaodeAlifJsleepFN.jsleep_android.MainActivity.nameList;

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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.BedType;
import com.LaodeAlifJsleepFN.City;
import com.LaodeAlifJsleepFN.Facility;
import com.LaodeAlifJsleepFN.Room;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner filter;
    TextView firstFilter, secondFilter;
    EditText minSize, maxSize, minPrice, maxPrice;
    Spinner city, bedType;
    Button applyFilter;
    CheckBox ac, bathup, balcony, wifi, refrigerator, restaurant, fitnessCenter, swimmingPool;
    City cityFilter;
    BedType bedFilter;
    private static ArrayList<Facility> facilityFilter = new ArrayList<>();
    private static ArrayList<Room> filteredRoom = new ArrayList<>();
    BaseApiService mApiService;
    Context mcontext;
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mApiService = UtilsApi.getApiService();
        mcontext = this;
        applyFilter = findViewById(R.id.applyFilter);
        firstFilter = findViewById(R.id.firstFilter);
        secondFilter = findViewById(R.id.secondFIlter);
        filter = (Spinner) findViewById(R.id.spinnerFilter);
        bedType = (Spinner) findViewById(R.id.spinnerBed);
        minSize = findViewById(R.id.minSizeInput);
        minSize.setText("0");
        maxSize = findViewById(R.id.maxSizeInput);
        maxSize.setText("0");
        city = (Spinner) findViewById(R.id.spinnerCity);
        minPrice = findViewById(R.id.minPriceInput);
        minPrice.setText("0");
        maxPrice = findViewById(R.id.maxPriceInput);
        maxPrice.setText("0");
        ac = findViewById(R.id.acFilter);
        ac.setOnClickListener(this::onClick);
        bathup = findViewById(R.id.bathupFilter);
        bathup.setOnClickListener(this::onClick);
        balcony = findViewById(R.id.balconyFilter);
        balcony.setOnClickListener(this::onClick);
        wifi = findViewById(R.id.wifiFilter);
        wifi.setOnClickListener(this::onClick);
        refrigerator = findViewById(R.id.refrigeratorFilter);
        refrigerator.setOnClickListener(this::onClick);
        restaurant = findViewById(R.id.restaurantFilter);
        restaurant.setOnClickListener(this::onClick);
        fitnessCenter = findViewById(R.id.fitnessCenterFilter);
        fitnessCenter.setOnClickListener(this::onClick);
        swimmingPool = findViewById(R.id.swimmingPoolFilter);
        swimmingPool.setOnClickListener(this::onClick);
        ArrayAdapter<Filter> adapterFilter = new ArrayAdapter<Filter>(getApplicationContext(), android.R.layout.simple_spinner_item, Filter.values());
        filter.setAdapter(adapterFilter);
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i) == (Filter.BEDTYPE)){
                    invisEverything();
                    firstFilter.setText("Bed Type: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    bedType.setAdapter(new ArrayAdapter<BedType>(getApplicationContext(), android.R.layout.simple_spinner_item, BedType.values()));
                    bedType.setVisibility(View.VISIBLE);
                    bedType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterBed, View view, int pos, long l) {
                            bedFilter = BedType.valueOf(adapterBed.getItemAtPosition(pos).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterBed) {
                        }
                    });
                }
                if(adapterView.getItemAtPosition(i) == (Filter.SIZE)){
                    invisEverything();
                    firstFilter.setText("Minimum Size: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    minSize.setVisibility(View.VISIBLE);

                    secondFilter.setText("Maximum Size: ");
                    secondFilter.setVisibility(View.VISIBLE);
                    maxSize.setVisibility(View.VISIBLE);
                }
                if(adapterView.getItemAtPosition(i) == (Filter.CITY)){
                    invisEverything();
                    firstFilter.setText("City: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    city.setAdapter(new ArrayAdapter<City>(getApplicationContext(), android.R.layout.simple_spinner_item, City.values()));
                    city.setVisibility(View.VISIBLE);
                    city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterCity, View view, int pos, long l) {
                            cityFilter = City.valueOf(adapterCity.getItemAtPosition(pos).toString());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterCity) {
                        }
                    });
                }
                if(adapterView.getItemAtPosition(i) == (Filter.PRICE)){
                    invisEverything();
                    firstFilter.setText("Minimum Price: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    minPrice.setVisibility(View.VISIBLE);
                    secondFilter.setText("Maximum Price: ");
                    secondFilter.setVisibility(View.VISIBLE);
                    maxPrice.setVisibility(View.VISIBLE);
                }
                if(adapterView.getItemAtPosition(i) == Filter.FACILITY){
                    invisEverything();
                    if(FilterActivity.facilityFilter != null){
                        FilterActivity.facilityFilter.clear();
                    }
                    firstFilter.setText("Facility: ");
                    ac.setVisibility(View.VISIBLE);
                    bathup.setVisibility(View.VISIBLE);
                    balcony.setVisibility(View.VISIBLE);
                    wifi.setVisibility(View.VISIBLE);
                    refrigerator.setVisibility(View.VISIBLE);
                    restaurant.setVisibility(View.VISIBLE);
                    fitnessCenter.setVisibility(View.VISIBLE);
                    swimmingPool.setVisibility(View.VISIBLE);
                    if(ac.isChecked())
                        System.out.println("AAAAAAAAAAA ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomFilter();
                System.out.println("memek");
            }
        });

    }

    /**
     * Method to fill facility list from checkboxes input
     * @param view view
     */
    @Override
    public void onClick(View view) {
        System.out.println("MAsuk tidak sih");
        switch (view.getId()) {
            case R.id.acFilter:
                if (ac.isChecked())
                    System.out.println("AAAAAACCCCC");
                    FilterActivity.facilityFilter.add(Facility.AC);
                break;
            case R.id.bathupFilter:
                if (bathup.isChecked())
                    FilterActivity.facilityFilter.add(Facility.Bathtub);
                break;
            case R.id.balconyFilter:
                if (balcony.isChecked())
                    FilterActivity.facilityFilter.add(Facility.Balcony);
                break;
            case R.id.wifiFilter:
                if (wifi.isChecked())
                    FilterActivity.facilityFilter.add(Facility.WiFi);
                break;
            case R.id.refrigeratorFilter:
                if (refrigerator.isChecked())
                    FilterActivity.facilityFilter.add(Facility.Refrigerator);
                break;
            case R.id.restaurantFilter:
                if(restaurant.isChecked())
                    FilterActivity.facilityFilter.add(Facility.Restaurant);
                break;
            case R.id.fitnessCenterFilter:
                if(fitnessCenter.isChecked())
                    FilterActivity.facilityFilter.add(Facility.FitnessCenter);
                break;
            case R.id.swimmingPoolFilter:
                if(swimmingPool.isChecked())
                    FilterActivity.facilityFilter.add(Facility.SwimmingPool);
                break;
        }
    }

    /**
     * Method to apply selected room filter
     * @return null
     */
    protected List<Room> roomFilter(){
        System.out.println("Facility filter: ");
        System.out.println(FilterActivity.facilityFilter);
        mApiService.getFilteredRoom(MainActivity.currpage, MainActivity.pagesize, Double.parseDouble(minPrice.getText().toString()), Double.parseDouble(maxPrice.getText().toString()), cityFilter, bedFilter,
                Integer.parseInt(minSize.getText().toString()), Integer.parseInt(maxSize.getText().toString()), FilterActivity.facilityFilter).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                LoginActivity.filterUsed = 1;
                if(response.isSuccessful()){
                    nameList.clear();
                    filteredRoom.clear();
                    filteredRoom.addAll(response.body());
                    for(Room i : filteredRoom){
                        MainActivity.nameList.add(i.name);
                    }
                    System.out.println("name list sini : " + nameList);
                    Collections.sort(nameList);
                }

                System.out.println("Name list: ");
                System.out.println(nameList);
                startActivity(new Intent(FilterActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                System.out.println("Gagal: " + t);
            }
        });
        return null;
    }

    /**
     * Method to hide every unselected filter's element
     */
    public void invisEverything(){
        minSize.setVisibility(View.INVISIBLE);
        maxSize.setVisibility(View.INVISIBLE);
        city.setVisibility(View.INVISIBLE);
        minPrice.setVisibility(View.INVISIBLE);
        maxPrice.setVisibility(View.INVISIBLE);
        bedType.setVisibility(View.INVISIBLE);
        secondFilter.setVisibility(View.INVISIBLE);
        ac.setVisibility(View.INVISIBLE);
        bathup.setVisibility(View.INVISIBLE);
        balcony.setVisibility(View.INVISIBLE);
        wifi.setVisibility(View.INVISIBLE);
        refrigerator.setVisibility(View.INVISIBLE);
        restaurant.setVisibility(View.INVISIBLE);
        fitnessCenter.setVisibility(View.INVISIBLE);
        swimmingPool.setVisibility(View.INVISIBLE);
    }


    public enum Filter{
        SIZE, PRICE, FACILITY, CITY, BEDTYPE
    }
}

