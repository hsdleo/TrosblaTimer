package com.example.trosblatimer.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trosblatimer.R;

public class JogadorViewHolder extends RecyclerView.ViewHolder {
    private final TextView jogadorItemView;

    private JogadorViewHolder(View itemView) {
        super(itemView);
        jogadorItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        jogadorItemView.setText(text);
    }

    static JogadorViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new JogadorViewHolder(view);
    }
}