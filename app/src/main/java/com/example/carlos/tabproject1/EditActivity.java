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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity implements View.OnKeyListener ,TextWatcher {
    TODO todo;
    EditText nameEditText, notesEditText;
    Button doneBtn, cancelBtn;
    DatabaseReference databaseReference;
    DialogInterface dialog;
    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        nameEditText = findViewById(R.id.nameAlterText);
        nameEditText.addTextChangedListener(this);
        nameEditText.setOnKeyListener(this);
        nameEditText.setText("yerrr");

        notesEditText = findViewById(R.id.notesAlterText);
        notesEditText.addTextChangedListener(this);
        notesEditText.setOnKeyListener(this);
        notesEditText.setText("we in the building");

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.tab_text_1));

        doneBtn = findViewById(R.id.editDone);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "coming soon", Toast.LENGTH_LONG).show();
//                Log.d("We in the building", "onClick: Done");
//                saveTask();
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
    public void saveTask() {
        Log.d("saveTask called", "saveTask: we made it ");
        TODO key = dataSnapshot.getValue(TODO.class);
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
