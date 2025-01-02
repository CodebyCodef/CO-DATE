package com.example.co_date.network;
import com.example.co_date.model.User;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("home")
    Call<List<User>> getUsers();
}
