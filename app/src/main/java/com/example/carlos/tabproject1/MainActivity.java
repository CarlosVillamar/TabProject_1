package com.example.carlos.tabproject1;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private FirebaseDatabase fireDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireDB = FirebaseDatabase.getInstance();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        mViewPager = (ViewPager) findViewById(R.id.container);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mSectionsPagerAdapter.getFrag(new PlaceholderFragment(), "Personal");
        mSectionsPagerAdapter.getFrag(new PlaceholderFragment(), "Business");
        // Set up the ViewPager with the sections adapter.


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        ArrayList<TODO> todoArrayList;
        tabAdapter adapter;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            Log.d(".onCreateView", "it works");
            View v = inflater.inflate(R.layout.fragment_tab1, container, false);
            RecyclerView recyclerView = v.findViewById(R.id.recycleView);
            todoArrayList = new ArrayList<TODO>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


            adapter = new tabAdapter(getContext(),todoArrayList);
            recyclerView.setAdapter(adapter);

            initializeData();
            return v;
        }

        private void initializeData() {
            String[] nameArray = getResources().getStringArray(R.array.tab2Name);
            String[] noteArray = getResources().getStringArray(R.array.NotesforTab2);

            todoArrayList.clear();

            for(int i = 0; i< nameArray.length;i++){
                todoArrayList.add(new TODO(nameArray[i],noteArray[i],false));

            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            switch (id) {
                case R.id.menuAddTask:
                   // Toast.makeText(getContext(), "meh", Toast.LENGTH_SHORT).show();
                    todoArrayList.add(new TODO("NAME","NOTES",true) );
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.menuDelete:
                   // Toast.makeText(getContext(), "deleteeMEHHHHH", Toast.LENGTH_SHORT).show();
                    if (todoArrayList.size()>=0){
//                    todoArrayList.remove(todoArrayList.size()-1);

                    adapter.removeItem( todoArrayList.remove(2));
                    adapter.notifyDataSetChanged();
                    }else {
                        break;
                    }
                    break;
                default:

            }
            return super.onOptionsItemSelected(item);
        }

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            // return fragments.get(position);
            /**
             *
             * create a switch here for the tab fragments
             */
            switch (position) {
                case 0:
                    return new Tab1Fragment();
                case 1:
                    return new PlaceholderFragment();
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


}


