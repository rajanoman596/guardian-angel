package com.stackbuffers.myguardianangels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stackbuffers.myguardianangels.Models.Google_Signup_Response;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioRecording_Demo extends AppCompatActivity {

   Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recording_demo);

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGoogleApi();
            }
        });


    }

    private void callGoogleApi() {

        String name = "atoz";
        String email ="atoz_123@gmail.com";
        Call<Google_Signup_Response> call = RetrofitClientInstance.getInstance().getApi().googleSignup(name,email);
        call.enqueue(new Callback<Google_Signup_Response>() {
            @Override
            public void onResponse(Call<Google_Signup_Response> call, Response<Google_Signup_Response> response) {
                Google_Signup_Response res = response.body();
                if(response.isSuccessful()&&res.getStatus()==200){
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Google_Signup_Response> call, Throwable t) {

            }
        });
    }
}