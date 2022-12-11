package com.LaodeAlifJsleepFN.jsleep_android;

import static com.LaodeAlifJsleepFN.jsleep_android.MainActivity.accountList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.jsleep_android.model.Invoice;
import com.LaodeAlifJsleepFN.jsleep_android.model.Payment;
import com.LaodeAlifJsleepFN.jsleep_android.request.BaseApiService;
import com.LaodeAlifJsleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity class to accept new room order
 */
public class AcceptOrderActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    private ListView orderList, completeOrder;
    public static int listSelected, idSelected;
    public static ArrayList<String> customerList;
    public static ArrayList<Payment> watingListPayment;
    public static ArrayList<Account> customerAcc;
    public static ArrayList<Payment> completedPaymentList;
    public static ArrayList<String> customerHistoryList;
    int page = 0;
    int pageSize = 11;

    /**
     * method to make activity layout
     * @param savedInstanceState instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        orderList = findViewById(R.id.OrderList);
        completeOrder = findViewById(R.id.CompleteOrder);
        watingListPayment = new ArrayList<Payment>();
        customerList = new ArrayList<>();
        customerAcc = new ArrayList<>();
        completedPaymentList = new ArrayList<>();
        customerHistoryList = new ArrayList<>();
        getWaitingPayment(page, pageSize);
        getCompletedPayment(page, pageSize);
        System.out.println("Size customer acc: " + customerAcc.size());
        System.out.println("List akun: ");
        System.out.println(accountList);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listSelected = i;
                System.out.println("List selected : " + listSelected);
                System.out.println("size customer lsit: " + customerList.size());
                System.out.println("size account list " + accountList.size());
                for(int k = 0; k < accountList.size(); k++){
                    if(customerList.get(i).equals(accountList.get(k).name)){
                        System.out.println("ID Sama : " + accountList.get(k).id);
                        idSelected = accountList.get(k).id;
                    }
                    System.out.println("k = "  + k);
                }
                Toast.makeText(mContext, "Selected: " + i, Toast.LENGTH_SHORT);

                startActivity(new Intent(AcceptOrderActivity.this, AcceptOrderConfirmActivity.class));
            }
        });
        System.out.println(customerAcc);
    }

    /**
     * Method to display payment list
     * @param page is the page number
     */
    public void displayPaymentList(int page){
        for(int i = 0; i < customerList.size(); i++){
            System.out.println("Renter " + i + ": " + customerList.get(i));
        }
        System.out.println("Customer acc size : " + customerAcc.size());
        System.out.println("Customer history size: ");
        ArrayAdapter<String> customerArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerList);
        orderList.setAdapter(customerArray);
        ArrayAdapter<String> historyArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerHistoryList);
        completeOrder.setAdapter(historyArray);
    }

    /**
     * Method to get payment with Waiting status
     * @param page is the page number
     * @param pageSize is the size of the page
     * @return null
     */
    protected List<Payment> getWaitingPayment(int page, int pageSize){
        mApiService.waitingPayment(MainActivity.idLog, page, pageSize).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    if(watingListPayment != null)
                        watingListPayment.clear();
                    watingListPayment.addAll(response.body());
                    System.out.println("List payment: ");
                    System.out.println(watingListPayment);
                    for(int i = 0; i < watingListPayment.size(); i++){
                        for(int j = 0; j < accountList.size(); j++) {
                            if(accountList.get(j).id == watingListPayment.get(i).buyerId)
                                customerList.add(accountList.get(j).name);
                        }
                    }
                    displayPaymentList(page);
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Order list not found", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    /**
     * Method to get payment with Success status
     * @param page is the page number
     * @param pageSize is the size of the page
     * @return null
     */
    protected List<Payment> getCompletedPayment(int page, int pageSize){
        mApiService.completedPayment(MainActivity.idLog, page, pageSize).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    if(completedPaymentList != null)
                        completedPaymentList.clear();
                    completedPaymentList.addAll(response.body());
                    System.out.println("Completed list: ");
                    System.out.println(completedPaymentList);
                    for(int i = 0; i < completedPaymentList.size(); i++){
                        for(int j = 0; j < accountList.size(); j++) {
                            if(accountList.get(j).id == completedPaymentList.get(i).buyerId)
                                if(completedPaymentList.get(i).status == Invoice.PaymentStatus.FAILED){
                                    customerHistoryList.add(accountList.get(j).name + " (Cancelled)");
                                }else{
                                    customerHistoryList.add(accountList.get(j).name);
                                }
                        }
                    }
                    displayPaymentList(page);
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

}