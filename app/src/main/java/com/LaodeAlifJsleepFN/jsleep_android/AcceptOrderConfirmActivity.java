package com.LaodeAlifJsleepFN.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.jsleep_android.model.Payment;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity to accept order that's been selected in AcceptOrderActivity
 */
public class AcceptOrderConfirmActivity extends AppCompatActivity {
    TextView name, buyerid, room, bed, from, to, price;
    Button acceptOrder;
    BaseApiService mApiService;
    Context mContext;

    /**
     * Method to create activity's layout
     * @param savedInstanceState is the instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order_confirm);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.BuyerConfirm);
        buyerid = findViewById(R.id.buyerIDConfirm);
        room = findViewById(R.id.roomNameConfirm);
        bed = findViewById(R.id.BedConfirm);
        from = findViewById(R.id.fromDateConfirm);
        to = findViewById(R.id.toDateConfirm);
        price = findViewById(R.id.PriceConfirm);
        name.setText("Buyer's name: " + AcceptOrderActivity.customerList.get(AcceptOrderActivity.listSelected));
        buyerid.setText("Buyer's ID: " + AcceptOrderActivity.idSelected); //masih ngaco
        room.setText("Room id: " + AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).getRoomId());
        bed.setText("Bed Type: " + MainActivity.roomList.get(AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).getRoomId()).bedType.toString());
        from.setText("From: " + AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).from.toString()); //masih ngaco
        to.setText("To: " + AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).to.toString());
        price.setText("Price: Rp " + MainActivity.roomList.get(AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).getRoomId()).price.price);
        acceptOrder = findViewById(R.id.AcceptOrder);
        acceptOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * Method to change payment status to Success
             * @param view view
             */
            @Override
            public void onClick(View view) {
                mApiService.acceptPayment(AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).id).enqueue(new Callback<List<Payment>>() {
                    @Override
                    public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                        System.out.println("Accept kontol");
                        Toast.makeText(mContext, "Payment accepted", Toast.LENGTH_SHORT);
                        startActivity(new Intent(AcceptOrderConfirmActivity.this, MainActivity.class));
                    }
                    @Override
                    public void onFailure(Call<List<Payment>> call, Throwable t) {
                        System.out.println("gk keaccept kontol");
                        Toast.makeText(mContext, "Failed to accept order", Toast.LENGTH_SHORT);
                        startActivity(new Intent(AcceptOrderConfirmActivity.this, MainActivity.class));
                    }
                });
            }
        });

    }
}