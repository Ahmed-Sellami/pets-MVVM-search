package com.example.android.pets.viewmodel;

import android.app.Application;

import com.example.android.pets.data.Pet;
import com.example.android.pets.data.PetRepository;
import com.example.android.pets.data.PetsDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PetViewModel extends AndroidViewModel {

    private PetRepository mRepository;
    private LiveData<List<Pet>> mAllPets;

    public PetViewModel(Application application){
        super(application);
        mRepository = new PetRepository(PetsDatabase.getDatabase(application).petDao());
        mAllPets = mRepository.getAllPets();
    }

    public LiveData<List<Pet>> getAllPets() {
        return mAllPets;
    }

    public Pet getPet(int id) {
        Pet pet = null;
        try {
            pet = mRepository.getPet(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pet;
    }

    public void insert(Pet pet) {mRepository.insert(pet);}

    public void delete(Pet pet){mRepository.delete(pet);}

    public void update(Pet pet){mRepository.update(pet);}

}
