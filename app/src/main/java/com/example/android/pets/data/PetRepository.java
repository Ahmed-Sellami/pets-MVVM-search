package com.example.android.pets.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PetRepository {

    private PetDao mPetDao;
    private LiveData<List<Pet>> mAllPets;

    PetRepository(Application application){
        PetsDatabase db = PetsDatabase.getDatabase(application);
        mPetDao = db.petDao();
        mAllPets = mPetDao.getPets();
    }

    LiveData<List<Pet>> getAllPets() {
        return mAllPets;
    }

    Pet getPet(int id){
        for (Pet pet :
                mAllPets.getValue()) {
            if(pet.getId() == id)
                return pet;
        }
        return null;
    }


    void insert(final Pet pet) {
        PetsDatabase.databaseWriteExecutor.execute(() -> mPetDao.insert(pet));
    }
    void delete(final Pet pet){
        PetsDatabase.databaseWriteExecutor.execute(() -> mPetDao.delete(pet));
    }
    void update(final Pet pet){
        PetsDatabase.databaseWriteExecutor.execute(() -> mPetDao.update(pet));
    }

}
