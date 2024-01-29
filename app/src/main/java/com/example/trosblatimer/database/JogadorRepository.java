package com.example.trosblatimer.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class JogadorRepository {

    private JogadorDao mJogadorDao;
    private LiveData<List<Jogador>> mAllJogadores;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    JogadorRepository(Application application) {
        TrosblaDatabase db = TrosblaDatabase.getDatabase(application);
        mJogadorDao = db.jogadorDao();
        mAllJogadores = mJogadorDao.getAlphabetizedJogadores();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Jogador>> getAllJogadores() {
        return mAllJogadores;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Jogador jogador) {
        TrosblaDatabase.databaseWriteExecutor.execute(() -> {
            mJogadorDao.insert(jogador);
        });
    }
}