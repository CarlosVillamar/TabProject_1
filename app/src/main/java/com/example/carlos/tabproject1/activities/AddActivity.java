package com.example.carlos.tabproject1.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carlos.tabproject1.R;
import com.example.carlos.tabproject1.models.TodoTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity allows us to add entries into our database for the current tab we have displayed
 */
public class AddActivity extends AppCompatActivity implements View.OnKeyListener, TextWatcher {
    //TodoTask: whatever entries we use as a pathname for our nodes in firebase must not contain . # $ [ or ]

    TodoTask todoTask;
    EditText nameEditText, notesEditText;
    FloatingActionButton addNoteBtn;
    String taskname, tasknote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        /**
         * Upon creation, we find the input text boxes and buttons we have set in our layout
         * for this activity and set the appropriate listeners to them
         */

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
            }
        });


    }

    public void saveTask() {
        /**
         * This function will save the user's entry and return its data via an intent back to the tab fragment it came from
         * */
        Intent intent = getIntent();

        taskname = String.valueOf(nameEditText.getText());
        tasknote = String.valueOf(notesEditText.getText());

        if (taskname.isEmpty() || taskname.equals("")) {
            Toast.makeText(getBaseContext(), "Leave nothing Empty", Toast.LENGTH_SHORT).show();
        } else if (!taskname.isEmpty()) {
            Log.d("yerrrrrr", "saveTask: taskname is not empty triggered");
            todoTask = new TodoTask();

            String tName = todoTask.setName(taskname);
            String tNote = todoTask.setNote(tasknote);
            Boolean edit = todoTask.readyForDeletion(false);
            String ID = todoTask.setID(UUID.randomUUID().toString());
            todoTask.toMap();

            intent.putExtra("name", tName);
            intent.putExtra("note", tNote);
            intent.putExtra("is this editiable", edit);
            intent.putExtra("ID", ID);
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
            //return to the previous activity without doing anything
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
