package com.LaodeAlifJsleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;
import retrofit2.*;
import com.LaodeAlifJsleepFN.*;
import com.LaodeAlifJsleepFN.Renter;

public class MeActivity extends AppCompatActivity {
    TextView name, email, balance, nameRentDisp, addressDisp, phoneNumDisp;
    Button registRenter, cancelRegist, confirmRegist;
    EditText nameRent, address, phoneNum;
    BaseApiService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        setContentView(R.layout.activity_me);
        name = findViewById(R.id.namedisplay);
        email = findViewById(R.id.emaildisplay);
        balance = findViewById(R.id.balancedisplay);
        registRenter = findViewById(R.id.registerRenter);
        nameRent = findViewById(R.id.nameRenter);
        address = findViewById(R.id.addressRenter);
        phoneNum = findViewById(R.id.phoneNumRenter);
        cancelRegist = findViewById(R.id.cancelRegistRent);
        confirmRegist = findViewById(R.id.confirmRegistRent);
        nameRentDisp = findViewById(R.id.nameRentDisp);
        addressDisp = findViewById(R.id.addressRentDisp);
        phoneNumDisp = findViewById(R.id.phoneRentDisp);
        name.setText(MainActivity.nameLog);
        email.setText(MainActivity.emailLog);
        balance.setText(MainActivity.balanceLog);
        if(MainActivity.renter == null){
            nameRent.setVisibility(View.INVISIBLE);
            address.setVisibility(View.INVISIBLE);
            phoneNum.setVisibility(View.INVISIBLE);
            cancelRegist.setVisibility(View.INVISIBLE);
            confirmRegist.setVisibility(View.INVISIBLE);
            nameRentDisp.setVisibility(View.INVISIBLE);
            phoneNumDisp.setVisibility(View.INVISIBLE);
            addressDisp.setVisibility(View.INVISIBLE);
            registRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameRent.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    phoneNum.setVisibility(View.VISIBLE);
                    cancelRegist.setVisibility(View.VISIBLE);
                    confirmRegist.setVisibility(View.VISIBLE);
                    confirmRegist.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Renter renter = registerRenter();
                        }
                    });
                }
            });
        }else{
            registRenter.setVisibility(View.INVISIBLE);
            nameRentDisp.setText(MainActivity.renter.username);
            addressDisp.setText(MainActivity.renter.address);
            phoneNumDisp.setText(MainActivity.renter.phoneNumber);
            cancelRegist.setVisibility(View.INVISIBLE);
            confirmRegist.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(MainActivity.renter != null){
            switch (item.getItemId()) {
                case R.id.add_button:
                    startActivity(new Intent(MeActivity.this, CreateRoom.class));
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }else{
            switch (item.getItemId()){
                default:
                    Toast.makeText(mContext, "Renter not found", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
    protected Renter registerRenter(){
        mApiService.registerRenter(MainActivity.idLog, nameRent.getText().toString(), address.getText().toString(), phoneNum.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    Renter renter;
                    renter = response.body();
                    Toast.makeText(mContext, "Renter successfully registered", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(MeActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println("Fail");
                Toast.makeText(mContext, "Renter already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menurenter, menu);
        return true;
    }

}