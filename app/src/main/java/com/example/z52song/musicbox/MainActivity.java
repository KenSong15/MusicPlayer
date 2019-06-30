package com.example.z52song.musicbox;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private ImageView artistImage;
    private TextView leftTime;
    private TextView righTime;
    private SeekBar seekBar;
    private Button prevButton;
    private Button playButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(b) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }

                SimpleDateFormat playTime = new SimpleDateFormat("mm:ss");
                int currentPos = mediaPlayer.getCurrentPosition();
                int durationInt = mediaPlayer.getDuration();

                leftTime.setText(playTime.format(new Date(currentPos)));
                righTime.setText(playTime.format(new Date(durationInt - currentPos)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void setUpUI(){

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.m1to20);

        artistImage = findViewById(R.id.topOval);
        leftTime = findViewById(R.id.leftTime);
        righTime = findViewById(R.id.rightTime);
        seekBar = findViewById(R.id.seekBar);
        prevButton = findViewById(R.id.prevButton);
        playButton = findViewById(R.id.playButtion);
        nextButton = findViewById(R.id.nextButton);

        prevButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.prevButton:
                //TODO prev
                break;

            case R.id.playButtion:

                if(mediaPlayer.isPlaying()){
                    pauseMusic();
                } else {
                    playMusic();
                }
                break;

            case R.id.nextButton:
                //TODO next
                break;

        }
    }

    public void pauseMusic(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            playButton.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void playMusic(){
        if(mediaPlayer != null) {
            mediaPlayer.start();
            playButton.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }
}




















