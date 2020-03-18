package com.example.android.pets.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PetDao {

    @Insert
    void insert(Pet pet);

    @Query("SELECT * from pet_table")
    LiveData<List<Pet>> getPets();

    @Update
    int update(Pet pet);

    @Delete
    void delete(Pet pet);

}
