package com.example.z52song.musicbox;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Thread thread;

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

                leftTime.setText(String.valueOf(playTime.format(new Date(currentPos))));
                righTime.setText(String.valueOf(playTime.format(new Date(durationInt - currentPos))));
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
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.mytwenty);

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
        righTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaPlayer.getDuration()))));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.prevButton:
                backMusic();
                break;

            case R.id.playButtion:

                Log.d("duration", Integer.toString(mediaPlayer.getDuration()));

                if(mediaPlayer.isPlaying()){
                    pauseMusic();
                } else {
                    playMusic();
                }
                break;

            case R.id.nextButton:
                nextMusic();
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

            updateThread();
        }
    }

    public void backMusic(){
        if(mediaPlayer != null){
            mediaPlayer.seekTo(0);
            playMusic();
        }
    }

    public void nextMusic(){
        if(mediaPlayer != null){
            mediaPlayer.seekTo(mediaPlayer.getDuration());
            playButton.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    //TODO: this could be the solution of the negative time
    public void setTimeShow(int dur, TextView tv){
        int mins = dur / 1000 / 60;
        int secs = dur / 1000 % 60;
        tv.setText(mins + ":" + secs);
    }

    public void updateThread(){
        thread = new Thread(){

            @Override
            public void run() {
                try{
                    while(mediaPlayer != null && mediaPlayer.isPlaying()){

                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //update the seekbar
                                int newPosition = mediaPlayer.getCurrentPosition();
                                int newMax = mediaPlayer.getDuration();
                                seekBar.setProgress(newPosition);
                                seekBar.setMax(newMax);

                                //update the text
                                leftTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss")
                                .format(new Date(mediaPlayer.getCurrentPosition()))));

                                righTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss")
                                        .format(new Date(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()))));

                            }
                        });
                    }
                } catch(Exception e){
                    Log.e("seekbar thread", e.toString());
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {

        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        thread.interrupt();
        thread = null;

        super.onDestroy();
    }
}




















