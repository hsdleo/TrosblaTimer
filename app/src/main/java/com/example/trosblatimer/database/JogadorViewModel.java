package com.example.trosblatimer.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JogadorViewModel extends AndroidViewModel {

    private JogadorRepository mRepository;

    private final LiveData<List<Jogador>> mAllJogadores;

    public JogadorViewModel(Application application) {
        super(application);
        mRepository = new JogadorRepository(application);
        mAllJogadores = mRepository.getAllJogadores();
    }

    public LiveData<List<Jogador>> getAllJogadores() {
        return mAllJogadores;
    }

    public void insert(Jogador jogador) {
        mRepository.insert(jogador);
    }
}