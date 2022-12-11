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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity class to confirm room order
 */
public class ConfirmRoomOrderActivity extends AppCompatActivity {
    TextView name, renter, address, city, roomName, BedType, from, to, price;
    Button confirm, cancel;
    BaseApiService mApiService;
    Context mcontext;
    private double priceDisplay;
    /**
     * Method to make activity's layout
     * @param savedInstanceState instance's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_room_order);
        mApiService = UtilsApi.getApiService();
        mcontext = this;
        name = findViewById(R.id.BuyerConfirm);
        renter = findViewById(R.id.RenterConfirm);
        address = findViewById(R.id.AddressConfirm);
        city = findViewById(R.id.CityConfirm);
        roomName = findViewById(R.id.roomNameConfirm);
        BedType = findViewById(R.id.BedConfirm);
        from = findViewById(R.id.fromDateConfirm);
        to = findViewById(R.id.toDateConfirm);
        price = findViewById(R.id.PriceConfirm);
        confirm = findViewById(R.id.AcceptOrder);
        cancel = findViewById(R.id.cancelOrder);
        if(MainActivity.isOnGoing != 1){
            cancel.setVisibility(View.INVISIBLE);
            double roomPrice =(DetailRoomActivity.selectedRoom.price.price);
            String fromDate = DetailRoomActivity.fromDate;
            String toDate = DetailRoomActivity.toDate;
            priceDisplay = getTotalPrice(roomPrice, fromDate, toDate);
            name.setText("Name: " + MainActivity.nameLog);
            renter.setText("Renter: " + MainActivity.accountList.get(MainActivity.roomList.get(DetailRoomActivity.roomListSelected).accountId).name);
            address.setText("Address: " + DetailRoomActivity.selectedRoom.address);
            city.setText("City: " + DetailRoomActivity.selectedRoom.city.toString());
            roomName.setText("Room : " + DetailRoomActivity.selectedRoom.name);
            BedType.setText("Bed Type: " + DetailRoomActivity.selectedRoom.bedType.toString());
            from.setText("From : " + fromDate);
            to.setText("To: " + toDate);
            price.setText("Price: Rp " + priceDisplay);
            confirm.setVisibility(View.VISIBLE);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPayment();
                }
            });
        }else if(MainActivity.isOnGoing == 1){
            confirm.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.VISIBLE);
            double roomPrice = MainActivity.allRoomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).price.price;
            String fromDate = MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).from.toString();
            String toDate = MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).to.toString();
            priceDisplay = getTotalPrice(roomPrice, fromDate, toDate);
            name.setText("Name: " + MainActivity.nameLog);
            renter.setText("Renter: " + MainActivity.accountList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).renterId).name);
            address.setText("Address: " + MainActivity.allRoomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).address);
            city.setText("City: " + MainActivity.allRoomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).city);
            roomName.setText("Room : " + MainActivity.allRoomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).name);
            BedType.setText("Bed Type: " + MainActivity.allRoomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).bedType);
            from.setText("From: " + fromDate);
            to.setText("From: " + toDate);
            price.setText("Price: " + priceDisplay);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mApiService.cancel(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).id).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Toast.makeText(mcontext, "Pesanan telah dibatalkan", Toast.LENGTH_SHORT);
                            startActivity(new Intent(ConfirmRoomOrderActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(mcontext, "Gagal membatalkan pesanan", Toast.LENGTH_SHORT);
                        }
                    });
                }
            });
        }

    }

    public double getTotalPrice(double price, String from, String to){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFrom = dateFormat.parse(from);
            Date dateTo = dateFormat.parse(to);
            long duration = TimeUnit.DAYS.convert(dateTo.getTime() - dateFrom.getTime(), TimeUnit.MILLISECONDS);
            System.out.println("Total Duration: " + duration);
            return price * duration;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return price;
    }

    /**
     * Method to create a new room order
     * @return null
     */
    protected Payment createPayment(){
        mApiService.createPayment(MainActivity.idLog, DetailRoomActivity.selectedRoom.accountId, DetailRoomActivity.selectedRoom.id,
                DetailRoomActivity.fromDate, DetailRoomActivity.toDate).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                Toast.makeText(mcontext, "Payment Berhasil", Toast.LENGTH_SHORT).show();
                Intent backtoMain = new Intent(ConfirmRoomOrderActivity.this, MainActivity.class);
                startActivity(backtoMain);
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(mcontext, "Payment Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }
}