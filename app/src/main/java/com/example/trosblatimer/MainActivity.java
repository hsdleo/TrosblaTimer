package com.example.trosblatimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trosblatimer.database.Jogador;
import com.example.trosblatimer.database.JogadorViewModel;
import com.example.trosblatimer.ui.JogadorAdapterFirebase;
import com.example.trosblatimer.ui.JogadorListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private JogadorViewModel mJogadorViewModel;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);
        database = FirebaseDatabase.getInstance();


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView recyclerViewFirebase = findViewById(R.id.recyclerview_firebase);
        recyclerViewFirebase.setLayoutManager(new LinearLayoutManager(this));

        final JogadorListAdapter adapter = new JogadorListAdapter(new JogadorListAdapter.JogadorDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mJogadorViewModel = new ViewModelProvider(this).get(JogadorViewModel.class);
        mJogadorViewModel.getAllJogadores().observe(this, jogadores -> {
            adapter.submitList(jogadores);
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NovoJogadorActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        database.getReference().child("jogador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Jogador> listaJogadores = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Jogador jogador = dataSnapshot.getValue(Jogador.class);
                    Objects.requireNonNull(jogador).setKey(dataSnapshot.getKey());
                    listaJogadores.add(jogador);
                }

                JogadorAdapterFirebase adapterFirebase = new JogadorAdapterFirebase(MainActivity.this, listaJogadores);
                recyclerViewFirebase.setAdapter(adapterFirebase);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Jogador jogador = new Jogador(data.getStringExtra(NovoJogadorActivity.EXTRA_REPLY));
            mJogadorViewModel.insert(jogador);
            database.getReference().child("jogador").push().setValue(jogador).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Salvo no firebase com sucesso",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}