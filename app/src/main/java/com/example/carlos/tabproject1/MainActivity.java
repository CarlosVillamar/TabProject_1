package com.example.carlos.tabproject1;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the two primary sections of the activity.

        mViewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Set our fragments to the page adapter
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mSectionsPagerAdapter.getFrag(new Tab2Fragment(), "Personal");
        mSectionsPagerAdapter.getFrag(new Tab2Fragment(), "Business");

        // Set up the ViewPager with the sections adapter.
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("meh",mSectionsPagerAdapter.getItem());
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
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
}


