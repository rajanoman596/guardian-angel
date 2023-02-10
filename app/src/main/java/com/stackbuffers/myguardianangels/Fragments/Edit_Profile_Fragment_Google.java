package com.stackbuffers.myguardianangels.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stackbuffers.myguardianangels.Models.GetProfile_Response;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Edit_Profile_Fragment_Google extends Fragment {

    EditText address_et,name_et,phone_et,emergency_phne;
    String time_,gender_,country_,city_;
    Button btn_save;
    String id,date,gid;
    TextView dob_tv,title;

    DatePickerDialog.OnDateSetListener setListener;

    SharedPreferences preferences,pref;
    Spinner time_spinner,gender_spinner,country_spin,city_spin;



    public Edit_Profile_Fragment_Google() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_profile_fragment_google, container, false);

        time_spinner = view.findViewById(R.id.time_spinner);
        gender_spinner = view.findViewById(R.id.gender_spinner);
        country_spin = view.findViewById(R.id.country_spin);
        city_spin = view.findViewById(R.id.city_spin);
        address_et = view.findViewById(R.id.address_et);
        name_et = view.findViewById(R.id.name_et);
        phone_et = view .findViewById(R.id.phone_et);
        dob_tv = view.findViewById(R.id.dob_tv);

        emergency_phne = view.findViewById(R.id.emergency_phne);

        btn_save = view.findViewById(R.id.btn_save);

        preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        id = preferences.getString("userId", " ");
        setData(id);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dob_tv.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          DatePickerDialog datePickerDialog = new DatePickerDialog(
                                                  getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                                                  , setListener, year, month, day);
                                          datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                          datePickerDialog.show();

                                      }
                                  }
        );

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                date = day+"-"+month+"-"+year;
                dob_tv.setText(date);
            }
        };




        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.time,R.layout.custom_spinner_layout);
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
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.gender,R.layout.custom_spinner_layout);
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

        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.country_array,R.layout.custom_spinner_layout);
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

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.city_array,R.layout.custom_spinner_layout);
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
                pref = getActivity().getSharedPreferences("Gcredentials", Context.MODE_PRIVATE);
                gid = pref.getString("GuserId"," ");

                name_et.setHint(gid);

                String name = name_et.getText().toString();
                String address = address_et.getText().toString();
                String Phone = phone_et.getText().toString();
                String e_contact = emergency_phne.getText().toString();


                updateProfile(gid,name,address,Phone,e_contact,date,time_,gender_,country_,city_);
            }
        });






        return view;
    }

    private void setData(String id) {
        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().getProfile(id);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                GetProfile_Response res = response.body();
                if(response.isSuccessful()&&res.getStatus()==200){
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

    private void updateProfile(String id, String name, String address, String phone,String emergency, String date, String time_, String gender_, String country_, String city_) {

        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().updateProfile(date,id,name,phone,emergency,gender_,country_,city_,address,time_);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                GetProfile_Response response1 = response.body();
                if(response.isSuccessful()&& response1.getStatus()==200){
                    Toast.makeText(getActivity(), response1.getMessage() + " "+name, Toast.LENGTH_SHORT).show();
                    Profile_Fragment4 editProfile_fragment = new Profile_Fragment4();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame1, editProfile_fragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(Call<GetProfile_Response> call, Throwable t) {

            }
        });
    }
}