package com.example.trosblatimer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JogadorDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Jogador jogador);

    @Query("DELETE FROM jogador")
    void deleteAll();

    @Query("SELECT * FROM jogador ORDER BY nome ASC")
    LiveData<List<Jogador>> getAlphabetizedJogadores();
}