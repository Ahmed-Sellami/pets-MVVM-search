package com.example.android.pets.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet_table")
public class Pet {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String breed;
    private int gender;
    private int weight;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Pet(int id,@NonNull String name,@NonNull String breed, int gender, int weight, byte[] image) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
        this.image = image;
    }
    @NonNull
    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }

    @NonNull
    public String getBreed() {
        return breed;
    }

    public byte[] getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public static final int UNKNOWN = 0;
    public static final int MALE = 1;
    public static final int FEMALE = 2;

}
