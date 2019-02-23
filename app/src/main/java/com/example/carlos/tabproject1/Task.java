package com.example.carlos.tabproject1;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Carlos on 4/30/2018.
 */
class Task {
    private String name, note, ID, pathname;

    private boolean editable;

    Task() {
        //this class is needed for the database snapshots

    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, String notes, Boolean editable, String ID) {
        this.ID = ID;
        this.name = name;
        this.note = notes;
        this.editable = editable;

    }

    public Task(String name, String notes, Boolean editable, String ID, String pathname) {
        this.ID = ID;
        this.pathname = pathname;
        this.name = name;
        this.note = notes;
        this.editable = editable;

    }

    String getID() {
        return ID;
    }

    String getName() {
        return name;
    }

    String getNote() {
        return note;
    }

    String getPathname(){return pathname;}


    boolean isEditable() {
        return editable;
    }

    boolean getEditable(boolean editable) {
        this.editable = editable;
        return editable;
    }

    public String setID(String ID) {
        this.ID = ID;
        return ID;
    }
    public String setPathname(String pathname){
        this.pathname = pathname;
        return pathname;
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
