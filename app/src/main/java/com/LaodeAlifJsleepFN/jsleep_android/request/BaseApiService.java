package com.LaodeAlifJsleepFN.jsleep_android.request;

import com.LaodeAlifJsleepFN.Account;
import retrofit2.Call;
import retrofit2.http.*;


public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);
    @POST("account/{id}")
    Call<Account> postAccount (@Path("id") int id);
    @PUT("account/{id}")
    Call<Account> putAccount (@Path("id") int id);
    @DELETE("account/{id}")
    Call<Account> deleteAccount (@Path("id") int id);
}
