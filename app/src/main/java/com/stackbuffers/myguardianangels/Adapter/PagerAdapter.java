package com.stackbuffers.myguardianangels.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stackbuffers.myguardianangels.Fragments.Angels_Evidence_Fragment;
import com.stackbuffers.myguardianangels.Fragments.My_Evidence_fragment;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {


        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0: return  new Angels_Evidence_Fragment();
            case 1: return new My_Evidence_fragment();
//            case 2: return new Reviews_Fragment_Tab3();
            default:return new Angels_Evidence_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        if(position==0){
            title = "Angels Evidence";
        }
        else if(position==1){
            title="My Evidence";
        }
//        else if (position==2){
//            title="Reviews";
//
//        }

        return title;
    }
}
