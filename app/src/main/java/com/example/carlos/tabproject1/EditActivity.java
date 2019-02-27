package com.example.carlos.tabproject1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * This activity allows us to edit an entry in it's corresponding tab
 * this class is very similar to the AddActivity class however this class will request the tabName from the TabAdpater
 *
 * This class will get called via an intent that is called from the TabAdapter in order to read and write entries fromm the correct tab
 * It will receive an explicit intent call from said adapter in order to know which database instance to use.
 *
 * However unlike AddActivity the firebase database entry will get set in this class
 * */
public class EditActivity extends AppCompatActivity implements View.OnKeyListener ,TextWatcher {
    Task task;
    EditText nameEditText, notesEditText;
    Button doneBtn, cancelBtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        //retrieve the tabname from the intent call
        final String tabName = getIntent().getStringExtra("Tabname");

        //retrieve the correct firebase instance node with the tabname we received from the intent call
        databaseReference = FirebaseDatabase.getInstance().getReference(tabName);
//        Log.d("yerrr", "onCreate: "+ getIntent().getStringExtra("TaskName"));

        /**init buttons and add listeners to them
         * */
        nameEditText = findViewById(R.id.nameAlterText);
        nameEditText.addTextChangedListener(this);
        nameEditText.setOnKeyListener(this);

        notesEditText = findViewById(R.id.notesAlterText);
        notesEditText.addTextChangedListener(this);
        notesEditText.setOnKeyListener(this);

        /**unlike AddActivity we will load up the task name and its corresponding note data
         * for the user to see what entry they are editing
         * */
        nameEditText.setText(getIntent().getStringExtra("TaskName"));
        notesEditText.setText(getIntent().getStringExtra("TaskNote"));

        doneBtn = findViewById(R.id.editDone);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
                finish();
            }
        });
        cancelBtn = findViewById(R.id.editCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void saveTask() {
        Log.d("saveTask called", "saveTask: we made it ");

        String taskname = String.valueOf(nameEditText.getText());
        String tasknote = String.valueOf(notesEditText.getText());
        String ID = UUID.randomUUID().toString();

        if (taskname == null || taskname.equals(" ")) {
            Toast.makeText(getBaseContext(), "Leave nothing Empty", Toast.LENGTH_SHORT).show();
            task = new Task();
            String tName = "Fix me";
            String tNote = "Delete me";
            Boolean edit = task.readyForDeletion(false);
            task.toMap();
            task = new Task(tName,tNote,edit,ID,FirebasePathVerify.pathCheck(tName));//create the task object
            databaseReference.child(getIntent().getStringExtra("TaskName")).setValue(task);//re-write the firebase entry
            finish();
            return;
        }else if(!taskname.isEmpty()){
            //Log.d("editSaveTask", "saveTask: " +  taskname+ " " + tasknote+" "+ taskPath );
            task = new Task();
            String tName = task.setName(taskname);
            String tNote = task.setNote(tasknote);
            Boolean edit = task.readyForDeletion(false);
            ID = task.setID(ID);
            task.toMap();
            task = new Task(tName,tNote,edit,ID,FirebasePathVerify.pathCheck(tName));//create the task object
            databaseReference.child(getIntent().getStringExtra("TaskName")).setValue(task);//re-write the firebase entry
            finish();
        }
        Toast.makeText(getApplicationContext(),"Task updated",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //We want to set the cursor to end to the taskname instead of positioning it behind the
            //taskname
            nameEditText.setSelection(nameEditText.length());

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //go back to previous activity and take no action
            this.finish();
            v.getWindowToken();
            Log.d("onKey", "Go back");
            return true;
        }
        return false;
    }
}
