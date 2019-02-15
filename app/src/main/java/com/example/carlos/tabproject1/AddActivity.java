package com.example.carlos.tabproject1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnKeyListener, TextWatcher {

    TODO todo;
    EditText nameEditText, notesEditText;
    CheckBox checkBox;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_activity);

        nameEditText = findViewById(R.id.nameEditText);
        notesEditText = findViewById(R.id.notesEditText);
        checkBox = findViewById(R.id.completedCheckBox);
        //checkBox.onTouchEvent()

        nameEditText.addTextChangedListener(this);
        notesEditText.addTextChangedListener(this);

        nameEditText.setOnKeyListener(this);
        notesEditText.setOnKeyListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    public void saveTask() {
        Log.d("saveTask called", "saveTask: we made it ");
        String taskname = String.valueOf(nameEditText.getText());
        String tasknote = String.valueOf(notesEditText.getText());
//        int id = todo.setID(databaseReference.getKey().length());

        if (taskname == null || taskname.equals(" ") || tasknote == null || tasknote.equals("")) {
            Toast.makeText(getBaseContext(), "Leave nothing Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        todo = new TODO();
        String tName = todo.setName(taskname);
        String tNote = todo.setNote(tasknote);
        Boolean edit = todo.getEditable(true);

        todo.toMap();
       //TODO: assign a unique ID to each entry
        databaseReference.child(tName).setValue(todo);


        Intent intent = getIntent();
        intent.putExtra("name",tName);
        intent.putExtra("note",tNote);
        setResult(1,intent);
        finish();

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
            Log.d("onKey", "Go back");
            this.finish();
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
