package com.example.carlos.tabproject1.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carlos.tabproject1.R;
import com.example.carlos.tabproject1.activities.AddActivity;
import com.example.carlos.tabproject1.activities.SettingsActivity;
import com.example.carlos.tabproject1.db.FirebasePathVerify;
import com.example.carlos.tabproject1.db.FirebaseUtility;
import com.example.carlos.tabproject1.models.TodoTask;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class Tab1Fragment extends Fragment {

    FragmentActivity activity = getActivity();
    FirebaseUtility fireDB;
    List<TodoTask> todoTaskListArray = new ArrayList<>();
    TabAdapter adapter;
    TodoTask mTodoTask;
    RecyclerView recyclerView;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);//make sure we recognize the add, delete and setting buttons on the action bar

        // Inflate the layout for this fragment by setting the view the layout will live in
        View view = inflater.inflate(R.layout.tab_fragment, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        //init and set our tab adapter to recognize what data it will render to the user
        adapter = new TabAdapter(getContext(), todoTaskListArray, getString(R.string.tab_text_1));

        RecyclerViewHelper viewHelper = new RecyclerViewHelper(view,linearLayoutManager,adapter);
        recyclerView = viewHelper.getRecyclerView();

        //pull the firebase database reference for this tab and its event listeners
        fireDB = new FirebaseUtility(todoTaskListArray,
                adapter, mTodoTask,recyclerView, "School Tasks");

        /**
         * The if statement below address a null activity issue I was facing when traveling
         * between activities
         *
         * The solution to this issue ws inspired from this stack overflow thread
         * https://stackoverflow.com/questions/28672883/java-lang-illegalstateexception-fragment-not-attached-to-activity
         */

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
        /**we can now pull specific instances when our fragment starts its lifecycle, by adding
         * value listeners to our database instance we can now load the instance data on startup and
         * ensure it's updated when its the instances value changes*/
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
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menuAddTask:
                //make a call to AddActivity to add a todoTask to the list
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("TaskNumber", adapter.getItemCount() + 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.menuDelete:
                /**For our delete button we want to loop through the todoTask list itself and check
                 * which object is ready for deletion and remove them, and make sure the removal is
                 * reflected in the view itself*/
                for (int i = 0; i <= todoTaskListArray.size() - 1; i++) {
                    if (todoTaskListArray.get(i).canWeDelete()) {
                        //TODO: figure out a way to do this with getID()
                        mTodoTask = todoTaskListArray.get(i);
                        fireDB.removeNode(mTodoTask);
                        adapter.notifyDataSetChanged();

                        Log.d(TAG, "onOptionsItemSelected: node deleted" + mTodoTask.getPathname());
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
         * We retrieve the data our intent call returns and create a new todoTask object,
         * then we save the object in a new firebase entry*/
        String id = data.getStringExtra("ID");
        String v = data.getStringExtra("note");
        String s = data.getStringExtra("name");
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
