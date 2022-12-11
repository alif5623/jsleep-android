package com.LaodeAlifJsleepFN.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.Renter;
import com.LaodeAlifJsleepFN.Room;
import com.LaodeAlifJsleepFN.jsleep_android.model.Payment;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    BaseApiService mApiService;
    Context mContext;
    String name;
    EditText page;
    Button next, prev, go, filter;
    TextView noOnGoing;
    ImageView noRoomIcon;
    public static String emailLog;
    public static String passwordLog;
    public static String balanceLog;
    public static String nameLog;
    public static Renter renter;
    public static int isFiltered;
    public static int idLog;
    public static int listSelected;
    public static int roomSelected;
    public static int paymentSelected;
    private ListView lv, onGoinglv;
    private SearchView searchBar;
    private String searchedRoom;
    public static int isOnGoing; //Nandain kalo yang dipilih list onGoing
    public static ArrayList<Room> allRoomList = new ArrayList<>();
    public static ArrayList<Room> roomList = new ArrayList<Room>();
    public static ArrayList<Room> roomSearchList = new ArrayList<>();
    public static ArrayList<Account> accountList = new ArrayList<>();
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<String> onGoingList = new ArrayList<>();
    public static ArrayList<Payment> paymentListonGoing = new ArrayList<Payment>();
    public static int currpage = 0;
    public static int pagesize = 7;
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        page = findViewById(R.id.findPage);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listView);
        onGoinglv = findViewById(R.id.onGoingLv);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        getAllRoom();
        if(LoginActivity.filterUsed == 0)
            getPaginatedRoom(currpage, pagesize);
        getOnGoingPayment();
        searchBar = (SearchView) findViewById(R.id.searchBar);
        filter = findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });
        System.out.println("Id: " + idLog);
        next = findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currpage ++;
                getPaginatedRoom(currpage, pagesize);
            }
        });
        prev = findViewById(R.id.prevButton);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currpage -= 1;
                getPaginatedRoom(currpage, pagesize);
            }
        });
        go = findViewById(R.id.goButton);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currpage = Integer.parseInt(page.getText().toString()) - 1;
                System.out.println("currpage: " + currpage);
                getPaginatedRoom(currpage, pagesize);
            }
        });
        mApiService.getAllACcount().enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if(response.isSuccessful()) {
                    if (accountList != null)
                        accountList.clear();
                    accountList.addAll(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                Toast.makeText(mContext, "No Account found", Toast.LENGTH_SHORT);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isOnGoing = 0;
                listSelected = i;
                System.out.println("Selected: " + nameList.get(listSelected));
                for(Room j : roomList){
                    if(j.name.equals(nameList.get(listSelected))){
                        roomSelected = j.id;
                        System.out.println("Selected id: " + roomSelected);
                    }
                }
                Intent toDetailRoom = new Intent(MainActivity.this, DetailRoomActivity.class);
                startActivity(toDetailRoom);
                Toast.makeText(mContext, "Room : "+ i, Toast.LENGTH_SHORT).show();

            }
        });
        onGoinglv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int onGoingRoomSelected;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int onGoingSelected, long l) {
                isOnGoing = 1;
                for(Room i : allRoomList){
                    if(i.name.equals(onGoingList.get(onGoingSelected)))
                        onGoingRoomSelected = i.id;
                }
                for(Payment i : paymentListonGoing){
                    if(i.getRoomId() == onGoingRoomSelected && i.buyerId == idLog)
                        paymentSelected = i.id;
                        startActivity(new Intent(MainActivity.this, ConfirmRoomOrderActivity.class));
                }
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchedRoom = s;
                System.out.println(s);
                mApiService.searchRoom(searchedRoom, currpage, pagesize).enqueue(new Callback<List<Room>>() {
                    @Override
                    public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                        if(response.isSuccessful()){
                            System.out.println("masuk tidak");
                            if(roomSearchList != null)
                                roomSearchList.clear();
                            roomSearchList.addAll(response.body());
                            nameList.clear();
                            for(Room i : roomSearchList){
                                nameList.add(i.name);
                            }
                            System.out.println("Name list baru: " + nameList);
                            Collections.sort(nameList);
                        }
                        displayList(currpage);
                    }

                    @Override
                    public void onFailure(Call<List<Room>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        System.out.println("Renter main : ");
        System.out.println(renter);
    }

    /**
     * Method to assign function to top menu's item
     * @param item top menu item
     * @return selected item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                startActivity(new Intent(this, MeActivity.class));
                return true;
            case R.id.add_button:
                startActivity(new Intent(this, RegisterActivity.class));
                return true;
            case R.id.notif_button:
                startActivity(new Intent(this, AcceptOrderActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method to display logged acccount payment in WAITING status
     */
    public void displayOnGoing(){
        noRoomIcon = findViewById(R.id.noRoomIcon);
        noOnGoing = findViewById(R.id.noOrderFound);
        if(onGoingList.isEmpty()){
            noRoomIcon.setVisibility(View.VISIBLE);
            onGoinglv.setVisibility(View.INVISIBLE);
            noOnGoing.setVisibility(View.VISIBLE);
        }else{
            noRoomIcon.setVisibility(View.INVISIBLE);
            noOnGoing.setVisibility(View.INVISIBLE);
            onGoinglv.setVisibility(View.VISIBLE);
            ArrayAdapter<String> onGoingRoom = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, onGoingList);
            onGoinglv.setAdapter(onGoingRoom);
        }

    }

    /**
     * Method to display list of room's name
     * @param currpage is the current page number
     */
    public void displayList(int currpage){
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        lv.setAdapter(arrA);
    }

    /**
     * Method to create top menu
     * @param menu topmenu
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    /**
     * Method to get payment with WAITING status in payment.json
     * @return null
     */

    protected List<Payment> getOnGoingPayment(){
        mApiService.getOnGoingPayment(MainActivity.idLog).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> responsePayment) {
                System.out.println("Respon: " + responsePayment);
                if(paymentListonGoing != null)
                    paymentListonGoing.clear();
                if(onGoingList != null)
                    onGoingList.clear();
                if(!responsePayment.body().isEmpty()){
                    System.out.println("kok masuk?");
                    paymentListonGoing.addAll(responsePayment.body());
                    System.out.println("payment di sini: " + paymentListonGoing.get(0).buyerId) ;
                    for(int i = 0; i < paymentListonGoing.size(); i++) {
                        System.out.println("masuk ke " + i);
                        for (int j = 0; j < allRoomList.size(); j++) {
                            if (allRoomList.get(j).id == paymentListonGoing.get(i).getRoomId())
                                onGoingList.add(allRoomList.get(j).name + " (" + paymentListonGoing.get(i).status + ")");
                        }
                    }
                }
                displayOnGoing();

            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

    /**
     * Method to get all registered room from room.json
     * @param page is page number
     * @param pagesize is the size of the page
     * @return null
     */
    protected List<Room> getPaginatedRoom(int page, int pagesize){
        if(isFiltered != 1){
            if(roomList != null)
                roomList.clear();
            if(nameList !=null)
                nameList.clear();
        }

        mApiService.getPaginatedRoom(page, pagesize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    roomList.addAll(response.body());
                    for(Room i : roomList){
                        nameList.add(i.name);
                    }
                    Collections.sort(nameList);
                }
                displayList(page);
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Room not found", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected List<Room> getAllRoom(){
        mApiService.getAllRoom().enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    if(allRoomList != null)
                        allRoomList.clear();
                    allRoomList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Room not found", Toast.LENGTH_SHORT);
            }
        });
        return null;
    }

}