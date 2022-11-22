package com.LaodeAlifJsleepFN.jsleep_android.request;

import com.LaodeAlifJsleepFN.Account;
import retrofit2.Call;
import retrofit2.http.*;


public interface BaseApiService {
   /* @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);*/
    @POST("account/login")
    Call<Account> getAccount(@Query("email") String email, @Query("password") String password);
    @POST("account/register")
    Call<Account> registerAccount(@Query("name") String name, @Query("email") String email, @Query("password") String password);
}
