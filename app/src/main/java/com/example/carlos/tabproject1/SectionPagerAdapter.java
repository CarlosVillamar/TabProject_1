package com.example.carlos.tabproject1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> tabName = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    int tabNum;

    public SectionsPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        tabNum = tabCount;
    }

    public void getFrag(Fragment fragments, String tabName) {
        this.fragments.add(fragments);
        this.tabName.add(tabName);


    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a Tab2Fragment (defined as a static inner class below).
        // return Tab2Fragment.newInstance(position + 1);
        // return fragments.get(position);
        /**
         *
         * create a switch here for the tab fragments
         */
        switch (position) {
            case 0:
                return new Tab1Fragment();
            case 1:
                return new Tab2Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages
        // return 2;
        //return fragments.size();
        return tabNum;
    }


}
