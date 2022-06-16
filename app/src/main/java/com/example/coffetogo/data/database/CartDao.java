package com.example.coffetogo.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface CartDao {

    @Query("SELECT * FROM cart_items ")
    Flowable<List<CartDbModel>> getCartList();

    @Query("SELECT COUNT(*) FROM cart_items")
    Flowable<Integer> getCartCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartDbModel cartItem);

    @Update
    void updateCartItem(CartDbModel cartItem);

    @Query("DELETE FROM cart_items WHERE name=:cartName")
    void deleteCartItem(String cartName);
}
