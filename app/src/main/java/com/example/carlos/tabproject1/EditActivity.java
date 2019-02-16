package com.example.carlos.tabproject1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class EditActivity extends AppCompatActivity implements View.OnKeyListener ,TextWatcher {
    TODO todo;
    EditText nameEditText, notesEditText;
    FloatingActionButton addNoteBtn;
    DatabaseReference databaseReference;
    DialogInterface dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        nameEditText = findViewById(R.id.nameAlterText);
        nameEditText.addTextChangedListener(this);
        nameEditText.setOnKeyListener(this);

        notesEditText = findViewById(R.id.notesAlterText);
        notesEditText.addTextChangedListener(this);
        notesEditText.setOnKeyListener(this);

        //consider adding buttons

    }
    public void saveTask() {
        Log.d("saveTask called", "saveTask: we made it ");
        String taskname = String.valueOf(nameEditText.getText());
        String tasknote = String.valueOf(notesEditText.getText());
        Intent intent = getIntent();

        if (taskname == null || taskname.equals(" ") || tasknote == null || tasknote.equals("")) {
            Toast.makeText(getBaseContext(), "Leave nothing Empty", Toast.LENGTH_SHORT).show();
            setResult(0,intent);
            todo = new TODO();
            String tName = todo.setName(taskname);
            String tNote = todo.setNote(tasknote);
            Boolean edit = todo.getEditable(true);
            todo.toMap();
            //TODO: assign a unique ID to each entry

            intent.putExtra("name", tName);
            intent.putExtra("note", tNote);
            intent.putExtra("is this editiable", edit);
            setResult(1, intent);
            finish();
            return;
        }else {

            todo = new TODO();
            String tName = todo.setName(taskname);
            String tNote = todo.setNote(tasknote);
            Boolean edit = todo.getEditable(true);
            todo.toMap();
            //TODO: assign a unique ID to each entry

            intent.putExtra("name", tName);
            intent.putExtra("note", tNote);
            intent.putExtra("is this editiable", edit);
            setResult(1, intent);
            finish();
        }
    }









    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //TODO: Add imports text values to their proper input boxes before the change
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
            saveTask();
            v.getWindowToken();
            this.finish();
            Log.d("onKey", "Go back");
            return true;
        }
        return false;
    }
}
