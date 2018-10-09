package com.example.admin.stopwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.media.MediaPlayer.create;
import static android.widget.SeekBar.*;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timerTextView;
    Boolean isTimerActive = false;
    Button timerButton;
    CountDownTimer countDownTimer;

    public void resteTimer(){

        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        timerButton.setText("Go");
        timerSeekbar.setEnabled(true);
        timerTextView.setText("0:30");
        isTimerActive = false;
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - minutes*60;

        String secondsString = Integer.toString(seconds);
        if(seconds <= 9){

            secondsString = "0"+secondsString;


        }

      timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);

    }


    public void buttonClicked(View view){

        if(isTimerActive == false) {

            isTimerActive = true;
            timerSeekbar.setEnabled(false);
            timerButton.setText("Stop");


           countDownTimer = new CountDownTimer(timerSeekbar.getProgress()* 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFinish() {
                      resteTimer();
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.news_intro);
                    mp.start();
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                       vibrator.vibrate(500);
                    Toast.makeText(MainActivity.this, "ohho...Its Done.", Toast.LENGTH_SHORT).show();
                }
            }.start();

        } else{

            Toast.makeText(this, "Timer reset to default!", Toast.LENGTH_SHORT).show();
            resteTimer();



        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.seekBarId);
        timerTextView = (TextView) findViewById(R.id.textViewId);
        timerButton = (Button) findViewById(R.id.buttonId);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        }

    }

