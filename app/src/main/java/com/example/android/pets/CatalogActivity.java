package com.example.android.pets;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.pets.adapter.PetListAdapter;
import com.example.android.pets.data.Pet;
import com.example.android.pets.viewmodel.PetViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;


/**
 * Displays list of allPets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private PetViewModel mPetViewModel;

    SearchView mSearchView;

    private PetListAdapter mAdapter;

    View mEmptyShelter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        setUI();

        mAdapter = new PetListAdapter(this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPetViewModel = new ViewModelProvider(this).get(PetViewModel.class);

        // Update the cached copy of the Pets in the mAdapter.
        mPetViewModel.getAllPets().observe(this, pets -> {

            mAdapter.setPets(pets);

            if(mAdapter.getItemCount() != 0)
                mEmptyShelter.setVisibility(View.GONE);
            else mEmptyShelter.setVisibility(View.VISIBLE);
        });


        setSearchView();

    }

    void setSearchView() {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        assert searchManager != null;
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (containsName(PetListAdapter.allPets, s.toUpperCase())) {
                    mAdapter.getFilter().filter(s);
                } else {
                    Toast.makeText(getBaseContext(),
                            "Not found",
                            Toast.LENGTH_LONG)
                            .show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    void setUI() {

        mEmptyShelter = findViewById(R.id.empty_shelter);
        mSearchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerview);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    boolean containsName(List<Pet> list, String name){
        for (Pet pet :
                list) {
            if(pet.getName().toUpperCase().contains(name))
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.setQuery("", false);
        mSearchView.clearFocus();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Pet pet = new Pet(
                    0,
                    Objects.requireNonNull(data.getStringExtra("Name")),
                    Objects.requireNonNull(data.getStringExtra("Breed")),
                    data.getIntExtra("Gender", 0),
                    data.getIntExtra("Weight", 0),
                    data.getByteArrayExtra("Image"));


            mPetViewModel.insert(pet);

        } else if(resultCode != RESULT_OK) {

            Toast.makeText(
                    getApplicationContext(),
                    "Not Saved",
                    Toast.LENGTH_LONG).show();
        }

    }

}