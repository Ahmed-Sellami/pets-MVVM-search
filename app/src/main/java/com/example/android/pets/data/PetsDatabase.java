package com.example.android.pets.data;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Pet.class}, version = 1, exportSchema = false)
public abstract class PetsDatabase extends RoomDatabase {

    public abstract PetDao petDao();

    private static volatile PetsDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PetsDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (PetsDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PetsDatabase.class, "pet_database")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}
