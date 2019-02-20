package com.example.carlos.tabproject1;

import android.content.Context;
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
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Tab2Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    ArrayList<Task> taskArrayList;
    TabAdapter adapter;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    CheckBox checkBox;
    Task mTask;
    Context context;

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
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycleViewMain);

        taskArrayList = new ArrayList<Task>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new TabAdapter(getContext(), taskArrayList);
        recyclerView.setAdapter(adapter);

       initializeData();
        return v;
    }

    private void initializeData() {
        String[] nameArray = getResources().getStringArray(R.array.tab2Name);
        String[] noteArray = getResources().getStringArray(R.array.NotesforTab2);

        taskArrayList.clear();

        for(int i = 0; i< nameArray.length;i++){
            taskArrayList.add(new Task(nameArray[i],noteArray[i],false));

        }
        adapter.notifyDataSetChanged();
    }
    private void getAllTask(DataSnapshot dataSnapshot) {
        Task key = dataSnapshot.getValue(Task.class);
        taskArrayList.add(key);
        adapter = new TabAdapter(getContext(), taskArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void taskDeletion(DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Task taskTitle = singleSnapshot.getValue(Task.class);
            for (int i = 0; i < taskArrayList.size(); i++) {
                if (taskArrayList.contains(taskTitle) && checkBox.isChecked()) {
                    taskArrayList.remove(taskTitle);

                }
            }
            Log.d(TAG, "Task tile " + taskTitle);
            adapter.notifyDataSetChanged();
            adapter = new TabAdapter(getContext(), taskArrayList);
            recyclerView.setAdapter(adapter);
        }
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
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                // Toast.makeText(getContext(), "deleteeMEHHHHH", Toast.LENGTH_SHORT).show();
                if (taskArrayList.size() >= 0) {
//                    taskArrayList.remove(taskArrayList.size()-1);

                    adapter.removeItem(taskArrayList.remove(2));
                    adapter.notifyDataSetChanged();
                } else {
                    break;
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(getString(R.string.tab_text_2));
        Log.d(TAG, "onActivityCreated: "+ databaseReference.child(getString(R.string.tab_text_2)));
        //we can now pull speffic instances

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                taskArrayList.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()) {
//                        getAllTask(snap);
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
        Boolean edit = data.getBooleanExtra("is this editable",true);
        if(s.isEmpty()) s ="fix me";

        if (requestCode == 1) {
            mTask = new Task(s, v,edit);
            databaseReference.child(s).setValue(mTask);
            //as long as we make sure we have the right references we can just add it to the correc nesting tree
        }else{
            return;
        }
        adapter.notifyDataSetChanged();
    }

}
