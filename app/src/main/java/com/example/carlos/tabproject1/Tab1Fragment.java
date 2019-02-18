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
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    DatabaseReference databaseReference;
    List<TODO> todoArrayList = new ArrayList<>();


    public Tab1Fragment() {
        // Required empty public constructor
    }
    CheckBox checkBox;
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
//        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.tab_text_1));
        //we can now pull speffic instances

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                todoArrayList.clear();
                for(DataSnapshot dSnap : dataSnapshot.getChildren()) {
                    getAllTask(dSnap);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        return v;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void clearList(){
        Log.d(TAG, "clearList: runs");
        if(!todoArrayList.isEmpty()){
            todoArrayList.clear();
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
                Toast.makeText(getContext(),"Settings menu coming soon",Toast.LENGTH_SHORT).show();
                //TODO: create a settings menu
                break;
            case R.id.menuAddTask:

                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                if (todoArrayList.size() >= 1) {
//                    checkBox.isSelected();
//                    checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
//                    checkBox.getTag();
                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            getAllTask(dataSnapshot);
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            getAllTask(dataSnapshot);
                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            taskDeletion(dataSnapshot);
                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
//                    adapter.notifyDataSetChanged();
                }

            default:

        }
        return super.onOptionsItemSelected(item);
    }


    private void getAllTask(DataSnapshot dataSnapshot){
            TODO key = dataSnapshot.getValue(TODO.class);
            todoArrayList.add(key);
            adapter = new tabAdapter(getContext(), todoArrayList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

    }

    private void taskDeletion(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            TODO taskTitle = singleSnapshot.getValue(TODO.class);
            for(int i = 0; i < todoArrayList.size(); i++){
                if(todoArrayList.contains(taskTitle)&& checkBox.isChecked()){
                    todoArrayList.remove(taskTitle);

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
            String v = data.getStringExtra("note");
            String s = data.getStringExtra("name");
            Boolean edit = data.getBooleanExtra("is this editable",true);
            if(s.isEmpty()) s ="fix me";

        if (requestCode == 1) {
            mTodo = new TODO(s, v,edit);
            databaseReference.child(s).setValue(mTodo);
            //as long as we make sure we have the right references we can just add it to the correc nesting tree
        }else{
            return;
        }
            adapter.notifyDataSetChanged();
    }


}
