package com.stackbuffers.myguardianangels.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.stackbuffers.myguardianangels.AudioRecording_Demo;
import com.stackbuffers.myguardianangels.MainActivity;
import com.stackbuffers.myguardianangels.R;

public class Splash_Activity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                if (preferences.contains("userEmail")) {
                    startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                    finish();
                }else {
                    Intent intent = new Intent(Splash_Activity.this, LogIn_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}