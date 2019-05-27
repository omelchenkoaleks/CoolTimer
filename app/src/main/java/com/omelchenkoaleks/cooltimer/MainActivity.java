package com.omelchenkoaleks.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = findViewById(R.id.seek_bar);
        mTextView = findViewById(R.id.text_view);

        mSeekBar.setMax(600);
        mSeekBar.setProgress(60);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                long progressInMillis = progress * 1000;
                updateTimer(progressInMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        new CountDownTimer(mSeekBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.d("onFinish: ", "Finish");
            }
        }.start();
    }

    private void updateTimer(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished/1000/60);
        int seconds = (int) (millisUntilFinished/1000 - (minutes * 60));

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }

        mTextView.setText(minutesString + ":" + secondsString);
    }
}
