package com.stackbuffers.myguardianangels.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.stackbuffers.myguardianangels.Adapter.PagerAdapter;
import com.stackbuffers.myguardianangels.R;


public class Evidence_Fragment2 extends Fragment {
    TabLayout tablayout1;
    ViewPager viewPager;



    public Evidence_Fragment2() {
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
        View view = inflater.inflate(R.layout.fragment_evidence_2, container, false);

        tablayout1 = view.findViewById(R.id.tablayout1);
        viewPager = view.findViewById(R.id.viewPager);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        tablayout1 = view.findViewById(R.id.tablayout1);
        tablayout1.setupWithViewPager(viewPager);

        return view;
    }
}