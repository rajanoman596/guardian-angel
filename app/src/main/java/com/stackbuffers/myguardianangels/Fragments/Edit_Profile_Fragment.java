package com.stackbuffers.myguardianangels.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.stackbuffers.myguardianangels.Models.Country;
import com.stackbuffers.myguardianangels.Models.CountryResponse;
import com.stackbuffers.myguardianangels.Models.GetProfile_Response;
import com.stackbuffers.myguardianangels.Models.UploadImage;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;
import com.stackbuffers.myguardianangels.interfaces.CallbackListener;
import com.stackbuffers.myguardianangels.manager.Manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Edit_Profile_Fragment extends Fragment {
    Spinner time_spinner, gender_spinner, country_spin, city_spin;
    EditText address_et, name_et, phone_et,emergency_contact, city_et;
    String time_, gender_, country_="", city_;
    Button btn_save;
    String id, date;
    TextView dob_tv;
    ImageView profile_image;
    private String imageFilePath;
    List<String> countriesList = new ArrayList<String>();
    List<Country> countriesDataList = new ArrayList<Country>();
    List<String> cityList = new ArrayList<String>();
    Country selectedCountry;
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> cityAdapter;

    DatePickerDialog.OnDateSetListener setListener;

    SharedPreferences preferences;

    ProgressBar progress_bar;


    public Edit_Profile_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_edit__profile_, container, false);
        time_spinner = view.findViewById(R.id.time_spinner);
        gender_spinner = view.findViewById(R.id.gender_spinner);
        country_spin = view.findViewById(R.id.country_spin);
        city_spin = view.findViewById(R.id.city_spin);
        address_et = view.findViewById(R.id.address_et);
        name_et = view.findViewById(R.id.name_et);
        phone_et = view.findViewById(R.id.phone_et);
        dob_tv = view.findViewById(R.id.dob_tv);
        profile_image = view.findViewById(R.id.profile_image);
        emergency_contact = view.findViewById(R.id.emergency_contact);
        city_et = view.findViewById(R.id.city_et);
        progress_bar = view.findViewById(R.id.progress_bar);



        btn_save = view.findViewById(R.id.btn_save);

        preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        id = preferences.getString("userId", " ");
        setData(id);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dob_tv.setOnClickListener(v -> {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                            , setListener, year, month, day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();

                }
        );

        setListener = (view1, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            date = day + "-" + month1 + "-" + year1;
            dob_tv.setText(date);
        };

        profile_image.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return;
            }
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 99);
            //someActivityResultLauncher.launch(photoPickerIntent);

//                ImagePicker.with(requireActivity())
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                        .start();
        });


        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.time, R.layout.custom_spinner_layout);
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
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, R.layout.custom_spinner_layout);
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

        countryAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.custom_spinner_layout, countriesList);
        country_spin.setAdapter(countryAdapter);
        country_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_ = country_spin.getSelectedItem().toString();
//                for (int i = 0; i < countriesDataList.size(); i++) {
//                    if (countriesDataList.get(i).getName().equals(country_)) {
//                        selectedCountry = countriesDataList.get(i);
//                        getCities(selectedCountry.getId());
//                        break;
//                    }
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getCountries();
            }
        },1000);


        cityAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.custom_spinner_layout, cityList);
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

                String name = name_et.getText().toString();
                String address = address_et.getText().toString();
                String Phone = phone_et.getText().toString();
                String emergencycontact = emergency_contact.getText().toString();
                String city = city_et.getText().toString();


                progress_bar.setVisibility(View.VISIBLE);
                if (imageFilePath != null) {
                    saveImage(Uri.parse(imageFilePath), new CallbackListener() {
                        @Override
                        public void onReceiveData(Location p0) {
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateProfile(id, name, address, Phone,emergencycontact, date, time_, gender_, country_, city);
                                }
                            }, 100);
                        }
                    });
                    return;
                }

                updateProfile(id, name, address, Phone,emergencycontact, date, time_, gender_, country_, city);
            }
        });

        return view;
    }

    void saveImage(Uri imageUri, CallbackListener callbackListener) {
        String base64Image = Manager.encodeToBase64(imageUri, requireActivity());
        if (base64Image.isEmpty()) return;
        Call<JsonObject> call = RetrofitClientInstance.getInstance().getApi().updateAvatar(base64Image, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse Image loaded: ");
                callbackListener.onReceiveData(null);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
                Toast.makeText(requireActivity(), "Image could't save", Toast.LENGTH_SHORT).show();
                callbackListener.onReceiveData(null);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 99) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                final Uri imageUri = data.getData();
                //Glide.with(requireActivity()).load(imageUri).into(profile_image);
                profile_image.setImageURI(Uri.parse(getPath(imageUri)));
                //imageFile = new File(getPath(imageUri));
                imageFilePath = imageUri.toString();
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private void getCountries() {
        Log.d("TAG", "getCountries: ");
        Call<CountryResponse> call = RetrofitClientInstance.getInstance().getApi().getCountries(id);
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                Log.d("TAG", "Countries loaded: ");
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

                        country_spin.setSelection(countryAdapter.getPosition(country_));
                    }

                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Log.d("TAG", "getCountries error: " + t.getMessage());
            }
        });
    }

    private void getCities(int countryID) {
        Log.d("TAG", "getCities: " + countryID);
        Call<CountryResponse> call = RetrofitClientInstance.getInstance().getApi().getCities(String.valueOf(countryID));
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    CountryResponse res = response.body();
                    Log.d("TAG", "onResponse: getCities" + response.body().getData().toString());
                    if (res != null && res.data != null) {
                        cityList.clear();
                        for (int i = 0; i < res.getData().size(); i++) {
                            cityList.add(res.getData().get(i).getName());
                        }
                        cityAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {

            }
        });
    }

    private void setData(String id) {
        progress_bar.setVisibility(View.VISIBLE);
        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().getProfile(id);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                progress_bar.setVisibility(View.GONE);
                GetProfile_Response res = response.body();
                if (response.isSuccessful() && res.getStatus() == 200) {
                    name_et.setHint(res.getData().getName());
                    phone_et.setHint(res.getData().getPhone());
                    address_et.setHint(res.getData().getAddress());
                    dob_tv.setHint(res.getData().getDob());
                    city_et.setHint(res.getData().getCity());
                    country_ = res.getData().country;
                }
                else {
                    Toast.makeText(requireActivity(),"Profile data loading failure",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfile_Response> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void updateProfile(String id, String name, String address, String phone,String emergencycontact, String date, String time_, String gender_, String country_, String city_) {

        //if image in selected by user then update it on server

        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().updateProfile(date, id, name, phone,emergencycontact, gender_, country_, city_, address, time_);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                GetProfile_Response response1 = response.body();
                progress_bar.setVisibility(View.GONE);
                if (response.isSuccessful() && response1.getStatus() == 200) {
                    Toast.makeText(getActivity(), response1.getMessage() + " " + name, Toast.LENGTH_SHORT).show();
                    Profile_Fragment4 editProfile_fragment = new Profile_Fragment4();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame1, editProfile_fragment);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(requireActivity(), response1 != null ? response1.getMessage() : "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfile_Response> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}