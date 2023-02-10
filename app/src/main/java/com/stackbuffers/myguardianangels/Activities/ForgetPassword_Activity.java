package com.stackbuffers.myguardianangels.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stackbuffers.myguardianangels.Models.Verify_Resend_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword_Activity extends AppCompatActivity {

    EditText email_et;
    Button sendLink_btn;
    TextView sign_up_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email_et = findViewById(R.id.email_et);
        sendLink_btn = findViewById(R.id.sendLink_btn);
        sign_up_tv = findViewById(R.id.sign_up_tv);
        sign_up_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp_Activity.class));
            }
        });
        sendLink_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                if(email.isEmpty()){
                    email_et.setError("Email is required");
                    email_et.requestFocus();
                    return;
                }
                verify(email);
            }
        });

    }

    private void verify(String email) {
        Call<Verify_Resend_Response> call = RetrofitClientInstance.getInstance().getApi().forget(email);
        call.enqueue(new Callback<Verify_Resend_Response>() {
            @Override
            public void onResponse(Call<Verify_Resend_Response> call, Response<Verify_Resend_Response> response) {
                Verify_Resend_Response res = response.body();
                if(response.isSuccessful()&&res.getStatus()==200){
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LogIn_Activity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Verify_Resend_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Poor Internet Connection!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}