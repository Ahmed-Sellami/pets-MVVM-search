package com.example.android.pets.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PetViewModel extends AndroidViewModel {

    private PetRepository mRepository;
    private LiveData<List<Pet>> mAllPets;

    public PetViewModel(Application application){
        super(application);
        mRepository = new PetRepository(application);
        mAllPets = mRepository.getAllPets();
    }

    public LiveData<List<Pet>> getAllPets() {
        return mAllPets;
    }

    public Pet getPet(int id){return mRepository.getPet(id);}

    public void insert(Pet pet) {mRepository.insert(pet);}

    public void delete(Pet pet){mRepository.delete(pet);}

    public void update(Pet pet){mRepository.update(pet);}

}
