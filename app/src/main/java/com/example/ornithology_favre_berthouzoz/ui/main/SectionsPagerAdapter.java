package com.example.ornithology_favre_berthouzoz.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ornithology_favre_berthouzoz.AddEditBirdActivity;
import com.example.ornithology_favre_berthouzoz.R;
import com.example.ornithology_favre_berthouzoz.TabbedInfoBird;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {



    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_description, R.string.tab_text_image, R.string.tab_text_scream };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {

    String description, biology;


        Fragment fragment = null;

        switch (position){
            case 0:                                     //first position


                fragment = new DescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Description", AddEditBirdActivity.EXTRA_DESCRIPTION);
                fragment.setArguments(bundle);
                //return new DescriptionFragment();
               break;
            case 1:
                fragment = new ImageFragment();
                break;
            case 2:
                fragment = new ScreamFragment();
                break;
        }
        return fragment;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}