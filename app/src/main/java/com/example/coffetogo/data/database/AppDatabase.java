package com.example.coffetogo.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartDbModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;
    private static final String DB_NAME = "coffee.db";


    public static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            if (db == null) {
                AppDatabase instance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AppDatabase.class,
                                DB_NAME
                        )
                        .fallbackToDestructiveMigration()
                        .build();
                db = instance;
            }
            return db;
        }
    }

    public abstract CartDao cartDao();
}
