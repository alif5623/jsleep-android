package com.LaodeAlifJsleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MeActivity extends AppCompatActivity {
    TextView name, email, balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        name = findViewById(R.id.namedisplay);
        email = findViewById(R.id.emaildisplay);
        balance = findViewById(R.id.balancedisplay);

        name.setText(MainActivity.nameLog);
        email.setText(MainActivity.emailLog);
        balance.setText(MainActivity.balanceLog);
    }
}