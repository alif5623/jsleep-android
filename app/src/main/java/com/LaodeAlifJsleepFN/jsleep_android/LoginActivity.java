package com.LaodeAlifJsleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;
import com.LaodeAlifJsleepFN.jsleep_android.MainActivity;
import java.sql.SQLOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button loginButton = findViewById(R.id.loginButton);
        TextView register = findViewById(R.id.registerNow);
        username = findViewById(R.id.usernameTextBoxLogin);
        password = findViewById(R.id.passwordTextBoxLogin);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Account account = requestLogin();
            }
        });
    }
    /*
    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id = 0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    */
    protected Account requestLogin(){
            mApiService.getAccount(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if(response.isSuccessful()){
                        Account account;
                        account = response.body();
                        System.out.println(account.toString());
                        Intent move = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(move);
                        MainActivity.emailLog = username.getText().toString();
                        MainActivity.passwordLog = password.getText().toString();
                        MainActivity.nameLog = account.name;
                        MainActivity.balanceLog = String.valueOf(account.balance);
                        MainActivity.renter = account.renter;
                        MainActivity.idLog = account.id;
                    }
                }
                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    Toast.makeText(mContext, "email atau password salah!", Toast.LENGTH_SHORT).show();
                    System.out.println("email atau password salah");
                }
            });
            return null;
    }
}
