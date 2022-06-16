package com.example.coffetogo.data.network;


import com.example.coffetogo.data.network.models.AuthInfo;
import com.example.coffetogo.data.network.models.CatalogItem;
import com.example.coffetogo.data.network.models.CategoryItem;
import com.example.coffetogo.data.network.models.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("login")
    Observable<UserInfo> login(@Body AuthInfo authInfo);

    @GET("catalog/categories")
    Observable<List<CategoryItem>> getCategories();

    @GET("catalog/drinks")
    Observable<List<CatalogItem>> getDrink(@Query("drink_type") String drinkType);
}
