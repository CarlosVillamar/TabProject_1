package com.example.carlos.tabproject1;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * Created by Carlos on 4/30/2018.
 */
class TODO {
    static final String NAME_KEY = "Name";
    static final String NOTE_KEY = "Notes";
    static final int ID_KEY = 0;


    private String name;
    private String note;
    private int ID;
    private boolean editable;

    TODO(){
        //this class is needed for the database snapshots

    }

    public TODO(String name) {
        this.name = name;
    }

    public TODO(String name,String notes) {
       // this.ID = ID;
        this.name = name;
        this.note = notes;
       // this.editable =  editable;

    }

    int getID(){return ID;}

    String getName() {
        return name;
    }

    String getNote() {
        return note;
    }



    boolean isEditable(){return editable;}

    boolean getEditable(boolean editable){
        this.editable = editable;
        return editable;
    }

    public int setID(int id){
        this.ID = id;
        return id;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }
    public String setNote(String note){
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
