package com.stackbuffers.myguardianangels.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stackbuffers.myguardianangels.Models.Verify_Resend_Response;
import com.stackbuffers.myguardianangels.Models.Verify_Email_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Verify_Activity extends AppCompatActivity {
    Button verify_btn,resend_email_btn;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verify_btn = findViewById(R.id.verify_btn);
        resend_email_btn = findViewById(R.id.resend_email_btn);
        email = getIntent().getStringExtra("email");

        resend_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationEmail(email);
            }
        });


        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyEmail(email);

            }
        });


    }

    private void resendVerificationEmail(String email) {
        Call<Verify_Resend_Response> call = RetrofitClientInstance.getInstance().getApi().forget(email);
        call.enqueue(new Callback<Verify_Resend_Response>() {
            @Override
            public void onResponse(Call<Verify_Resend_Response> call, Response<Verify_Resend_Response> response) {
                Verify_Resend_Response res = response.body();
                if(response.isSuccessful()&&res.getStatus()==200){
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LogIn_Activity.class));
                }
            }

            @Override
            public void onFailure(Call<Verify_Resend_Response> call, Throwable t) {

            }
        });
    }

    private void verifyEmail(String email) {

        Call<Verify_Email_Response> call = RetrofitClientInstance.getInstance().getApi().verify(email);
        call.enqueue(new Callback<Verify_Email_Response>() {
            @Override
            public void onResponse(Call<Verify_Email_Response> call, Response<Verify_Email_Response> response) {
                Verify_Email_Response res = response.body();
                if(response.isSuccessful() && res.getStatus()==200){
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LogIn_Activity.class));


                }
            }

            @Override
            public void onFailure(Call<Verify_Email_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}