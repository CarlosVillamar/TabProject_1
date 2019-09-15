package com.example.carlos.tabproject1;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass for out first tab.
 */
public class Tab1Fragment extends Fragment {
    DatabaseReference databaseReference;
    List<TaskList> taskListArrayList = new ArrayList<>();
    TabAdapter adapter;
    TaskList mTaskList;
    FragmentActivity activity = getActivity();
    RecyclerView recyclerView;


    public Tab1Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);//make sure we recognize the add, delete and setting buttons on the action bar
        taskListArrayList = new ArrayList<TaskList>();//create the taskList list objects with a fixed size

        // Inflate the layout for this fragment by setting the view the layout will live in
        View v = inflater.inflate(R.layout.tab_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        //init and set our tab adapter to recognize what data it will render to the user
        adapter = new TabAdapter(getContext(), taskListArrayList, getString(R.string.tab_text_1));
        recyclerView.setAdapter(adapter);

        //pull the firebase database reference for this tab and its event listeners
        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.tab_text_1));
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                taskListArrayList.clear();
                getAllTask(dataSnapshot);
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

        /**
         * The if statement below address a null activity issue I was facing when traveling
         * between activities
         *
         * The solution to this issue ws inspired from this stack overflow thread
         * https://stackoverflow.com/questions/28672883/java-lang-illegalstateexception-fragment-not-attached-to-activity
         */
        if(activity != null&& isAdded()){
            /**we want to check if our main activity has a value and if the fragment class method
             *isAdded returns a true value, when isAdded returns true, it indicates the fragment
             *is attached to a UI component*/
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
        /**we can now pull specific instances when our fragment starts its lifecycle, by adding
         * value listeners to our database instance we can now load the instance data on startup and
         * ensure it's updated when its the instances value changes*/
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This method is called once with the initial value and again whenever data at this location is updated.
                taskListArrayList.clear();//ensure our fragments does not create duplicates
                for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
//                    Log.d("mehhhhh",dSnap.toString());
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void clearList() {
        //TODO:TEST CASE
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
                //TODO: create a settings menu
                Intent settingsIntent = new Intent(getContext(),SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menuAddTask:
                //make a call to AddActivity to add a taskList to the list
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("TaskNumber", adapter.getItemCount() + 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                /**For our delete button we want to loop through the taskList list itself and check
                 * which object is ready for deletion and remove them, and make sure the removal is
                 * reflected in the view itself*/
                for(int i = 0; i<= taskListArrayList.size()-1; i++){
                    if (taskListArrayList.get(i).canWeDelete()){
                        //TODO: figure out a way to do this with getID()
                        mTaskList = taskListArrayList.get(i);
                        Log.d(TAG, "onOptionsItemSelected: node deleted" + mTaskList.getPathname());
//                        Toast.makeText(getContext(),mTaskList.getID(),Toast.LENGTH_SHORT).show();
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
        /**This override method is a will trigger when the explicit intent call to AddActivity is
         * complete.
         *
         * We retrieve the data our intent call returns and create a new taskList object,
         * then we save the object in a new firebase entry*/
        String id = data.getStringExtra("ID");
        String v = data.getStringExtra("note");
        String s = data.getStringExtra("name");
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

    public void getAllTask(DataSnapshot dataSnapshot) {
        /**This method will retreive all of our taskList objects from firebase, as them to our List
         * interface and make sure it all gets added to our view*/
        Log.d("mehhh",dataSnapshot.getValue().toString());

        TaskList key = dataSnapshot.getValue(TaskList.class);
        Log.d("keyzzz",key.toString());

        taskListArrayList.add(key);
        adapter = new TabAdapter(getContext(), taskListArrayList, getString(R.string.tab_text_1));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
