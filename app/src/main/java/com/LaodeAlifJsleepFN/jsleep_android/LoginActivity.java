package com.LaodeAlifJsleepFN.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Login class activity
 */
public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;
    TextView JTitle, SleepTitle, subtitle;
    public static int filterUsed = 0;
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button loginButton = findViewById(R.id.loginButton);
        Button register = findViewById(R.id.registerNow);
        username = findViewById(R.id.usernameTextBoxLogin);
        password = findViewById(R.id.passwordTextBoxLogin);
        JTitle = findViewById(R.id.JTitle);
        SleepTitle = findViewById(R.id.SleepTitle);
        subtitle = findViewById(R.id.subTitle);
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

    /**
     * Method to get account that matched with login info
     * @return null
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
