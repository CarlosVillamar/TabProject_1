package com.example.carlos.tabproject1;


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
import android.widget.CompoundButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    DatabaseReference databaseReference;
//    FirebaseDatabase firebaseDatabase;
    List<TODO> todoArrayList = new ArrayList<>();


    public Tab1Fragment() {
        // Required empty public constructor
    }    CheckBox checkBox;
    tabAdapter adapter;
    TODO mTodo;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = v.findViewById(R.id.recycleView);
        checkBox = v.findViewById(R.id.completedCheckBox);
        todoArrayList = new ArrayList<TODO>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        adapter = new tabAdapter(getContext(), todoArrayList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initializeData();
        return v;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menuAddTask:

                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);

                adapter.notifyDataSetChanged();
                break;
            case R.id.menuDelete:
                if (todoArrayList.size() >= 1) {
                    todoArrayList.remove(todoArrayList.size() - 1);
//                        checkBox.isSelected();
//                    checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
//                    checkBox.getTag();
//                    todoArrayList.remove();
//                    databaseReference.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            getAllTask(dataSnapshot);
//                        }
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                            getAllTask(dataSnapshot);
//                        }
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//                            taskDeletion(dataSnapshot);
//                        }
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//                    });
                    adapter.notifyDataSetChanged();
                } else {
                    break;
                }
                break;

            default:

        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeData() {

        String[] nameArray = getResources().getStringArray(R.array.Name);
        String[] noteArray = getResources().getStringArray(R.array.Notes);

        todoArrayList.clear();

//        DatabaseReference reference =  firebaseDatabase.getReference();
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                for(DataSnapshot dSnap : dataSnapshot.getChildren()) {
//                    String taskValue = dSnap.getValue(String.class);
//                    todoArrayList.add(new TODO(taskValue));
//                    Log.d("Yerrrrrrrrr", "Value is: " + taskValue);
//
//                }
//                    adapter = new tabAdapter(getContext(), todoArrayList);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();Ò
                    getAllTask(dSnap);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Log.d("yerrrr", "initializeData: declared");
        adapter.notifyDataSetChanged();

    }
    private void getAllTask(DataSnapshot dataSnapshot){
//        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//            String taskTitle = singleSnapshot.getValue(String.class);
            String key2 = databaseReference.child("task").toString();
            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
            map.get(key2);
            Log.d("Yerrrrrrrrr", "Value is: " + map);
            todoArrayList.add(new TODO(key2));
            adapter = new tabAdapter(getContext(), todoArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//        }
    }

    private void taskDeletion(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String taskTitle = singleSnapshot.getValue(String.class);
            for(int i = 0; i < todoArrayList.size(); i++){
                if(todoArrayList.get(i).getName().equals(taskTitle)){
                    todoArrayList.remove(i);
                }
            }
            Log.d(TAG, "Task tile " + taskTitle);
            adapter.notifyDataSetChanged();
            adapter = new tabAdapter(getContext(), todoArrayList);
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

//            String s = data.getStringExtra("name");
//            String v = data.getStringExtra("note");
//            databaseReference.child(s).push();
//            mTodo = new TODO(s, v);
//            todoArrayList.add(mTodo);
            adapter.notifyDataSetChanged();

        }

    }


}
