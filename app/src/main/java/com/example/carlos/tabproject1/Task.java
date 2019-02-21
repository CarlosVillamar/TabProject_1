package com.example.carlos.tabproject1;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Carlos on 4/30/2018.
 */
class Task {
    static final String NAME_KEY = "Name";
    static final String NOTE_KEY = "Notes";
    static final int ID_KEY = 0;


    private String name;
    private String note;
    private int ID;
    private boolean editable;

    Task() {
        //this class is needed for the database snapshots

    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, String notes, Boolean editable) {
        // this.ID = ID;
        this.name = name;
        this.note = notes;
        this.editable = editable;

    }

    public Task(String name, String notes, Boolean editable, int ID) {
        this.ID = ID;
        this.name = name;
        this.note = notes;
        this.editable = editable;

    }

    int getID() {
        return ID;
    }

    String getName() {
        return name;
    }

    String getNote() {
        return note;
    }


    boolean isEditable() {
        return editable;
    }

    boolean getEditable(boolean editable) {
        this.editable = editable;
        return editable;
    }

    public int setID(int id) {
        this.ID = id;
        return id;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String setNote(String note) {
        this.note = note;
        return note;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", ID);
        result.put("Task Name", name);
        result.put("Notes", note);
        result.put("isEditable", editable);
        return result;
    }


}
