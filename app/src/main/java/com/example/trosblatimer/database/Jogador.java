package com.example.trosblatimer.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jogador")
public class Jogador {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "key")
    private String mKey;
    @NonNull
    @ColumnInfo(name = "nome")
    private String mNome;

    public Jogador(@NonNull String nome) {this.mNome = nome;}

    public Jogador() {
    }

    public String getNome(){return this.mNome;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public void setNome(@NonNull String mNome) {
        this.mNome = mNome;
    }
}
