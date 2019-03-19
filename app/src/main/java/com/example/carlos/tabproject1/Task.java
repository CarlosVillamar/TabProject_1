package com.example.carlos.tabproject1;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Carlos on 4/30/2018.
 * this is a getter and setter class for each task created
 */
class Task {
    private String name, note, ID, pathname;

    private boolean delete;

    Task() {//this class is needed for the database snapshots
    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, String notes, Boolean delete, String ID) {
        this.ID = ID;
        this.name = name;
        this.note = notes;
        this.delete = delete;

    }

    public Task(String name, String notes, Boolean delete, String ID, String pathname) {
        this.ID = ID;
        this.pathname = pathname;
        this.name = name;
        this.note = notes;
        this.delete = delete;

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


    boolean canWeDelete() {
        return delete;
    }

    boolean readyForDeletion(boolean editable) {
        this.delete = editable;
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
        result.put("canWeDelete", delete);
        return result;
    }


}
