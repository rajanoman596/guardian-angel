package com.stackbuffers.myguardianangels.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.stackbuffers.myguardianangels.Activities.LogIn_Activity;
import com.stackbuffers.myguardianangels.Models.GetProfile_Response;
import com.stackbuffers.myguardianangels.Models.UploadImage;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_Fragment4 extends Fragment {

    ImageView list_item;
    Dialog dialog;
    CircleImageView profile_image;
    String id;
    TextView name_tv, email_tv, relation_tv, phone_tv, gender_tv, date_tv, country_tv, city_tv, complete_address_tv, time_tv;


    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    GoogleSignInClient mGoogleSignInClient;

    SharedPreferences preferences;
    // SharedPreferences pref;

    private final int REQUEST_CODE = 111;

    ProgressBar progress_bar;


    public Profile_Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_4, container, false);


        list_item = view.findViewById(R.id.list_item);
        profile_image = view.findViewById(R.id.profile_image);
        dialog = new Dialog(getActivity());
        name_tv = view.findViewById(R.id.name_tv);
        email_tv = view.findViewById(R.id.email_tv);
        relation_tv = view.findViewById(R.id.relation_tv);
        phone_tv = view.findViewById(R.id.phone_tv);
        gender_tv = view.findViewById(R.id.gender_tv);
        date_tv = view.findViewById(R.id.date_tv);
        country_tv = view.findViewById(R.id.country_tv);
        city_tv = view.findViewById(R.id.city_tv);
        time_tv = view.findViewById(R.id.time_tv);
        progress_bar = view.findViewById(R.id.progress_bar);
        complete_address_tv = view.findViewById(R.id.complete_address_tv);

        preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        if (id == null) {
            id = preferences.getString("userId", " ");
        }
        fetchProfileData(id);
        uploas(id);


        list_item.setOnClickListener(v -> alert());


        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        getImage();

//        if (ActivityCompat.checkSelfPermission(getContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED) {
//            getMyLocation();
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{
//                            Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE
//            );
//        }

        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getMyLocation();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();


        return view;
    }

    private void getImage() {
        Call<JsonObject> call = RetrofitClientInstance.getInstance().getApi().getAvatar(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse: ");
                    if (response.body() != null) {
                        String url = response.body().get("url").toString();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private void fetchProfileData(String id) {
        progress_bar.setVisibility(View.VISIBLE);
        Call<GetProfile_Response> call = RetrofitClientInstance.getInstance().getApi().getProfile(id);
        call.enqueue(new Callback<GetProfile_Response>() {
            @Override
            public void onResponse(Call<GetProfile_Response> call, Response<GetProfile_Response> response) {
                progress_bar.setVisibility(View.GONE);
                GetProfile_Response res = response.body();
                if (response.isSuccessful() && res.getStatus() == 200) {
                    name_tv.setText(res.getData().getName());
                    email_tv.setText(res.getData().getEmail());
                    phone_tv.setText(res.getData().getPhone());
                    gender_tv.setText(res.getData().getGender());
                    date_tv.setText(res.getData().getDob());
                    country_tv.setText(res.getData().getCountry());
                    city_tv.setText(res.getData().getCity());
                    complete_address_tv.setText(res.getData().getAddress());
                    time_tv.setText(res.getData().getTimePeriod());

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("time1", res.getData().getTimePeriod());
                    editor.apply();

                    if (getActivity() != null)
                        Glide.with(getActivity()).load("https://myguardianangels.app/newtest/api/user/avatar/url/" + res.getData().getAvatar()).into(profile_image);


                } else {
                    Toast.makeText(requireActivity(), "Profile data loading failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfile_Response> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), "Profile data loading failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(location -> supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (location == null) return;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here....!");
                googleMap.addMarker(markerOptions).showInfoWindow();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        }));
    }


    private void alert() {

        dialog.setContentView(R.layout.profile_nav);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;

        window.setAttributes(layoutParams);


        TextView edit = dialog.findViewById(R.id.edit_profile);
        TextView sign_out = dialog.findViewById(R.id.sign_out);
        TextView faq = dialog.findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FAQ_Fragment faq_fragment = new FAQ_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, faq_fragment);
                fragmentTransaction.commit();
                dialog.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment editProfile_fragment = new BlankFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame1, editProfile_fragment);
                fragmentTransaction.commit();
                dialog.dismiss();
            }


        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                    }
//                });
                preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LogIn_Activity.class));
                dialog.dismiss();
            }
        });

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CODE){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//               getMyLocation();
//            }
//            else {
//                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    public void uploas(String id){
        Call<UploadImage> call = RetrofitClientInstance.getInstance().getApi().updateImg(id);
        call.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {

                UploadImage res = response.body();


                if(response.isSuccessful()&&res.getStatus()==200){

                    Glide.with(getActivity()).load("https://myguardianangels.app/newtest/public/Asset/img/user/avatar.png"+res.getUrl()).into(profile_image);


                    Toast.makeText(getActivity(), res.getUrl(), Toast.LENGTH_SHORT).show();

                }

                else{
                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {

            }
        });

    }


}