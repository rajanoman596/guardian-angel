package com.stackbuffers.myguardianangels.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stackbuffers.myguardianangels.Models.Country;
import com.stackbuffers.myguardianangels.Models.CountryResponse;
import com.stackbuffers.myguardianangels.Models.GetProfile_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile_Activity extends AppCompatActivity {
    Spinner time_spinner, gender_spinner, country_spin, city_spin;
    EditText address_et, name_et, phone_et, city_et;
    String time_, gender_, country_, city_;
    Button btn_save;
    String id, date, gid;
    TextView dob_tv, title;
    ArrayAdapter<String> countryAdapter;
    List<Country> countriesDataList = new ArrayList<Country>();
    List<String> countriesList = new ArrayList<String>();

    DatePickerDialog.OnDateSetListener setListener;
    ProgressBar progress_bar;

    SharedPreferences preferences, pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        time_spinner = findViewById(R.id.time_spinner);
        gender_spinner = findViewById(R.id.gender_spinner);
        country_spin = findViewById(R.id.country_spin);
        city_spin = findViewById(R.id.city_spin);
        address_et = findViewById(R.id.address_et);
        name_et = findViewById(R.id.name_et);
        phone_et = findViewById(R.id.phone_et);
        dob_tv = findViewById(R.id.dob_tv);
        title = findViewById(R.id.title);
        city_et = findViewById(R.id.city_et);

        progress_bar = findViewById(R.id.progress_bar);

        btn_save = findViewById(R.id.btn_save);
        pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        gid = pref.getString("userId", " ");


        setData(gid);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dob_tv.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          DatePickerDialog datePickerDialog = new DatePickerDialog(
                                                  Edit_Profile_Activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                                                  , setListener, year, month, day);
                                          datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                          datePickerDialog.show();

                                      }
                                  }
        );

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = day + "-" + month + "-" + year;
                dob_tv.setText(date);
            }
        };


        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.time, R.layout.custom_spinner_layout);
        time_spinner.setAdapter(timeAdapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                time_ = time_spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gender, R.layout.custom_spinner_layout);
        gender_spinner.setAdapter(genderAdapter);
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_ = gender_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countryAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout, countriesList);
        country_spin.setAdapter(countryAdapter);
        country_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_ = country_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getCountries();

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.city_array, R.layout.custom_spinner_layout);
        city_spin.setAdapter(cityAdapter);
        city_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_ = city_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                gid = pref.getString("userId", " ");

                name_et.setHint(gid);

                String name = name_et.getText().toString();
                String address = address_et.getText().toString();
                String Phone = phone_et.getText().toString();
                String city = city_et.getText().toString();


                updateProfile(gid, name, address, Phone, date, time_, gender_, country_, city);

            }
        });
    }



    private void getCountries() {
        Call<CountryResponse> call = RetrofitClientInstance.getInstance().getApi().getCountries(id);
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    CountryResponse res = response.body();
                    if (res != null && res.data != null) {
                        countriesList.clear();
                        countriesDataList.clear();
                        countriesDataList.addAll(res.data);
                        for (int i = 0; i < res.getData().size(); i++) {
                            countriesList.add(res.getData().get(i).getName());
                        }
                        countryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });
    }


    private void updateProfile(String gid, String name, String address, String phone, String date, String time_, String gender_, String country_, String city_) {
        progress_bar.setVisibility(View.VISIBLE);
        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().updateProfile_(id, name, address, phone, date, time_, gender_, country_, city_);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                progress_bar.setVisibility(View.GONE);
                GetProfile_Response response1 = response.body();
                if (response.isSuccessful() && response1.getStatus() == 200) {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                } else {
                    Toast.makeText(getApplicationContext(), response1 != null ? response1.getMessage() : "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfile_Response> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setData(String id) {
        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().getProfile(id);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                GetProfile_Response res = response.body();
                if (response.isSuccessful() && res.getStatus() == 200) {
                    name_et.setHint(res.getData().getName());
                    phone_et.setHint(res.getData().getPhone());
                    address_et.setHint(res.getData().getAddress());
                    dob_tv.setHint(res.getData().getDob());
                }
            }

            @Override
            public void onFailure(Call<GetProfile_Response> call, Throwable t) {

            }
        });
    }


}