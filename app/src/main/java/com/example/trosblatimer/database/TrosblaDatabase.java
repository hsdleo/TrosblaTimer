package com.example.trosblatimer.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Jogador.class}, version = 1, exportSchema = false)
public abstract class TrosblaDatabase extends RoomDatabase {

    public abstract JogadorDao jogadorDao();

    private static volatile TrosblaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TrosblaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrosblaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TrosblaDatabase.class, "trosbla_database")
                            .addCallback(sTrosblaDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sTrosblaDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                JogadorDao dao = INSTANCE.jogadorDao();
                dao.deleteAll();

                Jogador jogador = new Jogador("Cruj");
                dao.insert(jogador);
                jogador = new Jogador("Dio");
                dao.insert(jogador);
            });
        }
    };
}