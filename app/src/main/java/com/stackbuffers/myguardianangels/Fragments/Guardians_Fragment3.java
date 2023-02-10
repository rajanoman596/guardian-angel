package com.stackbuffers.myguardianangels.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.stackbuffers.myguardianangels.Adapter.PagerAdapter2;
import com.stackbuffers.myguardianangels.R;



public class Guardians_Fragment3 extends Fragment {

    TabLayout tablayout2;
    ViewPager viewPager2;



    public Guardians_Fragment3() {
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
        View view = inflater.inflate(R.layout.fragment_guardians_3, container, false);

        tablayout2 = view.findViewById(R.id.tablayout2);
        viewPager2 = view.findViewById(R.id.viewPager2);


        viewPager2.setAdapter(new PagerAdapter2(getChildFragmentManager()));
        tablayout2.setupWithViewPager(viewPager2);
        return view;
    }
}