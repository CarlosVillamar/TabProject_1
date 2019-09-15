package com.example.carlos.tabproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class Tab2Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * The fragment argument representing the section number for this
     * fragment. Refer to Tab1Fragment class comments to understand how this class works.
     * Both classes are identical, we just refer to different firebase instances
     */
    AppCompatActivity activity;
    FirebaseUtility fireDB;
    List<TodoTask> todoTaskListArray;
    TodoTask mTodoTask;
    TabAdapter adapter;
    RecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.tab_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycleView);

        todoTaskListArray = new ArrayList<TodoTask>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new TabAdapter(getContext(), todoTaskListArray, getString(R.string.tab_text_2));

        RecyclerViewHelper viewHelper = new RecyclerViewHelper(view,linearLayoutManager,adapter);
        recyclerView = viewHelper.getRecyclerView();

        fireDB = new FirebaseUtility(todoTaskListArray,
                adapter, mTodoTask,recyclerView,"Personal");

        if (isDetached() || activity == null) {
            //implemented bouncer pattern to check validity
            Toast.makeText(getContext(), "bruhhh", Toast.LENGTH_SHORT).show();
        }else{
            /**we want to check if our main activity has a value and if the fragment class method
             isAdded returns a true value, when isAdded returns true, it indicates the fragment
             is attached to a UI component*/

            recyclerView.setVisibility(view.getVisibility());
            Log.d(TAG, "onCreateView: Visibility set");
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fireDB.addValueListener();
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
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menuAddTask:
                // Toast.makeText(getContext(), "meh", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                Toast.makeText(getContext(), "is it gone?", Toast.LENGTH_SHORT).show();
                for (int i = 0; i <= todoTaskListArray.size() - 1; i++) {
                    if (todoTaskListArray.get(i).canWeDelete()) {
                        //TODO: figure out a way to do this with getID()
                        mTodoTask = todoTaskListArray.get(i);
                        Log.d(TAG, "onOptionsItemSelected: " + mTodoTask.getPathname());
//                        databaseReference.child(mTodoTask.getPathname()).removeValue();
                        fireDB.removeNode(mTodoTask);
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
            mTodoTask = new TodoTask(s, v, edit, id, Path);
            fireDB.getPath(Path, mTodoTask);

            //as long as we make sure we have the right references we can just add it to the correct nesting tree
        } else if (requestCode == 0) {
            return;
        }
        adapter.notifyDataSetChanged();
    }
}
