package com.LaodeAlifJsleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.LaodeAlifJsleepFN.Renter;
import com.LaodeAlifJsleepFN.Room;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    String name;
    EditText page;
    Button next, prev, go;
    public static String emailLog;
    public static String passwordLog;
    public static String balanceLog;
    public static String nameLog;
    public static Renter renter;
    public static int idLog;
    ArrayList<Room> roomList = new ArrayList<Room>();
    ArrayList<String> nameList = new ArrayList<>();
    int currpage = 0;
    int pagesize = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        page = findViewById(R.id.findPage);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        getAllRoom(currpage, pagesize);
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(arrA);
        next = findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currpage += 1;
                getAllRoom(currpage, pagesize);
            }
        });
        prev = findViewById(R.id.prevButton);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currpage -= 1;
            }
        });
        go = findViewById(R.id.goButton);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currpage = Integer.parseInt(page.getText().toString());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                return super.onOptionsItemSelected(item);
            case R.id.person_button:
                startActivity(new Intent(this, MeActivity.class));
                return true;
            case R.id.add_button:
                startActivity(new Intent(this, RegisterActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    protected List<Room> getAllRoom(int page, int pagesize){
        mApiService.getAllRoom(page, pagesize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    if(roomList != null)
                        roomList.clear();
                    roomList.addAll(response.body());
                    for(Room i : roomList){
                        nameList.add(i.name);
                    }
                    Collections.sort(nameList);

                    System.out.println(roomList);
                    List<String> nameList = new ArrayList<>();
                    for (Room i : roomList){
                        nameList.add(i.name);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Room not found", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}