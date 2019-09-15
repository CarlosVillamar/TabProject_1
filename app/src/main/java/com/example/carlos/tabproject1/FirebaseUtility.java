package com.example.carlos.tabproject1;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;


public class FirebaseUtility {

    DatabaseReference databaseReference;
    List<TodoTask> todoTaskListArray;
    TabAdapter adapter;
    TodoTask mTodoTask;
    RecyclerView recyclerView;

    FirebaseUtility(List taskListArrayList, TabAdapter adapter, TodoTask mTodoTask, RecyclerView recyclerView, String tabName){
        this.todoTaskListArray = taskListArrayList;
        this.adapter =adapter;
        this.mTodoTask = mTodoTask;
        this. recyclerView = recyclerView;
        this.databaseReference =  FirebaseDatabase.getInstance().getReference(tabName);

        getChildRef();
        Log.d("hhh", databaseReference.toString());
    }

    public void getChildRef() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                todoTaskListArray.clear();
                getAllTask(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addValueListener(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This method is called once with the initial value and again whenever data at this location is updated.
                todoTaskListArray.clear();//ensure our fragments does not create duplicates
                for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
                    Log.d("onDataChange",dSnap.toString());
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

    public void getPath(String Path, TodoTask mTodoTask){
         databaseReference.child(Path).setValue(mTodoTask);
    }

    public void removeNode(TodoTask mTodoTask){
        databaseReference.child(mTodoTask.getPathname()).removeValue();
    }

    public void getAllTask(DataSnapshot dataSnapshot) {
        /**This method will retreive all of our todoTask objects from firebase, as them to our List
         * interface and make sure it all gets added to our view*/
        TodoTask key = dataSnapshot.getValue(TodoTask.class);

        todoTaskListArray.add(key);
        adapter = new TabAdapter(recyclerView.getContext(), todoTaskListArray, "School Tasks");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}