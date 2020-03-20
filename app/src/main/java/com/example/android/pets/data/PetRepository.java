package com.example.android.pets.data;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public class PetRepository {

    private PetDao mPetDao;
    private LiveData<List<Pet>> mAllPets;


    public PetRepository(Application application) {
        PetsDatabase db = PetsDatabase.getDatabase(application);
        mPetDao = db.petDao();
        mAllPets = mPetDao.getPets();
    }

    public LiveData<List<Pet>> getAllPets() {
        return mAllPets;
    }


    public void insert(final Pet pet) {
        PetsDatabase.databaseWriteExecutor.execute(() -> mPetDao.insert(pet));
    }

    public void delete(final Pet pet) {
        PetsDatabase.databaseWriteExecutor.execute(() -> mPetDao.delete(pet));
    }

    public void update(final Pet pet) {
        PetsDatabase.databaseWriteExecutor.execute(() -> mPetDao.update(pet));
    }

    public Future<Pet> getPet(final int id) {
        return PetsDatabase.databaseWriteExecutor.submit(() -> mPetDao.getPet(id));
    }
}
