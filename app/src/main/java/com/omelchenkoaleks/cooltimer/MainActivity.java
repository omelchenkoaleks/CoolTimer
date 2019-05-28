package com.omelchenkoaleks.cooltimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView mTextView;
    boolean isTimerOn;
    Button mButton;
    CountDownTimer mCountDownTimer;
    int defaultInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = findViewById(R.id.seek_bar);
        mTextView = findViewById(R.id.text_view);
        mButton = findViewById(R.id.button);

        mSeekBar.setMax(600);
        setIntervalFromSharedPreference(PreferenceManager.getDefaultSharedPreferences(this));
        isTimerOn = false;

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
        if (!isTimerOn) {
            mButton.setText(R.string.stop);
            mSeekBar.setEnabled(false);
            isTimerOn = true;

            mCountDownTimer = new CountDownTimer(
                    mSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (sharedPreferences.getBoolean("enable_sound", true)) {
                        String melodyName = sharedPreferences.getString("timer_melody", "bell");
                        MediaPlayer mediaPlayer = null;
                        if (melodyName.equals("bell")) {
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),
                                    R.raw.bell_sound);
                        } else if (melodyName.equals("alarm_siren")) {
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),
                                    R.raw.alarm_siren_sound);
                        } else if (melodyName.equals("bip")) {
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),
                                    R.raw.bip_sound);
                        }
                        mediaPlayer.start();
                    }
                    resetTimer();
                }
            };
            mCountDownTimer.start();

        } else {
            resetTimer();
        }
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

    private void resetTimer() {
        mCountDownTimer.cancel();
        mButton.setText(R.string.start);
        mSeekBar.setEnabled(true);
        isTimerOn = false;
        setIntervalFromSharedPreference(PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;

        } else if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setIntervalFromSharedPreference(SharedPreferences sharedPreference) {
        defaultInterval = Integer.valueOf(sharedPreference.getString("default interval", "30"));
        mTextView.setText("00:" + defaultInterval);
        mSeekBar.setProgress(defaultInterval);
    }
}
