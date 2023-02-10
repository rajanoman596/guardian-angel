package com.stackbuffers.myguardianangels.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stackbuffers.myguardianangels.Models.GuardianAngel_Edit_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Angel_Activity extends AppCompatActivity {

    EditText fullnam_et,email_et,relation_et;
    Button edit_btn;
    String recordId;
    String userId;
    String angelId;
    TextView edit_tv;
    //testPurpose
    TextView userId1,angel_id;
    String name,email,relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_angel);

        fullnam_et = findViewById(R.id.fullnam_et);
        email_et = findViewById(R.id.email_et);
        relation_et = findViewById(R.id.relation_et);
        edit_btn = findViewById(R.id.edit_btn);
        userId1 = findViewById(R.id.userId);
        angel_id = findViewById(R.id.angel_id);


        edit_tv = findViewById(R.id.edit_tv);
        name = getIntent().getStringExtra("username");
        fullnam_et.setText(name);
        email_et.setText(getIntent().getStringExtra("userEmail"));
        relation_et.setText(getIntent().getStringExtra("relation"));
        edit_tv.setText(getIntent().getStringExtra("record_id"));

        userId1.setText(getIntent().getStringExtra("user_id"));
        angel_id.setText(getIntent().getStringExtra("angel_id"));





        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordId = getIntent().getStringExtra("record_id");

                String name = fullnam_et.getText().toString();
                String email = email_et.getText().toString();
                String relation = relation_et.getText().toString();
                userId = getIntent().getStringExtra("user_id");
                angelId = getIntent().getStringExtra("angel_id");



               editAngel(recordId,userId,angelId,name,email,relation);


            }
        });




    }

    private void editAngel(String recordId, String userId, String angelId, String name, String email, String relation) {

        Call<GuardianAngel_Edit_Response> call = RetrofitClientInstance.getInstance().getApi().editAngel(recordId,userId,angelId,name,email,relation);
        call.enqueue(new Callback<GuardianAngel_Edit_Response>() {
            @Override
            public void onResponse(Call<GuardianAngel_Edit_Response> call, Response<GuardianAngel_Edit_Response> response) {
                GuardianAngel_Edit_Response res = response.body();
                if(response.isSuccessful()&&res.getStatus()==200){
                    Toast.makeText(getApplicationContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GuardianAngel_Edit_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

    }


}