package com.stackbuffers.myguardianangels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {



    Button record,stop,play,stopplaying;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String AudioSavePath = "";
    final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkPermission()){
            requestPermission();
        }
        record = findViewById(R.id.record);
        stop = findViewById(R.id.stop);
        play = findViewById(R.id.play);
        stopplaying = findViewById(R.id.stopplaying);

        mediaRecorder = new MediaRecorder();


         record.setOnClickListener(v -> {
                     if(checkPermission()) {
                         AudioSavePath = Environment.getExternalStorageDirectory()
                                 .getAbsolutePath() + "/"
                                 + UUID.randomUUID().toString() + "_audio_record.3gp";
                         setupMediaRecorder();
                         try {
                             mediaRecorder.prepare();
                             mediaRecorder.start();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         play.setEnabled(false);
                         stop.setEnabled(true);
                 stopplaying.setEnabled(false);
                         Toast.makeText(getApplicationContext(), "Recording....", Toast.LENGTH_SHORT).show();

                     }
         else{
             requestPermission();
         }
         });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaRecorder != null){
                    try{
                        stop.setEnabled(false);
                        play.setEnabled(true);
                        record.setEnabled(true);
                        stopplaying.setEnabled(false);
                        mediaRecorder.stop();
                        mediaRecorder.release();

                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }


                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    play.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer = new MediaPlayer();

            record.setEnabled(false);

            stopplaying.setEnabled(true);
            stop.setEnabled(false);
            mediaPlayer = new MediaPlayer();

            try {
                mediaPlayer.setDataSource(AudioSavePath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Playing....", Toast.LENGTH_SHORT).show();
        }
    });

    stopplaying.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            stop.setEnabled(false);
            record.setEnabled(true);
            stopplaying.setEnabled(false);
            play.setEnabled(true);

            if(mediaPlayer!=null){




            mediaPlayer.stop();
                mediaPlayer.release();
                setupMediaRecorder();
                Toast.makeText(getApplicationContext(), "Media player is stopped", Toast.LENGTH_SHORT).show();
            }
        }
    });


    }


    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePath);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }


    }


    private boolean checkPermission(){
        int write_external_storage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        return write_external_storage == PackageManager.PERMISSION_GRANTED &&
              record_audio_result ==  PackageManager.PERMISSION_GRANTED;
    }


}