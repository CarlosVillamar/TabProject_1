package com.example.carlos.tabproject1.views;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    /*
      this is a class to set up an adapter to recognize our tabs
    */

    //Set up a fixed size for our tabs and the fragments we will attach to the tabs
    ArrayList<String> tabName = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    int tabNum;

    public SectionsPagerAdapter(FragmentManager fm, int tabCount) {
        /**pass through the fragment manager and a tab count**/
        super(fm);
        tabNum = tabCount;
    }

    public void getFrag(Fragment fragments, String tabName) {
        /**add our fragments and tad names**/
        this.fragments.add(fragments);
        this.tabName.add(tabName);


    }

    @Override
    public Fragment getItem(int position) {
        /**
         *  create a switch here for the tab fragments and
         *  getItem is called to instantiate the fragment for the given page.
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
        //return the amount of tabs we have set up
        return tabNum;
    }

}
