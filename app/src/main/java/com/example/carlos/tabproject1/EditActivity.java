package com.example.carlos.tabproject1;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity implements View.OnKeyListener ,TextWatcher {
    Task task;
    EditText nameEditText, notesEditText;
    Button doneBtn, cancelBtn;
    DatabaseReference databaseReference;
    DialogInterface dialog;
    TabAdapter tabAdapter;
    DataSnapshot dataSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        String tabName = getIntent().getStringExtra("Tabname");
        int taskId = getIntent().getIntExtra("Task Number",0);
        final String taskPath = Integer.toString(taskId);
        Log.d("edit", "onCreate: tabName " + tabName + " taskId " + taskId + " taskPath " + taskPath );

        databaseReference = FirebaseDatabase.getInstance().getReference(tabName);
//        Log.d("EditActivity", "onCreate: DB "+ dataSnapshot.getChildren());

        nameEditText = findViewById(R.id.nameAlterText);
        nameEditText.addTextChangedListener(this);
        nameEditText.setOnKeyListener(this);
        nameEditText.setText(" ");

        notesEditText = findViewById(R.id.notesAlterText);
        notesEditText.addTextChangedListener(this);
        notesEditText.setOnKeyListener(this);
        notesEditText.setText(" ");

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.tab_text_1));

        doneBtn = findViewById(R.id.editDone);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "coming soon", Toast.LENGTH_LONG).show();
//                Log.d("We in the building", "onClick: Done");
                saveTask(taskPath);
                Log.d("yerrrr", "onClick: " + databaseReference.orderByKey());
                databaseReference.child("Tasks").getKey();
                finish();
            }
        });
        cancelBtn = findViewById(R.id.editCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "coming soon", Toast.LENGTH_LONG).show();
                Log.d("you thoughtttt", "onClick: canceled");
                finish();
            }
        });



    }
    public void saveTask(String taskPath) {
        Log.d("saveTask called", "saveTask: we made it ");

        String taskname = String.valueOf(nameEditText.getText());
        String tasknote = String.valueOf(notesEditText.getText());
        int taskId = getIntent().getIntExtra("Task Number",0);
        Log.d("editSaveTask", "saveTask: taskPath " + taskPath );


        if (taskname == null || taskname.equals(" ")) {
            Toast.makeText(getBaseContext(), "Leave nothing Empty", Toast.LENGTH_SHORT).show();
            task = new Task();
            String tName = "Fix me";
            String tNote = "Delete me";
            Boolean edit = task.getEditable(true);
            task = new Task(tName,tNote,edit,taskId);
            databaseReference.child(taskPath).setValue(task);
            finish();
            return;
        }else if(!taskname.isEmpty()){
            Log.d("editSaveTask", "saveTask: " +  taskname+ " " + tasknote );
            task = new Task();
            String tName = task.setName(taskname);
            String tNote = task.setNote(tasknote);
            Boolean edit = task.getEditable(true);
            task.toMap();

            task = new Task(tName,tNote,edit,taskId);
            databaseReference.child(taskPath).setValue(task);
            finish();
        }
    }









    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Task: Add imports text values to their proper input boxes before the change
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
            this.finish();
            Log.d("onKey", "Go back");
            return true;
        }
        return false;
    }
}
