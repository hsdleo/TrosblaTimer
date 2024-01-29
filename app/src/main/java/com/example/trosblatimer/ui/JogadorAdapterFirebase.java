package com.example.trosblatimer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trosblatimer.R;
import com.example.trosblatimer.database.Jogador;

import java.util.ArrayList;

import kotlin.reflect.KParameter;

public class JogadorAdapterFirebase extends RecyclerView.Adapter<JogadorAdapterFirebase.ViewHolder> {

    Context context;

    ArrayList<Jogador> listaJogadores;


    public JogadorAdapterFirebase(Context context, ArrayList<Jogador> listaJogadores) {
        this.context = context;
        this.listaJogadores = listaJogadores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nome.setText(listaJogadores.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return listaJogadores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textView);
        }
    }
}
