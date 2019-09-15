package com.example.carlos.tabproject1;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Tab2Fragment extends Fragment {
    /**The fragment argument representing the section number for this
     * fragment. Refer to Tab1Fragment class comments to understand how this class works.
     * Both classes are identical, we just refer to different firebase instances */
    List<TaskList> taskListArrayList;
    TabAdapter adapter;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    TaskList mTaskList;
    AppCompatActivity activity;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public Tab2Fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Tab2Fragment newInstance(int sectionNumber) {
        Tab2Fragment fragment = new Tab2Fragment();
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
        View v = inflater.inflate(R.layout.tab_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycleView);

        taskListArrayList = new ArrayList<TaskList>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new TabAdapter(getContext(), taskListArrayList, getString(R.string.tab_text_2));
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.tab_text_2));
        Log.d(TAG, "onCreateView: Tab2 " + databaseReference.getKey());
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                taskListArrayList.clear();
                getAllTask(dataSnapshot);
                Log.d(TAG, "onChildChanged: "+ s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getAllTask(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        if(activity != null&& isAdded()){
            recyclerView.setVisibility(v.getVisibility());
            Log.d(TAG, "onCreateView: Visibility set");
        }else if(isDetached()){
            Toast.makeText(getContext(),"bruhhh",Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: " + databaseReference.getKey());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                taskListArrayList.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + snap);
                    getAllTask(snap);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void clearList() {
        Log.d(TAG, "clearList: runs");
        if (!taskListArrayList.isEmpty()) {
            taskListArrayList.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.option_menu:
                Toast.makeText(getContext(), "Settings menu coming soon", Toast.LENGTH_SHORT).show();
                //TODO: create a settings menu
                Intent settingsIntent = new Intent(getContext(),SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menuAddTask:
                // Toast.makeText(getContext(), "meh", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                Toast.makeText(getContext(),"is it gone?",Toast.LENGTH_SHORT).show();
                for(int i = 0; i<= taskListArrayList.size()-1; i++){
                    if (taskListArrayList.get(i).canWeDelete()){
                        //TODO: figure out a way to do this with getID()
                        mTaskList = taskListArrayList.get(i);
                        Log.d(TAG, "onOptionsItemSelected: " + mTaskList.getPathname());
                        databaseReference.child(mTaskList.getPathname()).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String v = data.getStringExtra("note");
        String s = data.getStringExtra("name");
        String id = data.getStringExtra("ID");
        String Path = FirebasePathVerify.pathCheck(s);
        Boolean edit = data.getBooleanExtra("is this editable", false);

        if (requestCode == 1) {
            mTaskList = new TaskList(s, v, edit, id, Path);
            databaseReference.child(Path).setValue(mTaskList);//TODO:see options menu for delete

            //as long as we make sure we have the right references we can just add it to the correct nesting tree
        } else if (requestCode == 0) {
            return;
        }
        adapter.notifyDataSetChanged();
    }

    private void getAllTask(DataSnapshot dataSnapshot) {
        TaskList key = dataSnapshot.getValue(TaskList.class);
        taskListArrayList.add(key);
        adapter = new TabAdapter(getContext(), taskListArrayList, getString(R.string.tab_text_2));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
