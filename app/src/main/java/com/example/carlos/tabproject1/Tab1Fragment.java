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


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    DatabaseReference databaseReference;
    List<Task> taskArrayList = new ArrayList<>();
    TabAdapter adapter;
    Task mTask;
    RecyclerView recyclerView;


    public Tab1Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tab_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycleView);
        taskArrayList = new ArrayList<Task>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        adapter = new TabAdapter(getContext(), taskArrayList, getString(R.string.tab_text_1));
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.tab_text_1));
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                taskArrayList.clear();
                getAllTask(dataSnapshot);
                Toast.makeText(getContext(),"List refreshed",Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onChildChanged: "+ s);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //we can now pull speffic instances
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                taskArrayList.clear();
                for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
                    getAllTask(dSnap);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


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
                Intent settingsIntent = new Intent(getContext(),SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menuAddTask:
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("TaskNumber", adapter.getItemCount() + 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                Toast.makeText(getContext(),"is it gone?",Toast.LENGTH_SHORT).show();
                for(int i = 0; i<=taskArrayList.size()-1;i++){
                    if (taskArrayList.get(i).isEditable()){
                        //TODO: figure out a way to do this with getID()
                        mTask = taskArrayList.get(i);
                        Log.d(TAG, "onOptionsItemSelected: node deleted" + mTask.getPathname());
                        databaseReference.child(mTask.getPathname()).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                }
                break;

            default:

        }
        return super.onOptionsItemSelected(item);
    }


    private void getAllTask(DataSnapshot dataSnapshot) {
        Task key = dataSnapshot.getValue(Task.class);
        taskArrayList.add(key);
        adapter = new TabAdapter(getContext(), taskArrayList, getString(R.string.tab_text_1));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void moveTask(Task task) {
        taskArrayList.set(0, task);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String id = data.getStringExtra("ID");
        String v = data.getStringExtra("note");
        String s = data.getStringExtra("name");
        String Path = FirebasePathVerify.pathCheck(s);
        Boolean edit = data.getBooleanExtra("is this editable", false);

        if (requestCode == 1) {
            mTask = new Task(s, v, edit, id, Path);
            databaseReference.child(Path).setValue(mTask);//TODO:see options menu for delete

            //as long as we make sure we have the right references we can just add it to the correct nesting tree
        } else if (requestCode == 0) {
            return;
        }
        adapter.notifyDataSetChanged();
    }


}
