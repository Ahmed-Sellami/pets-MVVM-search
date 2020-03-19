package com.example.android.pets.data;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.Objects;

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

    public Pet getPet(int id) {
        for (Pet pet :
                Objects.requireNonNull(mAllPets.getValue())) {
            if(pet.getId() == id)
                return pet;
        }
        return null;
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

}
