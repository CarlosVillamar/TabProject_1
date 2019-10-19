package com.example.carlos.tabproject1.views;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.carlos.tabproject1.R;
import com.example.carlos.tabproject1.models.TodoTask;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    private TextView nameTV, noteTV;
    private TodoTask mTodoTask;
    private TabAdapter adapter;

    public ViewHolder(View view, TabAdapter inflate) {
        super(view);

        nameTV = view.findViewById(R.id.nameTextView);
        noteTV = view.findViewById(R.id.notesTextView);
        checkBox = view.findViewById(R.id.completedCheckBox);
        adapter = inflate;
    }

    public void bindTo(TodoTask todoTask) {
        nameTV.setText(todoTask.getName());
        noteTV.setText(todoTask.getNote());
        mTodoTask = todoTask;
    }
}
