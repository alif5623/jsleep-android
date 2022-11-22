package com.LaodeAlifJsleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.LaodeAlifJsleepFN.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    String name;
    public static String emailLog;
    public static String passwordLog;
    public static String balanceLog;
    public static String nameLog;
    static ArrayList<Room> roomList = new ArrayList<Room>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();
        try{
            InputStream filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] rooms = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, rooms);
        }catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<String> names = new ArrayList<String>();
        for(Room room : roomList){
            names.add(room.name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,
                names
        );
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(arrayAdapter);

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

}