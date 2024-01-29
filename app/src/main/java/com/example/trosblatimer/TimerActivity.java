package com.example.trosblatimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {
    private static final String FORMAT = "%02d:%02d";

    CountDownTimer cTimer = null;
    private TextView tvTimer;
    private boolean isRunning = false;

    private FirebaseDatabase database;

    private Long globalMillisUntilFinished = 30000L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        FirebaseApp.initializeApp(TimerActivity.this);
        database = FirebaseDatabase.getInstance();

        tvTimer = findViewById(R.id.tv_timer);
        FloatingActionButton fabTimer = findViewById(R.id.fabtimer);
        FloatingActionButton fabTimer2 = findViewById(R.id.fabtimer2);

        fabTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning) {
                    isRunning = false;
                    cancelTimer();
                    globalMillisUntilFinished = 30000L;
                } else {
                    isRunning = true;
                    startTimer();
                }
            }
        });

        fabTimer2.setOnClickListener(view -> {
            if (isRunning) {
                isRunning = false;
                cTimer.cancel();
            } else {
                isRunning = true;
                startTimer();
            }
        });

    }

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(globalMillisUntilFinished, 1000) {
            public void onTick(long millisUntilFinished) {
                globalMillisUntilFinished = millisUntilFinished;
                String textoTimer = String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tvTimer.setText("" + textoTimer);
                database.getReference().child("timer").child("teste").setValue(textoTimer);
            }

            public void onFinish() {
                isRunning = false;
                database.getReference().child("timer").child("teste").setValue("00:00:30");
                tvTimer.setText("00:30");
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null) {
            isRunning = false;
            database.getReference().child("timer").child("teste").setValue("00:30");
            tvTimer.setText("00:30");
            cTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
