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
    void update(Pet pet);

    @Query("SELECT * FROM pet_table WHERE id == :id")
    Pet getPet(int id);

    @Delete
    void delete(Pet pet);

}
