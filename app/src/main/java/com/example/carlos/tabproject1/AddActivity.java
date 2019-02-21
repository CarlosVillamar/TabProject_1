package com.example.carlos.tabproject1;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class AddActivity extends AppCompatActivity implements View.OnKeyListener, TextWatcher {
    //Task: whatever entries we use as a path in firebase must not contain . # $ [ or ]

    Task task;
    EditText nameEditText, notesEditText;
    FloatingActionButton addNoteBtn;
    DatabaseReference databaseReference;
    FirebasePathVerify FirebasePathVerify;
    String s, taskname,tasknote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        nameEditText = findViewById(R.id.nameEditText);
        nameEditText.addTextChangedListener(this);
        nameEditText.setOnKeyListener(this);

        notesEditText = findViewById(R.id.notesEditText);
        notesEditText.addTextChangedListener(this);
        notesEditText.setOnKeyListener(this);

        addNoteBtn = findViewById(R.id.floatingActionButton);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
                v.getWindowToken();
                finish();
            }
        });



    }

    public void saveTask() {
        Intent intent = getIntent();

//        Log.d("saveTask called", "saveTask: we made it ");
        taskname = String.valueOf(nameEditText.getText());
        tasknote = String.valueOf(notesEditText.getText());

        if (taskname == null || taskname.equals("")) {
            Toast.makeText(getBaseContext(), "Leave nothing Empty", Toast.LENGTH_SHORT).show();
            setResult(0, intent);
            Log.d("yerrrrrr", "saveTask: taskname is empty triggered");
            finish();
            return;
        } else if(!taskname.isEmpty()){
            Log.d("yerrrrrr", "saveTask: taskname is not empty triggered");
            task = new Task();
            String tName = task.setName(taskname);
            String tNote = task.setNote(tasknote);
            Boolean edit = task.getEditable(true);
            task.toMap();

            intent.putExtra("name", tName);
            intent.putExtra("note", tNote);
            intent.putExtra("is this editiable", edit);
            setResult(1, intent);
            finish();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            // hide the soft Keyboard
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveTask();
            v.getWindowToken();
            this.finish();
            Log.d("onKey", "Go back");
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
