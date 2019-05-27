package com.omelchenkoaleks.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            ТАЙМЕР
         */

        CountDownTimer myTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("myTimer", String.valueOf(millisUntilFinished/1000)
                        + " seconds left");
            }

            @Override
            public void onFinish() {
                Log.d("myTimer", "Finish");
            }
        };

        myTimer.start();


        /*
            Способ выводить нужную информацию через заданное время
            или как таймер ...
         */
//        final Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("Runnable: ", "Two seconds are passed");
//                handler.postDelayed(this, 2000);
//            }
//        };
//
//        handler.post(runnable);
    }
}
