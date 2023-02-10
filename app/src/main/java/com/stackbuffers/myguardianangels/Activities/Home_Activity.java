package com.stackbuffers.myguardianangels.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stackbuffers.myguardianangels.Fragments.Evidence_Fragment2;
import com.stackbuffers.myguardianangels.Fragments.Guardians_Fragment3;
import com.stackbuffers.myguardianangels.Fragments.Home_Fragment1;
import com.stackbuffers.myguardianangels.Fragments.Profile_Fragment4;
import com.stackbuffers.myguardianangels.R;

public class Home_Activity extends AppCompatActivity {

    BottomNavigationView botttom_nav1;
    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_RECORD_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);


        botttom_nav1 = findViewById(R.id.botttom_nav1);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Home_Fragment1()).commit();

        botttom_nav1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;
                switch (item.getItemId()){
                    case R.id.home: temp = new Home_Fragment1();
                        break;
                    case R.id.wishlist: temp = new Evidence_Fragment2();
                        break;
                    case R.id.add_to_cart: temp = new Guardians_Fragment3();
                        break;
                    case R.id.profile: temp = new Profile_Fragment4();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame1,temp).commit();
                return true;
            }
        });

        requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 120){
            if (grantResults.length > 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this)
                        .setTitle("Location permission required")
                        .setMessage("Please allow location permissions to continue!")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                finishAffinity();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        // .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }

    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                .PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                .PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager
                .PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE}, 120);

        }
    }

    }
