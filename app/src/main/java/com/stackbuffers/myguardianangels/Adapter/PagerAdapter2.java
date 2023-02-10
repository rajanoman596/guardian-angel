package com.stackbuffers.myguardianangels.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stackbuffers.myguardianangels.Fragments.Angels_Evidence_Fragment;
import com.stackbuffers.myguardianangels.Fragments.Check_Request_Fragment;
import com.stackbuffers.myguardianangels.Fragments.Guardian_Angel_Fragment;
import com.stackbuffers.myguardianangels.Fragments.My_Evidence_fragment;

public class PagerAdapter2 extends FragmentPagerAdapter {


    public PagerAdapter2(@NonNull FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter2(@NonNull FragmentManager fm, int behavior) {


        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0: return  new Guardian_Angel_Fragment();
            case 1: return new Check_Request_Fragment();
            default:return new Guardian_Angel_Fragment();
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
            title = "My Angels";
        }
        else if(position==1){
            title="Requested Angels";
        }


        return title;
    }
}
