package com.LaodeAlifJsleepFN.jsleep_android.request;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.BedType;
import com.LaodeAlifJsleepFN.City;
import com.LaodeAlifJsleepFN.Facility;
import com.LaodeAlifJsleepFN.Renter;
import com.LaodeAlifJsleepFN.Room;
import com.LaodeAlifJsleepFN.jsleep_android.model.Payment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface BaseApiService {
    @POST("account/login")
    Call<Account> getAccount(@Query("email") String email, @Query("password") String password);
    @POST("account/register")
    Call<Account> registerAccount(@Query("name") String name, @Query("email") String email, @Query("password") String password);
    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id, @Query("username") String username, @Query("address") String address, @Query("phoneNumber") String phoneNumber);
    @GET("room/getPaginatedRoom")
    Call<List<Room>> getPaginatedRoom(@Query("page") int page, @Query("pageSize")int pageSize);
    @POST("room/create")
    Call<Room> createRoom(@Query("accountId") int id, @Query("name") String name, @Query("size") int size, @Query("price") int price,
                                @Query("facility") ArrayList<Facility> facility, @Query("city")City city, @Query("address") String address, @Query("typeBed")BedType typeBed);
    @POST("account/{id}/topUp")
    Call<Boolean> topUp(@Path("id") int id, @Query("balance") double balance);
    @POST("payment/create")
    Call<Payment> createPayment(@Query("buyerId") int buyerId, @Query("renterId") int renterId, @Query("roomId") int roomId, @Query("from") String from, @Query("to") String to);
    @POST("payment/{id}/accept")
    Call<Boolean> acceptPayment(@Path("id") int id);
    @GET("payment/{id}/getWaitingPayment")
    Call<List<Payment>> waitingPayment(@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("payment/{id}/getCompletedPayment")
    Call<List<Payment>> completedPayment(@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("account/{id}/getById")
    Call<Account> getById(@Path("id") int id);
    @GET("account/getAllAccount")
    Call<List<Account>> getAllACcount();
    @GET("room/getFilteredRoom")
    Call<List<Room>> getFilteredRoom(@Query("page") int page, @Query("pageSize") int pageSize, @Query("city") City city, @Query("priceMin") double priceMin, @Query("priceMax") double priceMax,
                                 @Query("sizeMin") int sizeMin, @Query("sizeMax") int sizeMax, @Query("bed") BedType bed, @Query("facility") ArrayList<Facility> facility);
    @GET("payment/{id}/getMyOnGoingPayment")
    Call<List<Payment>> getOnGoingPayment(@Path("id") int id);
    @POST("payment/{id}/cancel")
    Call <Boolean> cancel(@Path("id") int id);
    @GET("room/searchRoom")
    Call<List<Room>> searchRoom(@Query("roomName") String roomName, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom();
}
