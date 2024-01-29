package com.example.trosblatimer.ui;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.trosblatimer.database.Jogador;

public class JogadorListAdapter extends ListAdapter<Jogador, JogadorViewHolder> {

    public JogadorListAdapter(@NonNull DiffUtil.ItemCallback<Jogador> diffCallback) {
        super(diffCallback);
    }

    @Override
    public JogadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return JogadorViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(JogadorViewHolder holder, int position) {
        Jogador current = getItem(position);
        holder.bind(current.getNome());
    }

    public static class JogadorDiff extends DiffUtil.ItemCallback<Jogador> {

        @Override
        public boolean areItemsTheSame(@NonNull Jogador oldItem, @NonNull Jogador newItem) {
            Log.d("debug_recycler","item the same");
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Jogador oldItem, @NonNull Jogador newItem) {
            Log.d("debug_recycler","contents the same");
            return oldItem.getNome().equals(newItem.getNome());
        }
    }
}
