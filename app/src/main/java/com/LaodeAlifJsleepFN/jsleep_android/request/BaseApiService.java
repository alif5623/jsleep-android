package com.LaodeAlifJsleepFN.jsleep_android.request;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.Renter;
import com.LaodeAlifJsleepFN.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;


public interface BaseApiService {
   /* @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);*/
    @POST("account/login")
    Call<Account> getAccount(@Query("email") String email, @Query("password") String password);
    @POST("account/register")
    Call<Account> registerAccount(@Query("name") String name, @Query("email") String email, @Query("password") String password);
    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id, @Query("username") String username, @Query("address") String address, @Query("phoneNumber") String phoneNumber);
    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize")int pageSize);
}
