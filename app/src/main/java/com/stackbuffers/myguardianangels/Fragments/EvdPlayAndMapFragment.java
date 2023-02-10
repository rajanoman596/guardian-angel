package com.stackbuffers.myguardianangels.Fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stackbuffers.myguardianangels.Models.myEvidence.MyEvidence;
import com.stackbuffers.myguardianangels.R;
import com.stackbuffers.myguardianangels.databinding.EvdPlayMapFragmentBinding;

public class EvdPlayAndMapFragment extends Fragment {

    MyEvidence evidence;

    public EvdPlayAndMapFragment(MyEvidence evidence) {
        this.evidence = evidence;
    }

    private EvdPlayMapFragmentBinding binding;
    SupportMapFragment supportMapFragment;
    LatLng latLng;
    MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EvdPlayMapFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (evidence != null) {
            String lat = evidence.latitude;
            String lng = evidence.longitude;

            try {
                latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            } catch (Exception e) {
            }

            if (evidence.file != null) {
                if (evidence.format != null && evidence.format.equals("mp3")) {
                    try {
                        mediaPlayer = new MediaPlayer();
                        String path = "https://myguardianangels.app/newtest/public/audio/" + evidence.file;
                        Log.d("TAG", "going to play audio: " + path);
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Toast.makeText(requireActivity(), "Playing audio", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }

                } else {

                    try {
                        // Start the MediaController
                        MediaController mediaController = new MediaController(requireActivity());
                        mediaController.setAnchorView(binding.videoView);
                        // Get the URL from String videoUrl
                        //Uri video = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
                        Uri video = Uri.parse("https://myguardianangels.app/newtest/public/video/" + evidence.file);
                        Log.d("TAG", "going to play: " + video);
                        binding.videoView.setMediaController(mediaController);
                        binding.videoView.setVideoURI(video);
                        binding.videoView.start();

                    } catch (Exception e) {
                        Log.d("tag", "error in video playing " + e.getMessage());
                        e.printStackTrace();
                    }


                    //binding.videoView.setVideoURI(Uri.parse("https://myguardianangels.app/newtest/public/video/"+evidence.file));
                    // binding.videoView.setVideoPath("https://myguardianangels.app/newtest/public/video/1650100913VID_20220416_142116.mp4");
                    // binding.videoView.setMediaController(mediaController);
                    //   binding.videoView.setVideoURI(Uri.parse("https://myguardianangels.app/newtest/public/video/1650100913VID_20220416_142116.mp4"));
                    // binding.videoView.start();
                }
            }
        }


        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(googleMap -> {
                if (latLng != null) {
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Evidence is here....!");
                    googleMap.addMarker(markerOptions).showInfoWindow();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            });
        }

    }
}
