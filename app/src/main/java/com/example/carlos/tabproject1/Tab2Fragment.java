package com.example.carlos.tabproject1;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    List<Task> taskArrayList;
    TabAdapter adapter;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    Task mTask;

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
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = v.findViewById(R.id.recycleView);

        taskArrayList = new ArrayList<Task>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new TabAdapter(getContext(), taskArrayList, getString(R.string.tab_text_2));
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
                taskArrayList.clear();
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


        return v;
    }

    private void getAllTask(DataSnapshot dataSnapshot) {
        Task key = dataSnapshot.getValue(Task.class);
        taskArrayList.add(key);
        adapter = new TabAdapter(getContext(), taskArrayList, getString(R.string.tab_text_2));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void pullReferences() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void clearList() {
        Log.d(TAG, "clearList: runs");
        if (!taskArrayList.isEmpty()) {
            taskArrayList.clear();
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
                break;
            case R.id.menuAddTask:
                // Toast.makeText(getContext(), "meh", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                Toast.makeText(getContext(),"is it gone?",Toast.LENGTH_SHORT).show();
                for(int i = 0; i<=taskArrayList.size()-1;i++){
                    if (taskArrayList.get(i).isEditable()){
                        //TODO: figure out a way to do this with getID()
                        mTask = taskArrayList.get(i);
                        Log.d(TAG, "onOptionsItemSelected: " + mTask.getName());
                        databaseReference.child(mTask.getName()).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
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
                taskArrayList.clear();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String v = data.getStringExtra("note");
        String s = data.getStringExtra("name");
        String id = data.getStringExtra("ID");
        Boolean edit = data.getBooleanExtra("is this editable", false);

        if (requestCode == 1) {
            s = FirebasePathVerify.pathCheck(s);
            mTask = new Task(s, v, edit, id);
            databaseReference.child(s).setValue(mTask);//TODO:see options menu for delete

            //as long as we make sure we have the right references we can just add it to the correct nesting tree
        } else if (requestCode == 0) {
            return;
        }
        adapter.notifyDataSetChanged();
    }

}
