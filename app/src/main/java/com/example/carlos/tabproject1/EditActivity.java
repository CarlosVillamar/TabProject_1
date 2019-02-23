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

import java.util.UUID;

public class EditActivity extends AppCompatActivity implements View.OnKeyListener ,TextWatcher {
    Task task;
    EditText nameEditText, notesEditText;
    Button doneBtn, cancelBtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        final String tabName = getIntent().getStringExtra("Tabname");

        databaseReference = FirebaseDatabase.getInstance().getReference(tabName);
//        Log.d("yerrr", "onCreate: "+ getIntent().getStringExtra("TaskName"));

        nameEditText = findViewById(R.id.nameAlterText);
        nameEditText.addTextChangedListener(this);
        nameEditText.setOnKeyListener(this);
        nameEditText.setText(getIntent().getStringExtra("TaskName"));

        notesEditText = findViewById(R.id.notesAlterText);
        notesEditText.addTextChangedListener(this);
        notesEditText.setOnKeyListener(this);
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
            Boolean edit = task.getEditable(false);
            task.toMap();
            task = new Task(tName,tNote,edit,ID,FirebasePathVerify.pathCheck(tName));
            databaseReference.child(getIntent().getStringExtra("TaskName")).setValue(task);
            finish();
            return;
        }else if(!taskname.isEmpty()){
//            Log.d("editSaveTask", "saveTask: " +  taskname+ " " + tasknote+" "+ taskPath );
            task = new Task();
            String tName = task.setName(taskname);
            String tNote = task.setNote(tasknote);
            String Path = task.setPathname(FirebasePathVerify.pathCheck(tName));
            Boolean edit = task.getEditable(false);
            ID = task.setID(ID);
            task.toMap();
            task = new Task(tName,tNote,edit,ID,Path);
            databaseReference.child(getIntent().getStringExtra("TaskName")).setValue(task);
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
