package com.LaodeAlifJsleepFN.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class activity to register new account
 */
public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText name, email, password;
    Context mContext;
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.nameTextBoxRegist);
        email = findViewById(R.id.emailTextBoxRegist);
        password = findViewById(R.id.passwordTextBoxRegist);
        Button RegisterButton = findViewById(R.id.registerButton);
        RegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Account account = requestRegister();
            }
        });
    }

    /**
     * Method to write a new registered account into account.json
     * @return null
     */
    protected Account requestRegister(){

        mApiService.registerAccount(name.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Account already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}