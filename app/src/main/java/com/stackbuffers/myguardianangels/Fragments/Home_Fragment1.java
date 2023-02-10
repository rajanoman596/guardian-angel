package com.stackbuffers.myguardianangels.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.stackbuffers.myguardianangels.Models.VideoUpload.VideoUpload;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.RetrofitClasses.RetrofitClientInstance;
import com.stackbuffers.myguardianangels.interfaces.CallbackListener;
import com.stackbuffers.myguardianangels.manager.LocationManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_Fragment1 extends Fragment {


    Button audioevidenceBtn, video_evidence_btn;
    String id;
    long time;
    SharedPreferences preferences;
    private String mFilename = null;
    File file;
    SharedPreferences pref;
    String gid;

    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder mediaRecorder;
//    MediaPlayer mediaPlayer;

    private Uri videoPath;

    TextView record_tv;
    TextView textview1, textview2, textview3, text5;

    String value, intValue;
    String audioTime;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int VIDEO_RECORD_CODE = 101;

    ProgressBar load_video, load_audio;

    Location currentLocation;


    public Home_Fragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadLocation() {
        LocationManager.getCurrentLocation(requireActivity(), new CallbackListener() {
            @Override
            public void onReceiveData(Location p0) {
                currentLocation = p0;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        audioevidenceBtn = view.findViewById(R.id.audio_evidence_btn);
        video_evidence_btn = view.findViewById(R.id.video_evidence_btn);
        record_tv = view.findViewById(R.id.record_tv);

        load_video = view.findViewById(R.id.load_video);
        load_audio = view.findViewById(R.id.load_audio);


        mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilename += "/recorded_audio.3gp";

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        textview1 = view.findViewById(R.id.textview1);
        textview2 = view.findViewById(R.id.textview2);
        textview3 = view.findViewById(R.id.textview3);


        text5 = view.findViewById(R.id.text5);


        preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        id = preferences.getString("userId", " ");

        requestPermissions();

        //For Video

        if (isCameraPresentInPhone()) {
            Toast.makeText(getActivity(), "Camera is detected!", Toast.LENGTH_SHORT).show();
            getCameraPermission();
        } else {
            Toast.makeText(getActivity(), "Camera is not detected!", Toast.LENGTH_SHORT).show();
        }

        video_evidence_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentLocation == null) {
                    loadLocation();
                    return;
                }

                recordVideo();
            }
        });
//

        audioevidenceBtn.setOnClickListener(v -> {


            if (currentLocation == null) {
                loadLocation();
                return;
            }

            value = preferences.getString("time1", "2 min");
            Log.d("TAG", "onCreateView: time value"+value);

            if (!value.isEmpty()){
                String number  = value.replaceAll("[^0-9]", "");
                int num = Integer.parseInt(number);
                time = num * 60000L;
            }

            //time = Long.parseLong("2") * 6000;


            new CountDownTimer(time
                    , time) {

                public void onTick(long millisUntilFinished) {
                    buttonRecordPressed(value);


                }

                public void onFinish() {
                    // called after count down is finished
                    buttonStopPressed();

                }
            }.start();

            preferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
            id = preferences.getString("userId", " ");


        });


//
//        play.setOnClickListener(v -> buttonPlayPressed());
        return view;
    }

    private void uploadAudio(String id, String latitude, String longitude, long time) {

        int t = (int) ((time / 1000) / 6);

        File file = new File(getRecordingPath());


        String format = "mp3";


//        Call<StoreAudio_Response> call = RetrofitClientInstance.getInstance().getApi().storeAudio(id,t,format,longitude,latitude,file);
//
//        call.enqueue(new Callback<StoreAudio_Response>() {
//            @Override
//            public void onResponse(Call<StoreAudio_Response> call, Response<StoreAudio_Response> response) {
//                StoreAudio_Response res = response.body();
//                if(response.isSuccessful()&&res.getStatus()==200){
//                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getActivity(), res.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StoreAudio_Response> call, Throwable t) {
//
//            }
//        });


    }


    private void getLocation() {

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
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(),
                            Locale.getDefault());


                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);

                        textview1.setText(String.valueOf(addresses.get(0).getLongitude()));
                        textview2.setText(String.valueOf(addresses.get(0).getLatitude()));
                        textview3.setText(String.valueOf(addresses.get(0).getCountryName()));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 120) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadLocation();
            }
        }

    }

    private boolean isMicrophonePresent() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }


    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                .PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                .PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager
                .PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE}, 120);

        }
    }

    public void buttonRecordPressed(String min) {

        try {

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingPath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getActivity(), "Recoding Started for "+min, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void buttonStopPressed() {

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(getActivity(), "Recoding Stopped!", Toast.LENGTH_SHORT).show();
        String latitude = String.valueOf(textview1.getText());
        String longitude = String.valueOf(textview2.getText());
        //   uploadAudio(id, latitude, longitude, time);
        AudioUpload(id, latitude, longitude, time);

    }

//
//    public void buttonPlayPressed() {
//
//        try{
//
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(getRecordingPath());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            Toast.makeText(getActivity(), "Playing!", Toast.LENGTH_SHORT).show();
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }


    public String getRecordingPath() {
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        file = new File(musicDirectory, "testRecordingFile" + ".mp3");

        text5.setText(file.getPath());
        return file.getPath();
    }

//Vivideo Functions ->

    private boolean isCameraPresentInPhone() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    private void recordVideo() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_DENIED || ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager
                .PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {
            videoPath = data.getData();
            VideoUpload(videoPath);
            Log.i("Video_Recording_TAG", "Video is Recorded And Avialable at path" + videoPath);

        } else if (resultCode == 1) {
            Toast.makeText(getActivity(), "Video Canceled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Recording Video has some error", Toast.LENGTH_SHORT).show();
        }
    }

    static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String realPath = cursor.getString(index);
            cursor.close();
            return realPath;
        }
    }


    String getFileDuration(File file) {
        String duration = "";
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(requireActivity(), Uri.fromFile(file));
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInMilliSec = Long.parseLong(time);
            retriever.release();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliSec);
            duration = String.valueOf(seconds);
        } catch (Exception ignored) {

        }

        return duration;
    }

    private void VideoUpload(Uri videoPath) {
        String latitude = String.valueOf(textview1.getText());
        String longitude = String.valueOf(textview2.getText());

        load_video.setVisibility(View.VISIBLE);

        file = new File(getRealPathFromURI(requireContext(), videoPath));

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("*/*"),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        RequestBody User_ID = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody Deuration = RequestBody.create(MediaType.parse("text/plain"), getFileDuration(file));
        RequestBody Formate = RequestBody.create(MediaType.parse("text/plain"), "mp4");
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(currentLocation.getLongitude()));
        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(currentLocation.getLatitude()));

        Call<VideoUpload> call = RetrofitClientInstance.getInstance().getApi().VideoUpload(
                body,
                User_ID,
                Deuration,
                Formate,
                Longitude,
                Latitude
        );
        call.enqueue(new Callback<VideoUpload>() {
            @Override
            public void onResponse(Call<VideoUpload> call, Response<VideoUpload> response) {
                VideoUpload res = response.body();
                load_video.setVisibility(View.GONE);

                if (response.isSuccessful() && res.status == 200) {
                    VideoUpload users = res;
                    Log.e("sdfasdfasfsfdas", res.message);
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("sdfasdfasfsfdas", res.message);
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VideoUpload> call, Throwable t) {
                load_video.setVisibility(View.GONE);
                Log.e("sdfasdfasfsfdas", t.toString());
                Toast.makeText(requireContext(), "File Upload Error", Toast.LENGTH_SHORT).show();


            }
        });
    }


    private void AudioUpload(String id, String latitude, String longitude, long time) {

        load_audio.setVisibility(View.VISIBLE);

        file = new File(text5.getText().toString());

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("*/*"),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        RequestBody User_ID = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody Deuration = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(time));
        RequestBody Formate = RequestBody.create(MediaType.parse("text/plain"), "mp3");
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(currentLocation.getLongitude()));
        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(currentLocation.getLatitude()));


        Call<VideoUpload> call = RetrofitClientInstance.getInstance().getApi().AudioUpload(
                body,
                User_ID,
                Deuration,
                Formate,
                Longitude,
                Latitude
        );
        call.enqueue(new Callback<VideoUpload>() {
            @Override
            public void onResponse(Call<VideoUpload> call, Response<VideoUpload> response) {
                VideoUpload res = response.body();
                load_audio.setVisibility(View.GONE);

                if (response.isSuccessful() && res.status == 200) {
                    VideoUpload users = res;
                    Log.e("sdfasdfasfsfdas", res.message);
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("sdfasdfasfsfdas", res.message);
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VideoUpload> call, Throwable t) {
                load_video.setVisibility(View.GONE);
                Log.e("sdfasdfasfsfdas", t.toString());
                Toast.makeText(requireContext(), "File Upload Error", Toast.LENGTH_SHORT).show();


            }
        });
    }


}